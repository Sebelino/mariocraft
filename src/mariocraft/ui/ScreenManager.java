package mariocraft.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * A class to handle the computer screen, allowing
 * @author Erik
 *
 */
public class ScreenManager {
	
	private GraphicsDevice vc;
	
	/**
	 * Create a new ScreenManager object
	 */
	public ScreenManager() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = ge.getDefaultScreenDevice();
	}
	
	/**
	 * Return the DisplayModes supported by the computer's graphics device
	 * @return
	 */
	public DisplayMode[] getCompatibleDisplayModes() {
		return vc.getDisplayModes();
	}
	
	/**
	 * Returns the first of the display modes in the array to be
	 * supported by the computer's graphics device
	 * @param modes
	 * @return
	 */
	public DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
		DisplayMode[] compatibleModes = vc.getDisplayModes();
		for (DisplayMode d : compatibleModes) {
			for (DisplayMode m : modes) {
				if (displayModesMatch(d, m)) {
					return m;
				}
			}
		}
		return null;
	}
	
	/**
	 * Decides whether two display modes match, i.e. whether both their
	 * height and depth are equal, their bit depths are equal if only
	 * one bit depth at a time is supported, and their refresh rates
	 * are equal if they are both known.
	 * @param dm1
	 * @param dm2
	 * @return
	 */
	public boolean displayModesMatch(DisplayMode dm1, DisplayMode dm2) {
		if (dm1.getHeight() != dm2.getHeight() || dm1.getWidth() != dm2.getWidth()) {
			return false;
		}
		if (dm1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && dm2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && dm1.getBitDepth() != dm2.getBitDepth()) {
			return false;
		}
		if (dm1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && dm2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && dm1.getRefreshRate() != dm2.getRefreshRate()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Get the display mode associated with the screen manager
	 * @return
	 */
	public DisplayMode getCurrentDisplayMode() {
		return vc.getDisplayMode();
	}
	
	/**
	 * Sets the DisplayMode to the one specified by the parameter
	 * @param dm
	 */
	public void setDisplayMode(DisplayMode dm) {
		if (dm != null && vc.isDisplayChangeSupported()) {
			vc.setDisplayMode(dm);
		}
	}
	
	/**
	 * Set the window to full screen.
	 * @param dm
	 */
	public void setFullScreen(JFrame f) {
		Window w = vc.getFullScreenWindow();
		
		f.setUndecorated(true);
		f.setIgnoreRepaint(true);
		f.setResizable(false);
		vc.setFullScreenWindow(f);
		final Window frame = vc.getFullScreenWindow();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					frame.createBufferStrategy(2);
				}
			});
		} catch (Exception e) {
			System.out.println("Error while creating buffer strategy");
		}
		
		if (w != null) {
			w.dispose();
		}
	}
	
	/**
	 * Return the Graphics2D object associated with the window
	 * shown in full screen
	 * @return
	 */
	public Graphics2D getGraphics() {
		Window w = vc.getFullScreenWindow();
		if (w != null) {
			return (Graphics2D) w.getBufferStrategy().getDrawGraphics();
		}
		return null;
	}
	
	/**
	 * Update the window
	 */
	public void update() {
		Window w = vc.getFullScreenWindow();
		if (w != null) {
			BufferStrategy b = w.getBufferStrategy();
			if (!b.contentsLost()) {
				b.show();
			}
		}
	}
	
	/**
	 * Return the width of the full screen window
	 * @return
	 */
	public int getWidth() {
		Window w = vc.getFullScreenWindow();
		if (w != null) {
			return w.getWidth();
		}
		return 0;
	}
	
	/**
	 * Return the height of the full screen window
	 * @return
	 */
	public int getHeight() {
		Window w = vc.getFullScreenWindow();
		if (w != null) {
			return w.getHeight();
		}
		return 0;
	}
	
	/**
	 * Return the window currently in full screen mode
	 * @return
	 */
	public Window getFullScreenWindow() {
		return vc.getFullScreenWindow();
	}
	
	/**
	 * Remove the window currently in full screen
	 */
	public void restoreScreen() {
		Window w = vc.getFullScreenWindow();
		if (w != null) {
			w.dispose();
		}
		vc.setFullScreenWindow(null);
	}
	
	/**
	 * Create an image compatible with the screen
	 * @param width
	 * @param height
	 * @param trans
	 * @return
	 */
	public BufferedImage createCompatibleImage(int width, int height, int trans) {
		Window w = vc.getFullScreenWindow();
		if (w != null) {
			GraphicsConfiguration g = w.getGraphicsConfiguration();
			return g.createCompatibleImage(width, height, trans);
		}
		return null;
	}
}
