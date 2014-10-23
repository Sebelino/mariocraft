package mariocraft.ui.screens;

import javax.swing.*;

import mariocraft.ui.ScreenController;
import mariocraft.ui.components.*;
import mariocraft.util.*;

import java.awt.*;
import java.awt.event.*;

/**
 * This class represents the first screen in the program, where the user
 * can choose to read instructions, start the game or end the game.
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class StartScreen extends VisibleScreen {
	
	private static final String IMAGE_NAME = "startscreen.jpg";
	
	private static final int FILLING_TOP = 300;
	private static final int FILLING_BETWEEN = 30;
	
	private MarioCraftButton startButton;
	private MarioCraftButton instructionButton;
	private MarioCraftButton endButton;
	
	public StartScreen(ScreenController sc) {
		super(sc, ImageUtil.resize(ImageUtil.loadImage(IMAGE_NAME),
	            ScreenController.SCREEN_WIDTH,
	            ScreenController.SCREEN_HEIGHT));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel fill = new JPanel();
		fill.setMaximumSize(new Dimension(0, FILLING_TOP));
		add(fill);
		
		startButton = new MarioCraftButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setGameScreen();
			}
		});
		add(startButton);
		
		fill = new JPanel();
		fill.setMaximumSize(new Dimension(0, FILLING_BETWEEN));
		add(fill);
		
		instructionButton = new MarioCraftButton("Instructions");
		instructionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setInstructionScreen();
			}
		});
		add(instructionButton);
		
		fill = new JPanel();
		fill.setMaximumSize(new Dimension(0, FILLING_BETWEEN));
		add(fill);
		
		endButton = new MarioCraftButton("End");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.exit();
			}
		});
		add(endButton);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
