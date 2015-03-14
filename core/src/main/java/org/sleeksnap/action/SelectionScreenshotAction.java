package org.sleeksnap.action;

import org.sleeksnap.gui.SelectionWindow;
import org.sleeksnap.upload.Upload;
import org.sleeksnap.upload.types.ImageUpload;
import org.sleeksnap.util.DisplayUtil;
import org.sleeksnap.util.ScreenshotUtil;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

/**
 * @author Nikki
 */
public class SelectionScreenshotAction implements Action<Upload> {

	@Override
	public void execute(Consumer<Upload> callable) {
		Rectangle area = DisplayUtil.getScreenSize();

		BufferedImage image = ScreenshotUtil.captureArea(area);
		SelectionWindow window = new SelectionWindow(area, image);

		window.setVisible(true);

		window.addSelectionCallback((rect) -> callable.accept(new ImageUpload(image.getSubimage(rect.x, rect.y, rect.width, rect.height))));
	}

}
