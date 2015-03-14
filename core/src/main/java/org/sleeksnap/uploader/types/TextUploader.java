package org.sleeksnap.uploader.types;

import org.sleeksnap.upload.types.TextUpload;
import org.sleeksnap.uploader.Uploader;

/**
 * An upload for Text (Like Paste.ee, Pastebin.com, etc)
 *
 * @author Nikki
 */
public interface TextUploader extends TypeUploader {

	/**
	 * Upload the text to the service
	 *
	 * @param text The text to upload
	 * @return The uploaded URI/URL
	 */
	public String uploadText(TextUpload text);

}
