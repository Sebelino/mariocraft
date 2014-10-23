package mariocraft.ui.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import mariocraft.ui.ScreenController;
import mariocraft.ui.components.MarioCraftButton;

/**
 * The screen shown when losing the game
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class GameOverScreen extends VisibleScreen {

	public GameOverScreen(ScreenController sc) {
		super(sc);
		
setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel l = new JLabel("YOU AND YOUR FRIENDS ARE DEAD. GAME OVER.");

		MarioCraftButton b = new MarioCraftButton("Back to menu");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setStartScreen();
			}
		});
		
		add(l);
		add(b);
	}
}
