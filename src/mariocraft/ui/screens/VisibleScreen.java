package mariocraft.ui.screens;

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

import mariocraft.ui.ScreenController;
import mariocraft.util.ImageUtil;

/**
 * The super class of all the screens in the game
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public abstract class VisibleScreen extends JPanel {
	
	private static final String DEFAULT_IMAGE = "bg1.jpg";
	
	protected ScreenController controller;
	private static BufferedImage DEFAULT_BACKGROUND = ImageUtil.resize(ImageUtil.loadImage(DEFAULT_IMAGE),
            										ScreenController.SCREEN_WIDTH,
            										ScreenController.SCREEN_HEIGHT);
	private BufferedImage background;
	
	public VisibleScreen(ScreenController sc) {	
		this(sc, null);
	}
	
	public VisibleScreen(ScreenController sc, BufferedImage i) {
		super(true);
		controller = sc;
		background = i;
	}
	
	public void updateState() {}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (background == null) {
			g.drawImage(DEFAULT_BACKGROUND, 0, 0, null);
		} else {
			g.drawImage(background, 0, 0, null);
		}
	}
}