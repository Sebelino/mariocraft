package mariocraft.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.border.*;

import mariocraft.model.Images;
import mariocraft.util.ImageUtil;

/**
 * A button in awesome MarioCraft design
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class MarioCraftButton extends JButton {
	
	private static final int BORDER_WIDTH = 40;
	private static final int BORDER_HEIGHT = 20;
	private static final int BUTTON_WIDTH = 20;
	private static final int BUTTON_HEIGHT = 5;
	private static final BufferedImage image1 = ImageUtil.resize(Images.BOULDER, BORDER_HEIGHT, BORDER_HEIGHT);
	
	/**
	 * Create a button without text.
	 */
	public MarioCraftButton() {
		this("");
	}
	
	/**
	 * Create a MarioCraftButton with the specified text
	 * @param text
	 */
	public MarioCraftButton(String text) {
		super(text);
		setBackground(Color.RED);
		Border b1 = BorderFactory.createMatteBorder(BORDER_HEIGHT, BORDER_WIDTH, BORDER_HEIGHT, BORDER_WIDTH, new ImageIcon(image1));
		Border b2 = BorderFactory.createMatteBorder(BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH, Color.RED);
		Border combo = BorderFactory.createCompoundBorder(b1, b2);
		setBorder(combo);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
	}
	
}
