package mariocraft.ui.screens;

import javax.swing.*;
import java.awt.event.*;
import mariocraft.ui.ScreenController;
import mariocraft.ui.components.*;

/**
 * The screen showed when the user completes the whole game
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class VictoryScreen extends VisibleScreen {

	public VictoryScreen(ScreenController sc) {
		super(sc);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel l = new JLabel("A WINNER IS YOU!");

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
