package org.sleeksnap.util;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

/**
 * A utility to make screen boundary checks easier
 *
 * @author Nikki
 */
public class DisplayUtil {

	/**
	 * Get the real screen size, including multiple screens
	 *
	 * @return The screen size
	 */
	public static Rectangle getScreenSize() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

		Rectangle allScreenBounds = new Rectangle();
		for (GraphicsDevice screen : screens) {
			Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();

			allScreenBounds.width += screenBounds.width;
			allScreenBounds.height = Math.max(allScreenBounds.height, screenBounds.height);

			if (screenBounds.x < allScreenBounds.y || screenBounds.y < allScreenBounds.y) {
				allScreenBounds.x = Math.min(allScreenBounds.x, screenBounds.x);
				allScreenBounds.y = Math.min(allScreenBounds.y, screenBounds.y);
			}
		}
		return allScreenBounds;
	}

	/**
	 * Get the screen bounds of each screen
	 *
	 * @return The screen size
	 */
	public static Rectangle[] getAllScreenBounds() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

		Rectangle[] allScreenBounds = new Rectangle[screens.length];
		for (int i = 0; i < screens.length; i++) {
			allScreenBounds[i] = screens[i].getDefaultConfiguration().getBounds();
		}
		return allScreenBounds;
	}
}
