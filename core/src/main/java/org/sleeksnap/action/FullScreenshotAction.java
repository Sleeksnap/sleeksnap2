package org.sleeksnap.action;

import org.sleeksnap.upload.Upload;
import org.sleeksnap.upload.types.ImageUpload;
import org.sleeksnap.util.ScreenshotUtil;

import java.util.function.Consumer;

/**
 * @author Nikki
 */
public class FullScreenshotAction implements Action<Upload> {
	@Override
	public void execute(Consumer<Upload> callable) {
		callable.accept(new ImageUpload(ScreenshotUtil.capture()));
	}
}
