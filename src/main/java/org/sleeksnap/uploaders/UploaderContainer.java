package org.sleeksnap.uploaders;

/**
 * A container storing uploader data
 *
 * @author Nikki
 */
public class UploaderContainer {

	/**
	 * The uploader
	 */
	private Uploader uploader;

	/**
	 * The Uploader MetaData
	 */
	private UploaderMetaData metaData;

	public UploaderContainer(Uploader uploader, UploaderMetaData metaData) {
		this.uploader = uploader;
		this.metaData = metaData;
	}

	/**
	 * Get the uploader
	 * @return The uploader for this container
	 */
	public Uploader getUploader() {
		return uploader;
	}

	/**
	 * Get the uploader MetaData
	 * @return The Uploader MetaData
	 */
	public UploaderMetaData getMetaData() {
		return metaData;
	}
}
