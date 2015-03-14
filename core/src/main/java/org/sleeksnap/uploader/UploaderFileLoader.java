package org.sleeksnap.uploader;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
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

	/**
	 * Construct a new Uploader File Loader.
	 *
	 * @param registry The registry to register uploaders to.
	 * @param directory The directory to load from.
	 */
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
				loadUploaderJar(f);
			} else if (f.isDirectory() && new File(f, "uploader.json").exists()) {
				loadUploaderDirectory(f);
			}
		}
	}

	/**
	 * Load an uploader from a zip or jar file
	 * @param file The file to load from
	 * @throws IOException If an error occurs while reading the file
	 */
	private void loadUploaderJar(File file) throws IOException {
		try (ZipFile zip = new ZipFile(file)) {
			ZipEntry infoEntry = zip.getEntry("uploader.json");

			if (infoEntry == null) {
				throw new IOException("No uploader information file.");
			}

			try (Reader reader = new InputStreamReader(zip.getInputStream(infoEntry))) {
				UploaderMetaData meta = gson.fromJson(reader, UploaderMetaData.class);

				ClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()});

				try {
					Class<?> cl = loader.loadClass(meta.getMainClass());

					Constructor<Uploader> cons = (Constructor<Uploader>) cl.getConstructor(UploaderMetaData.class);

					registry.register(cons.newInstance(meta));
				} catch (Exception e) {
					throw new IOException("Unable to initialize uploader main class.");
				}
			}
		}
	}
	/**
	 * Load an uploader from a directory.
	 * @param file The file to load from
	 * @throws IOException If an error occurs while reading the file
	 */
	private void loadUploaderDirectory(File file) throws IOException {
		File metaFile = new File(file, "uploader.json");

		try (Reader reader = new FileReader(metaFile)) {
			UploaderMetaData meta = gson.fromJson(reader, UploaderMetaData.class);

			ClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()});

			try {
				Class<?> cl = loader.loadClass(meta.getMainClass());

				Constructor<Uploader> cons = (Constructor<Uploader>) cl.getConstructor(UploaderMetaData.class);

				registry.register(cons.newInstance(meta));
			} catch (Exception e) {
				throw new IOException("Unable to initialize uploader main class.");
			}
		}
	}
}
