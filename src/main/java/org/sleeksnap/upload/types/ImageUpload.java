package org.sleeksnap.upload.types;

import org.sleeksnap.upload.ByteStreamUpload;
import org.sleeksnap.upload.Upload;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An Upload representing Images
 *
 * @author Nikki
 */
public class ImageUpload implements Upload, ByteStreamUpload {

	/**
	 * The image to upload
	 */
	private final BufferedImage image;

	/**
	 * Construct a new Image Upload
	 *
	 * @param image The image to upload
	 */
	public ImageUpload(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Get the image from this upload
	 *
	 * @return The Image to upload
	 */
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public InputStream asInputStream() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", output);
		return new ByteArrayInputStream(output.toByteArray());
	}
}
