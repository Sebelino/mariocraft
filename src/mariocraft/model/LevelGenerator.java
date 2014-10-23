package mariocraft.model;

import java.io.*;
import java.util.HashSet;

import mariocraft.MarioCraft;
import mariocraft.geom.Vector2D;

/**
 * A class for generating levels.
 * 
 * @author Erik Odenman
 * @author Sebastian Olsson
 * @version 2011-05-05
 */
public class LevelGenerator {
	
	private static final String LEVEL_PATH = "resources/levels/";
	
	private BufferedReader r;
	private String[] levels;
	private Level nextLevel;
	private int levelIndex;
	
    /**
     * Creates a new LevelGenerator reading levels from the directories
     * specified in the String[]
     * @param files The levels to be read
     */
	public LevelGenerator(String[] files) {
		if (files.length == 0) {
			throw new IllegalArgumentException("No levels found");
		}
		levels = files;
		levelIndex = 0;
	}
	
    /**
     * Generate the next level. Should only be used if hasMoreLevels()
     * returns true or a NullPointerException will be thrown
     * @throws IOException
     */
	public void generateNext() throws IOException {
		r = new BufferedReader(new InputStreamReader(MarioCraft.class.getResourceAsStream(LEVEL_PATH + levels[levelIndex]+"map.txt")));
		
		String line = r.readLine();
		int len = line.length();
		int lineNum = 0;
		
		LevelConstants lc = new LevelConstants(MarioCraft.class.getResourceAsStream(LEVEL_PATH + levels[levelIndex]+"mechanics.txt"));
		final float BLOCK_SIZE = lc.BLOCK_SIZE;
		
		BlockManager bm = new BlockManager(line.length(), BLOCK_SIZE);
		LevelDesigner next = new LevelDesigner();
		HashSet<Ball> balls = new HashSet<Ball>();
		HashSet<Spikes> spikes = new HashSet<Spikes>();
		HashSet<MovingPlatform> platforms = new HashSet<MovingPlatform>();
		MovingPattern[] patterns = new MovingPattern[lc.MOVING_PLATFORM_WIDTH.size()];
		for(int i = 0;i < patterns.length;i++) {
		    int patternLength = lc.MOVING_PLATFORM_PATTERN_X.get(i).length;
		    Vector2D[] velocities = new Vector2D[patternLength];
		    int[] periods = new int[patternLength];
		    for(int j = 0;j < patternLength;j++) {
	            periods[j] = lc.MOVING_PLATFORM_PERIODS.get(i)[j];
		        velocities[j] = new Vector2D(lc.MOVING_PLATFORM_PATTERN_X.get(i)[j]/periods[j], lc.MOVING_PLATFORM_PATTERN_Y.get(i)[j]/periods[j]);
		    }
		    patterns[i] = new MovingPattern(velocities, periods);
		}
		int indexPlatform = 0;
		
		do {
			if (line.length() > len) {
				throw new IllegalArgumentException("Line exceeds length of level");
			}
			char[] chars = line.toCharArray();
			for (int i = 0; i < chars.length; i++) {
			    float x = BLOCK_SIZE*i+BLOCK_SIZE/2;
	            float y = BLOCK_SIZE*lineNum+BLOCK_SIZE/2;
				if (chars[i] == '#') { // It's a block
					bm.addBlockAtIndex(new Block(x, y, BLOCK_SIZE, BLOCK_SIZE), i);
				} else if (chars[i] == '0') { // Boulder
                    bm.addBoulderAtIndex(new Boulder(x, y, BLOCK_SIZE, BLOCK_SIZE), i);
                } else if (chars[i] == 'P') { // Protagonist
					next.protagonist = new Protagonist(x, y, lc.PROTAGONIST_WIDTH, lc.PROTAGONIST_HEIGHT, lc.PROTAGONIST_SPEED_WALK, lc.PROTAGONIST_SPEED_JUMP, lc.PROTAGONIST_RANGE, BLOCK_SIZE);
				} else if (chars[i] == 'G') { // Goal
					next.goal = new Goal(x, y, lc.GOAL_WIDTH, lc.GOAL_HEIGHT);
				} else if (chars[i] == 'B') { // Ball
                    balls.add(new Ball(x, y, lc.BALL_WIDTH, lc.BALL_HEIGHT, new Vector2D(lc.BALL_SPEED_X, lc.BALL_SPEED_Y)));
                } else if (chars[i] == '^') { // Upwards pointing spikes
                    spikes.add(new Spikes(x, y+(BLOCK_SIZE-lc.SPIKE_HEIGHT)/2, lc.SPIKE_WIDTH, lc.SPIKE_HEIGHT, Direction.UP));
                } else if (chars[i] == 'V') { // Downwards pointing spikes
                    spikes.add(new Spikes(x, y-(BLOCK_SIZE-lc.SPIKE_HEIGHT)/2, lc.SPIKE_WIDTH, lc.SPIKE_HEIGHT, Direction.DOWN));
                } else if (chars[i] == '<') { // Leftwards pointing spikes
                    spikes.add(new Spikes(x+(BLOCK_SIZE-lc.SPIKE_WIDTH)/2, y, lc.SPIKE_WIDTH, lc.SPIKE_HEIGHT, Direction.LEFT));
                } else if (chars[i] == '>') { // Rightwards pointing spikes
                    spikes.add(new Spikes(x-(BLOCK_SIZE-lc.SPIKE_WIDTH)/2, y, lc.SPIKE_WIDTH, lc.SPIKE_HEIGHT, Direction.RIGHT));
                } else if (chars[i] == '-') { // Moving platform
                    platforms.add(new MovingPlatform(x, y, lc.MOVING_PLATFORM_WIDTH.get(indexPlatform), lc.MOVING_PLATFORM_HEIGHT.get(indexPlatform), patterns[indexPlatform]));
                    indexPlatform++;
                }
			}
			lineNum++;
		} while ((line = r.readLine()) != null);
		
		next.blocks = bm;
		next.balls = balls;
		next.spikes = spikes;
		next.movingPlatforms = platforms;
		nextLevel = new Level(next);
		
		levelIndex++;
	}
	
    /**
     * Returns true if there are more levels in the game
     * @return true if there are more levels in the game
     */
	public boolean hasMoreLevels() {
		return levelIndex < levels.length;
	}
	
	/**
     * Returns true if the next level has finished loading
     * @return true if the next level has finished loading
     */
	public boolean hasLoadedNext() {
		return nextLevel != null;
	}
	
    /**
     * Returns the next level
     * @return the next level
     */
	public Level getNextLevel() {
		Level next = nextLevel;
		nextLevel = null;
		return next;
	}
	
    /**
     * Returns the current level number
     * @return the current level number
     */
	public int levelNumber() {
		return levelIndex;
	}
	
    /**
     * Resets the current level.
     */
	public void resetLevel() {
		if (levelIndex != 0) {
			levelIndex--;
		}
	}
	
    /**
     * Resets the game, making all the levels being read again
     */
	public void resetGame() {
	    levelIndex = 0;
	    nextLevel = null;
    }
}
