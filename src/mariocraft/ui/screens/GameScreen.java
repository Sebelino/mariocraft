package mariocraft.ui.screens;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import mariocraft.ui.ScreenController;
import mariocraft.model.Block;
import mariocraft.model.Boulder;
import mariocraft.model.KeyState;
import mariocraft.model.Level;
import mariocraft.model.LevelGenerator;
import mariocraft.ui.components.*;

/**
 * Class to handle the game updates, changes between levels and
 * pausing/resuming.
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class GameScreen extends VisibleScreen {
	
	private static int START_LIVES = 30;
	
	private KeyState keys;
	LevelGenerator lg;
	private Level level;
	private PauseMenu pmenu;
	private boolean isPaused;
	private int numLives;

	/**
	 * Creates a game screen.
	 * 
	 * @param sc Screen controller
	 * @param ks Contains keyboard controls
	 */
	public GameScreen(ScreenController sc, String[] files) {
		super(sc);
		
		numLives = START_LIVES;
		
		pmenu = new PauseMenu();
		
		keys = new KeyState();
		
		addKeyListener(keys);
		addMouseListener(pmenu);	
		lg = new LevelGenerator(files);
		setNextLevel();
	}
	
	/**
	 * Sets the next level in the game. Should only be called if there
	 * are more levels in the LevelGenerator
	 */
	public void setNextLevel() {
		try {
			lg.generateNext();
			setLevel(lg.getNextLevel());
		} catch (IOException e) {
			System.err.println("Error while loading level");
			e.printStackTrace();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					controller.exit();
				}
			});
		}
	}
	
	/**
	 * Sets the current level to the given parameter
	 * @param l
	 */
	public void setLevel(Level l) {
	    keys.reset();
	    Block.unresize();
	    Boulder.unresize();
		l.setKeyState(keys);
		l.setScreenSize(ScreenController.SCREEN_WIDTH, ScreenController.SCREEN_HEIGHT);
		level = l;
	}
	
	/**
	 * Resets the game, giving the player all lives back and resetting
	 * all levels.
	 */
	public void resetGame() {
		numLives = START_LIVES;
		lg.resetGame();
	}
	
	/**
	 * Updates the screen.
	 */
	public void updateState() {
		updatePause();
		
		if (!isPaused) {
			if (level.isFinished()) {
				if (level.isVictorious()) {
					if (!lg.hasMoreLevels()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								controller.setVictoryScreen();
							}
						});
					} else {
					    SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                controller.setLevelCompleteScreen(lg.levelNumber());
                            }
                        });
						setNextLevel();
						
					}
				} else {
					numLives--;
					if (numLives <= 0) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								controller.setGameOverScreen();
							}
						});
						resetGame();
						setNextLevel();
					} else {
						level.resetProtagonist();
					}
				}
			} else {
				level.update();
			}
		}
	}
	
	/**
	 * Paint contents of the level to the screen
	 */
	public void paint(Graphics g) {
		super.paint(g);
		level.paint(g);
		if (isPaused) {
			pmenu.draw(g);
		}
		g.setColor(Color.BLACK);
		g.setFont(new MarioCraftFont());
		g.drawString(Integer.valueOf(numLives).toString()+"x", ScreenController.SCREEN_WIDTH - 50, 50);
	}
	
	/**
	 * Updates the pause state of the game. Makes a menu popup if the
	 * user has just pressed escape and makes it go away if the user has
	 * just pressed escape for a second time.
	 */
	private void updatePause() {
		if (isPaused) {
			if (!keys.escape) {
				unpause();
			}
		} else {
			if (keys.escape) {
				pause();
			}
		}
	}
	
	/**
	 * Pause the game
	 */
	public void pause() {
		isPaused = true;
		pmenu.setVisible(true);
	}
	
	/**
	 * Unpause the game
	 */
	public void unpause() {
		isPaused = false;
		pmenu.setVisible(false);
	}
	
	/*
	 * The menu showed when pausing the game
	 */
	private class PauseMenu extends JPanel implements MouseListener {
		private PauseMenuButton resume;
		private PauseMenuButton startMenu;
		private PauseMenuButton end;
		
		private final int X_POS;
		private final int Y_POS;
		private final int WIDTH;
		private final int HEIGHT;
		private final Color trans = new Color(0f, 0f, 0f, 0.5f);
		
		/**
		 * Create a new pause menu
		 */
		public PauseMenu() {

			HEIGHT = 370;
			WIDTH = 250;
			X_POS = (ScreenController.SCREEN_WIDTH - WIDTH) / 2;
			Y_POS = (ScreenController.SCREEN_HEIGHT - HEIGHT) / 2;
			
			setBackground(trans);
			setOpaque(false);
			
			resume = new PauseMenuButton("Resume Game", X_POS + 35, Y_POS + 50, 175, 45);
			
			startMenu = new PauseMenuButton("Main Menu", X_POS + 35, Y_POS + 150, 175, 45);

			end = new PauseMenuButton("End Game", X_POS + 35, Y_POS + 250, 175, 45);
		}
		
		/**
		 * Check if user clicked the menu
		 */
		public void mouseClicked(MouseEvent e) {
			if (isPaused) {
				int x = e.getX();
				int y = e.getY();
				if (resume.isOnButton(x, y)) {
					keys.escape = false;
					unpause();
				} else if (startMenu.isOnButton(x, y)) {
					keys.reset();
					controller.setStartScreen();
				} else if (end.isOnButton(x, y)) {
					controller.exit();
				}
			}
		}
		
		public void mousePressed(MouseEvent e) {}
		
		public void mouseReleased(MouseEvent e) {}
		
		public void mouseEntered(MouseEvent e) {}
		
		public void mouseExited(MouseEvent e) {}
		
		/**
		 * Draw the menu to the screen
		 * @param g
		 */
		public void draw(Graphics g) {
			g.setColor(trans);
			g.fillRect(X_POS, Y_POS, WIDTH, HEIGHT);
			resume.draw(g);
			startMenu.draw(g);
			end.draw(g);
		}
	}
	
	private static class PauseMenuButton {
		
		private String text;
		private Rectangle rect;
		private static Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
		private static Color fg = Color.RED;
		private static Color bg = new Color(0f, 0f, 0f, 0.8f);
		
		public PauseMenuButton(String text, int x, int y, int w, int h) {
			this.text = text;
			rect = new Rectangle(x, y, w, h);
		}
		
		/**
		 * Returns true if the location is on the button
		 * @param x
		 * @param y
		 * @return
		 */
		public boolean isOnButton(int x, int y) {
			return rect.contains(x, y);
		}
		
		/**
		 * Draw this button.
		 * @param g
		 */
		public void draw(Graphics g) {
			g.setColor(bg);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			g.setColor(fg);
			g.setFont(font);
			g.drawString(text, rect.x + 21, rect.y + rect.height/2 + 10);
		}
	}
}
