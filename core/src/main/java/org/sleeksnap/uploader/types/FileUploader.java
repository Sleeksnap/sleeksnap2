package org.sleeksnap.uploader.types;

import org.sleeksnap.upload.ByteStreamUpload;
import org.sleeksnap.uploader.Uploader;

/**
 * An Uploader for File services (Dropbox, Filebin, FTP, etc)
 * These can take Images, Text, and Files, so they get a ByteStreamUpload instance instead.
 *
 * @author Nikki
 */
public interface FileUploader extends TypeUploader {

	/**
	 * Upload a file (images, text, files) to the upload service
	 *
	 * @param upload The file to upload
	 * @return The uploaded URI/URL
	 */
	public String uploadFile(ByteStreamUpload upload);

}
