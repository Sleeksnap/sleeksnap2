package org.sleeksnap.uploaders.types;

import org.sleeksnap.upload.types.URLUpload;
import org.sleeksnap.uploaders.Uploader;

/**
 * An Uploader to handle URLs (Usually shorten like goo.gl)
 *
 * @author Nikki
 */
public interface URLUploader extends Uploader {
	/**
	 * Upload/shorten the specified URL
	 *
	 * @param url The upload containing the URL
	 * @return The uploaded/shortened URL
	 */
	public String uploadUrl(URLUpload url);
}
