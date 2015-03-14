package org.sleeksnap.gui;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
/**
 * A {@link javax.swing.JFrame} which allows us to push an image onto it for selection.
 *
 * @author Nikki
 *
 */
public class SelectionWindow extends JFrame {

	/**
	 * A callback which is called when selections are finished.
	 */
	public static interface SelectionCallback {
		/**
		 * The selection callback.
		 *
		 * @param rectangle The final dimensions/bounds.
		 */
		public void selectionFinished(Rectangle rectangle);
	}

	/**
	 * A callback which is called when selections are canceled
	 */
	public static interface SelectionCancelCallback {
		/**
		 * The cancelation callback
		 */
		public void selectionCanceled();
	}

	/**
	 * An {@link java.awt.event.KeyAdapter} to handle the cancel (ESCAPE) key
	 */
	private class ScreenCancelListener extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				close();
			}
		}
	}

	/**
	 * A <code>javax.swing.event.MouseInputAdapter</code> which listens for
	 * selections
	 *
	 * @author Nikki
	 */
	private class ScreenSelectionListener extends MouseInputAdapter {
		public void mouseDragged(MouseEvent e) {
			updateSize(e);
		}

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				e.consume();
				return;
			}
			currentRect = new Rectangle(e.getX(), e.getY(), 0, 0);
			updateDrawableRect(getWidth(), getHeight());
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			// Cancel button
			if (e.getButton() == MouseEvent.BUTTON3) {
				e.consume();
				if (currentRect != null) {
					currentRect = null;
					repaint();
				} else {
					close();
				}
				return;
			}

			if (currentRect == null) {
				return;
			}

			updateSize(e);

			try {
				capture();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/*
		 * Update the size of the current rectangle and call repaint. Because
		 * currentRect always has the same origin, translate it if the width or
		 * height is negative.
		 * 
		 * For efficiency (though that isn't an issue for this program), specify
		 * the painting region using arguments to the repaint() call.
		 */
		private void updateSize(MouseEvent e) {
			if (currentRect == null) {
				return;
			}
			currentRect.setSize(e.getX() - currentRect.x, e.getY()
					- currentRect.y);
			updateDrawableRect(getWidth(), getHeight());
			repaint();
		}
	}

	/**
	 * The Image to draw (usually a screenshot from the screen)
	 */
	private BufferedImage image;

	/**
	 * The screen area of this image
	 */
	private Rectangle area;

	/**
	 * The selected rectangle
	 */
	private Rectangle currentRect;

	/**
	 * The rectangle coordinates to draw
	 */
	private Rectangle rectToDraw = null;

	/**
	 * A list of callbacks for this window
	 */
	private List<SelectionCallback> callbacks = new LinkedList<SelectionCallback>();

	/**
	 * A list of callbacks for this window
	 */
	private List<SelectionCancelCallback> cancelCallbacks = new LinkedList<SelectionCancelCallback>();

	/**
	 * The image to draw to as an easy way of "double buffering"
	 */
	private Image buffer;

	/**
	 * The Graphics buffer
	 */
	private Graphics g;

	/**
	 * Construct a new selection window
	 *
	 * @param area  The area of the selection image
	 * @param image The image to select from
	 */
	public SelectionWindow(Rectangle area, BufferedImage image) {
		this.area = area;
		this.image = image;

		setPreferredSize(new Dimension(area.width, area.height));
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		setBounds(area);

		setUndecorated(true);

		ScreenSelectionListener listener = new ScreenSelectionListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);

		addKeyListener(new ScreenCancelListener());

		requestFocus();
	}

	/**
	 * Add a callback for when selection is finished
	 * @param callback The callback
	 */
	public void addSelectionCallback(SelectionCallback callback) {
		callbacks.add(callback);
	}

	/**
	 * Add a callback for when selection is canceled (user pressing ESCAPE or right clicking without a selection)
	 * @param callback The callback
	 */
	public void addCancelCallback(SelectionCancelCallback callback) {
		cancelCallbacks.add(callback);
	}

	/**
	 * Crop and call the callbacks, then dispose of this window.
	 */
	public void capture() {
		for (SelectionCallback callback : callbacks) {
			callback.selectionFinished(rectToDraw);
		}

		System.out.println("Finished");

		setVisible(false);
		dispose();
	}

	/**
	 * Close and cancel this window
	 */
	public void close() {
		for (SelectionCancelCallback callback : cancelCallbacks) {
			callback.selectionCanceled();
		}
		dispose();
	}

	/**
	 * The rectangle draw color
	 */
	private static Color rectColor = new Color(0, 0, 0, 50);

	/**
	 * Paint the window to the buffer
	 */
	public void paint() {
		g.clearRect(area.x, area.y, area.width, area.height);
		g.drawImage(image, 0, 0, null);

		if (currentRect != null) {
			g.setColor(rectColor);
			g.drawRect(rectToDraw.x, rectToDraw.y, rectToDraw.width, rectToDraw.height);
			g.fillRect(rectToDraw.x, rectToDraw.y, rectToDraw.width, rectToDraw.height);
		}
	}

	@Override
	public void paint(Graphics gr) {
		if (buffer == null) {
			this.buffer = createImage(area.width, area.height);
			this.g = buffer.getGraphics();
		}
		// paint to the buffer
		paint();
		// draw the buffer
		gr.drawImage(buffer, 0, 0, this);
	}

	/**
	 * Update the rectangle drawn over the image
	 *
	 * @param compWidth  The component width
	 * @param compHeight The component height
	 */
	private void updateDrawableRect(int compWidth, int compHeight) {
		if (currentRect == null) {
			return;
		}
		int x = currentRect.x;
		int y = currentRect.y;
		int width = currentRect.width;
		int height = currentRect.height;

		// Make the width and height positive, if necessary.
		if (width < 0) {
			width = 0 - width;
			x = x - width + 1;
			if (x < 0) {
				width += x;
				x = 0;
			}
		}
		if (height < 0) {
			height = 0 - height;
			y = y - height + 1;
			if (y < 0) {
				height += y;
				y = 0;
			}
		}

		// The rectangle shouldn't extend past the drawing area.
		if ((x + width) > compWidth) {
			width = compWidth - x;
		}
		if ((y + height) > compHeight) {
			height = compHeight - y;
		}

		// Update rectToDraw after saving old value.
		if (rectToDraw != null) {
			rectToDraw.setBounds(x, y, width, height);
		} else {
			rectToDraw = new Rectangle(x, y, width, height);
		}
	}

	public BufferedImage getImage() {
		return image;
	}
}