package mariocraft.ui.screens;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import mariocraft.ui.ScreenController;
import mariocraft.ui.components.*;
import mariocraft.util.ImageUtil;

/**
 * The screen used to show instructions to the user
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class InstructionScreen extends VisibleScreen {
	
	private static final String IMAGE_1 = "instructions.png";
	
	private static final int INSTRUCTION_SCREEN_HEIGHT = 600;
	private static final int INSTRUCTION_SCREEN_WIDTH = 800;
	
	CardLayout cl;
	
	public InstructionScreen(ScreenController sc) {
		super(sc);
		
		BufferedImage image1 = ImageUtil.resize(ImageUtil.loadImage(IMAGE_1),
				INSTRUCTION_SCREEN_WIDTH, INSTRUCTION_SCREEN_HEIGHT);
		
		JPanel instructionPanel = new JPanel();
		instructionPanel.setMaximumSize(new Dimension(INSTRUCTION_SCREEN_WIDTH, INSTRUCTION_SCREEN_HEIGHT));
		cl = new CardLayout();
		
		instructionPanel.setLayout(cl);
		instructionPanel.add(new JLabel(new ImageIcon(image1)), "1");
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton b = new MarioCraftButton("Back to menu");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setStartScreen();
			}
		});
		
		add(instructionPanel);
		add(b);
	}
}
