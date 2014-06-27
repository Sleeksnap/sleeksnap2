package org.sleeksnap.upload;

import java.io.IOException;
import java.io.InputStream;

/**
 * An Upload which can be converted to an InputStream
 *
 * @author Nikki
 */
public interface ByteStreamUpload extends Upload {
	/**
	 * Get the upload contents as an InputStream.
	 *
	 * @return The new InputStream
	 * @throws IOException If an error occurred while converting
	 */
	public InputStream asInputStream() throws IOException;
}
