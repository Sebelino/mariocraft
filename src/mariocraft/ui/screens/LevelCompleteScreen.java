package mariocraft.ui.screens;

import javax.swing.*;
import java.awt.event.*;

import mariocraft.ui.ScreenController;
import mariocraft.ui.components.*;

/**
 * The screen showed when the user completes a level
 * @author Erik Odenman
 *
 */
@SuppressWarnings("serial")
public class LevelCompleteScreen extends VisibleScreen
								implements MouseListener, KeyListener {
	
	private JLabel message;
	
	public LevelCompleteScreen(ScreenController sc) {
		super(sc);
		
		addMouseListener(this);
		addKeyListener(this);
		
		message = new JLabel();
		message.setAlignmentX(CENTER_ALIGNMENT);
		message.setFont(new MarioCraftFont());
		add(message);
		
		JLabel message2 = new JLabel("Press any key to continue.");
		message2.setAlignmentX(CENTER_ALIGNMENT);
		message2.setFont(new MarioCraftFont());
		add(message2);
	}
	
	/**
	 * Set the number of the level completed
	 * @param n
	 */
	public void setLevelNumber(int n) {
		message.setText("Congratulations! You completed level "+n+".");
	}
	
	public void mousePressed(MouseEvent e) {
		controller.setGameScreen();
	}
	
	public void keyPressed(KeyEvent e) {
		controller.setGameScreen();
	}
	
	public void keyReleased(KeyEvent e) {}
	
	public void keyTyped(KeyEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
}
