package mariocraft.ui;

import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
import java.math.BigDecimal;
import java.math.RoundingMode;

import mariocraft.ui.screens.*;

/**
 * Class to handle updates and changes between screens
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class ScreenController extends JFrame {
	
	private static final String START = "s";
	private static final String GAME = "g";
	private static final String INSTRUCTIONS = "i";
	private static final String GAME_OVER = "go";
	private static final String VICTORY = "v";
	private static final String LEVEL_COMPLETE = "l";
	
	private ScreenManager s;
	private GameScreen game;
	private boolean running;
	private VisibleScreen currentScreen;
	private HashMap<String, VisibleScreen> screens;
	
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	
	/*
	 * DisplayModes that could be allowed in the game. Not used right now,
	 * the current DisplayMode of the computer is used instead.
	 */
	private DisplayMode[] modes = {
			new DisplayMode(800, 600, 32, 0),
			new DisplayMode(800, 600, 24, 0),
			new DisplayMode(800, 600, 16, 0),
			new DisplayMode(1280, 1024, 32, 0),
			new DisplayMode(1280, 1024, 24, 0),
			new DisplayMode(1280, 1024, 16, 0),
	};

	/**
	 * Create a new ScreenController with the specified ScreenManager.
	 * @param sm the manager used by this ScreenController
	 */
	public ScreenController() {
		s = new ScreenManager();
		
		DisplayMode dm = s.getCurrentDisplayMode();
		SCREEN_WIDTH = dm.getWidth();
		SCREEN_HEIGHT = dm.getHeight();
		
		final ScreenController sc = this;
		screens = new HashMap<String, VisibleScreen>();
		
		final String[] files = {"level1/", "level2/", "level3/", "level4/", "level5/", "level6/"};
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					game = new GameScreen(sc, files);
					StartScreen start = new StartScreen(sc);
					GameOverScreen gameOver = new GameOverScreen(sc);
					InstructionScreen instr = new InstructionScreen(sc);
					VictoryScreen victory = new VictoryScreen(sc);
					LevelCompleteScreen levelcomp = new LevelCompleteScreen(sc);
					screens.put(START, start);
					screens.put(GAME_OVER, gameOver);
					screens.put(INSTRUCTIONS, instr);
					screens.put(VICTORY, victory);
					screens.put(GAME, game);
					screens.put(LEVEL_COMPLETE, levelcomp);
				}
			});
		} catch (Exception e) {
			System.out.println("Error while loading screens");
			e.printStackTrace();
			System.exit(0);
		}
		
		// Initialize an easy level. This will later be moved to a
		// separate level-generator class.
		//Level l = new Level(LevelDesigner.levelOne());
	    
	    //game.setLevel(l);
		

		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Returns true if the program is running, false otherwise.
	 * @return true if the program is running, false otherwise.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Updates the screen currently in full screen mode. Should only
	 * be called if the program is running, or nothing happens.
	 */
	public void update() {
		if (currentScreen != null) {
			currentScreen.updateState();
		}
	}
	
	/**
	 * Paints the current screen if present
	 */
	public void paint() {
		if (currentScreen != null) {
			final Graphics2D g = s.getGraphics();
			if (g != null) {
				currentScreen.paint(g);
				
				// BigDecimal used to write fps with only 2 decimal points
				BigDecimal bd = BigDecimal.valueOf(Core.averageFPS).setScale(2, RoundingMode.CEILING);
				BigDecimal bd2 = BigDecimal.valueOf(Core.averageUPS).setScale(2, RoundingMode.CEILING);
				
				// Write the FPS and UPS in the top left corner
				g.setColor(Color.BLACK);
				g.drawString("FPS: " + bd, 20, 20);
				g.drawString("UPS: " + bd2, 20, 40);
				s.update();
			}
		}
	}
	
	/**
	 * Start the program. This sets running to true and calls setStartScreen().
	 */
	public void start() {
		running = true;
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					setStartScreen();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		s.setFullScreen(this);
	}
	
	/**
	 * Sets the start screen to full screen mode.
	 */
	public void setStartScreen() {
		changeScreen(START);
	}
	
	/**
	 * Sets the instruction screen to full screen mode.
	 */
	public void setInstructionScreen() {
		changeScreen(INSTRUCTIONS);
	}
	
	/**
	 * Sets the game screen to full screen mode.
	 */
	public void setGameScreen() {
		changeScreen(GAME);
	}
	
	/**
	 * Sets the game over screen to full screen mode.
	 */
	public void setGameOverScreen() {
		changeScreen(GAME_OVER);
	}
	
	public void setLevelCompleteScreen(int levelNumber) {
		((LevelCompleteScreen)screens.get(LEVEL_COMPLETE)).setLevelNumber(levelNumber);
		changeScreen(LEVEL_COMPLETE);
	}
	
	/**
	 * Sets the victory screen to full screen mode.
	 */
	public void setVictoryScreen() {
		changeScreen(VICTORY);
	}
	
	/*
	 * Change the screen to the one specified by the String parameter
	 */
	private void changeScreen(String screen) {
		final String s = screen;
		if (currentScreen != null) {
			currentScreen.setVisible(false);
		}
		currentScreen = screens.get(s);
		setContentPane(currentScreen);
		currentScreen.setVisible(true);
		currentScreen.requestFocusInWindow();
	}
	
	/**
	 * Exit the program. This sets running to false and removes any
	 * screen currently in full screen mode.
	 */
	public void exit() {
		running = false;
		s.restoreScreen();
	}
}
