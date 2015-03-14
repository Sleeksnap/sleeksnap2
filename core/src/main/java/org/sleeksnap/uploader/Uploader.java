package org.sleeksnap.uploader;

/**
 * A basic uploader. This just stores standard uploader data.
 *
 * @author Nikki
 */
public abstract class Uploader {

	/**
	 * The Uploader MetaData
	 */
	private UploaderMetaData metaData;

	public Uploader(UploaderMetaData metaData) {
		this.metaData = metaData;
	}

	/**
	 * Get the uploader MetaData
	 * @return The Uploader MetaData
	 */
	public UploaderMetaData getMetaData() {
		return metaData;
	}
}
