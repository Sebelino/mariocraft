package mariocraft.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import mariocraft.util.ImageUtil;

/**
 * Class used to control an image shown in the background.
 * The image is looped in infinity in all directions.
 * @author Erik
 *
 */
public class BackgroundManager {
	private BufferedImage background;
	private int imageWidth;
	private int imageHeight;
	
	/**
     * Creates a BackGroundManager with the image with the specified
     * file name
     * @param fileName
     */
	public BackgroundManager(String fileName) {
		background = ImageUtil.loadImage(fileName);
		imageWidth = background.getWidth();
		imageHeight = background.getHeight();
	}
	
    /**
     * Paint the background image. If the image has gone off screen
     * another image is panted next to it to cover up
     * @param g the draw Graphics
     * @param xstart The x start position of the draw
     * @param ystart The y start position of the draw
     */
	public void paint(Graphics g, int xstart, int ystart) {
		xstart %= imageWidth;
		ystart %= imageHeight;
		
		if (xstart < 0) {
			xstart += imageWidth;
		}
		if (ystart < 0) {
			ystart += imageHeight;
		}
		
		g.drawImage(background, -xstart, -ystart, null);
		g.drawImage(background, -xstart + imageWidth, -ystart, null);
		g.drawImage(background, -xstart, -ystart + imageHeight, null);
		g.drawImage(background, -xstart + imageWidth, -ystart + imageHeight, null);
	}
}
