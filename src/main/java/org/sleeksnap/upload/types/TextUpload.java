package org.sleeksnap.upload.types;

import org.sleeksnap.upload.ByteStreamUpload;
import org.sleeksnap.upload.Upload;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * An Upload representing Text/Strings
 *
 * @author Nikki
 */
public class TextUpload implements Upload, ByteStreamUpload {

	/**
	 * The Text to upload
	 */
	private final String text;

	/**
	 * Construct a new Text upload
	 *
	 * @param text The text to upload
	 */
	public TextUpload(String text) {
		this.text = text;
	}

	/**
	 * Get the upload text
	 *
	 * @return The upload text
	 */
	public String getText() {
		return text;
	}

	@Override
	public InputStream asInputStream() {
		return new ByteArrayInputStream(text.getBytes());
	}
}
