package org.sleeksnap.uploaders.types;

import org.sleeksnap.upload.types.ImageUpload;
import org.sleeksnap.uploaders.Uploader;

/**
 * An Uploader for Images (Like Imgur, Tinypic, etc)
 *
 * @author Nikki
 */
public interface ImageUploader extends Uploader {
	/**
	 * Upload an image to the upload destination
	 *
	 * @param image The image to upload
	 * @return The uploaded URI/URL
	 */
	public String uploadImage(ImageUpload image);
}
