package org.sleeksnap.uploaders;

/**
 * Represents an uploader's information
 *
 * @author Nikki
 */
public class UploaderMetaData {
	/**
	 * The Uploader's main class
	 */
	private String mainClass;

	/**
	 * The Uploader's name
	 */
	private String name;

	/**
	 * A list of Uploader authors
	 */
	private String[] authors;

	/**
	 * The Uploader information URL
	 */
	private String url;

	/**
	 * Get the uploader's main class
	 * @return The main class attribute
	 */
	public String getMainClass() {
		return mainClass;
	}

	/**
	 * Get the uploader's name
	 * @return The uploader name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the uploader author list
	 * @return The author list
	 */
	public String[] getAuthors() {
		return authors;
	}

	/**
	 * Get the uploader's information  url
	 * @return The information url
	 */
	public String getUrl() {
		return url;
	}
}
