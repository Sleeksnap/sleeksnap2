package org.sleeksnap.uploader.types;

import org.sleeksnap.upload.types.ImageUpload;
import org.sleeksnap.uploader.Uploader;

/**
 * An Uploader for Images (Like Imgur, Tinypic, etc)
 *
 * @author Nikki
 */
public interface ImageUploader extends TypeUploader {

	/**
	 * Upload an image to the upload destination
	 *
	 * @param image The image to upload
	 * @return The uploaded URI/URL
	 */
	public String uploadImage(ImageUpload image);

}
