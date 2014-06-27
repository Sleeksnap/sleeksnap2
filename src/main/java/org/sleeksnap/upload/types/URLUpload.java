package org.sleeksnap.upload.types;

import org.sleeksnap.upload.Upload;

import java.net.URL;

/**
 * An Upload representing URLs
 *
 * @author Nikki
 */
public class URLUpload implements Upload {

	/**
	 * The URL to upload/shorten
	 */
	private final URL url;

	/**
	 * Construct a new URL Upload
	 *
	 * @param url The URL to upload/shorten
	 */
	public URLUpload(URL url) {
		this.url = url;
	}

	/**
	 * Get the URL for this upload
	 *
	 * @return The URL
	 */
	public URL getUrl() {
		return url;
	}
}
