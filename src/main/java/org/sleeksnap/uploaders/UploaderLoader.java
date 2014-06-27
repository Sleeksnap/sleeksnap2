package org.sleeksnap.uploaders;

import java.io.File;
import java.io.IOException;

/**
 * A Loader to load uploaders from JAR files
 *
 * @author Nikki
 */
public class UploaderLoader {

	private final UploaderRegistry registry;

	private final File directory;

	public UploaderLoader(UploaderRegistry registry, File directory) {
		this.registry = registry;
		this.directory = directory;
	}

	public void load() throws IOException {
		for (File f : directory.listFiles()) {
			if (f.getName().endsWith("jar") || f.getName().endsWith("zip")) {
				// TODO loading
			}
		}
	}
}
