package org.sleeksnap.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Base64 Encoder/Decoder
 * Modified by Nikki for use in Sleeksnap
 *
 * @author Emil Hernvall
 * @author Nikki
 * @link https://gist.github.com/EmilHernvall/953733
 */
public class Base64 {
	/**
	 * The encoder character table
	 */
	private static final char[] ENCODER_TABLE = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
	};

	/**
	 * The decoder data table
	 */
	private static final int[] DECODER_TABLE = {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54,
			55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2,
			3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
			20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30,
			31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
			48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
	};

	/**
	 * Encode a byte array into a base64 string
	 *
	 * @param data The byte array to encode
	 * @return The base64 encoded string
	 */
	public static String encode(byte[] data) {
		StringBuilder buffer = new StringBuilder();
		int pad = 0;
		for (int i = 0; i < data.length; i += 3) {
			int b = ((data[i] & 0xFF) << 16) & 0xFFFFFF;
			if (i + 1 < data.length) {
				b |= (data[i + 1] & 0xFF) << 8;
			} else {
				pad++;
			}
			if (i + 2 < data.length) {
				b |= (data[i + 2] & 0xFF);
			} else {
				pad++;
			}

			for (int j = 0; j < 4 - pad; j++) {
				int c = (b & 0xFC0000) >> 18;
				buffer.append(ENCODER_TABLE[c]);
				b <<= 6;
			}
		}
		for (int j = 0; j < pad; j++) {
			buffer.append("=");
		}
		return buffer.toString();
	}

	/**
	 * Encode the data from an InputStream to base64
	 *
	 * @param input The InputStream to read from
	 * @return The encoded base64 string
	 * @throws IOException If an error occurs while reading from the InputStream
	 */
	public static String encode(InputStream input) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while (true) {
			int read = input.read(buf, 0, buf.length);

			if (read == -1) {
				break;
			}

			buffer.write(buf, 0, read);
		}
		return encode(buffer.toByteArray());
	}

	/**
	 * Decode a base64 string into a byte array
	 *
	 * @param data The base64 string to decode
	 * @return The resulting byte array
	 */
	public static byte[] decode(String data) {
		byte[] bytes = data.getBytes();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		for (int i = 0; i < bytes.length; ) {
			int b = 0;
			if (DECODER_TABLE[bytes[i]] != -1) {
				b = (DECODER_TABLE[bytes[i]] & 0xFF) << 18;
			}
			// skip unknown characters
			else {
				i++;
				continue;
			}
			if (i + 1 < bytes.length && DECODER_TABLE[bytes[i + 1]] != -1) {
				b = b | ((DECODER_TABLE[bytes[i + 1]] & 0xFF) << 12);
			}
			if (i + 2 < bytes.length && DECODER_TABLE[bytes[i + 2]] != -1) {
				b = b | ((DECODER_TABLE[bytes[i + 2]] & 0xFF) << 6);
			}
			if (i + 3 < bytes.length && DECODER_TABLE[bytes[i + 3]] != -1) {
				b = b | (DECODER_TABLE[bytes[i + 3]] & 0xFF);
			}
			while ((b & 0xFFFFFF) != 0) {
				int c = (b & 0xFF0000) >> 16;
				buffer.write((char) c);
				b <<= 8;
			}
			i += 4;
		}
		return buffer.toByteArray();
	}
}