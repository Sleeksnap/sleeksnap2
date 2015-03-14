package org.sleeksnap.uploader.types;

import org.sleeksnap.upload.types.URLUpload;
import org.sleeksnap.uploader.Uploader;

/**
 * An Uploader to handle URLs (Usually shorten like goo.gl)
 *
 * @author Nikki
 */
public interface URLUploader extends TypeUploader {
	/**
	 * Upload/shorten the specified URL
	 *
	 * @param url The upload containing the URL
	 * @return The uploaded/shortened URL
	 */
	public String uploadUrl(URLUpload url);
}
