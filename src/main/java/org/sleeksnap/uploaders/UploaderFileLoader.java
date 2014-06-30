package org.sleeksnap.uploaders;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Nikki
 */
public class UploaderFileLoader {

	/**
	 * The gson instance used for loading metadata
	 */
	private static final Gson gson = new Gson();

	/**
	 * The registry to load the uploaders into
	 */
	private final UploaderRegistry registry;

	/**
	 * The directory to scan for uploaders
	 */
	private final File directory;

	public UploaderFileLoader(UploaderRegistry registry, File directory) {
		this.registry = registry;
		this.directory = directory;
	}

	/**
	 * Load uploaders for this registry
	 */
	public void load() throws IOException {
		for (File f : directory.listFiles()) {
			if (f.getName().endsWith("jar") || f.getName().endsWith("zip")) {
				loadUploader(f);
			}
		}
	}

	/**
	 * Load an uploader from a zip or jar file
	 * @param file The file to load from
	 * @throws IOException If an error occurs while reading the file
	 */
	private void loadUploader(File file) throws IOException {
		try (ZipFile zip = new ZipFile(file)) {
			ZipEntry infoEntry = zip.getEntry("uploader.json");

			if (infoEntry == null) {
				throw new IOException("No uploader information file.");
			}

			try (InputStreamReader reader = new InputStreamReader(zip.getInputStream(infoEntry))) {
				UploaderMetaData meta = gson.fromJson(reader, UploaderMetaData.class);

				ClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()});

				try {
					Class<?> cl = loader.loadClass(meta.getMainClass());

					Uploader uploader = (Uploader) cl.newInstance();

					registry.register(new UploaderContainer(uploader, meta));
				} catch (Exception e) {
					throw new IOException("Unable to initialize uploader main class.");
				}
			}
		}
	}
}
