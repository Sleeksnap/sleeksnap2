package org.sleeksnap.upload.types;

import org.sleeksnap.upload.ByteStreamUpload;
import org.sleeksnap.upload.Upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An Upload representing a File
 *
 * @author Nikki
 */
public class FileUpload implements Upload, ByteStreamUpload {

	/**
	 * The file to upload
	 */
	private final File file;

	/**
	 * Construct a new File Upload
	 *
	 * @param file The file to upload
	 */
	public FileUpload(File file) {
		this.file = file;
	}

	/**
	 * Get the file to upload
	 *
	 * @return The file object
	 */
	public File getFile() {
		return file;
	}

	@Override
	public InputStream asInputStream() throws IOException {
		return new FileInputStream(file);
	}
}
