package mariocraft.model;

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import mariocraft.MarioCraft;

/**
 * A class used to keep track of all the constants associated with a level
 * 
 * @author Erik Odenman
 * @version 2011-05-05
 */
public class LevelConstants {

	private static final String DEFAULT_VALUES = "resources/levels/mechanics_default.txt";
	
	public int BLOCK_SIZE;
	
	public float PROTAGONIST_WIDTH;
	public float PROTAGONIST_HEIGHT;
	public float PROTAGONIST_SPEED_WALK;
    public float PROTAGONIST_SPEED_JUMP;
    public float PROTAGONIST_RANGE;
    public Direction PROTAGONIST_START_DIRECTION;
    
    public float BALL_WIDTH;
    public float BALL_HEIGHT;
    public float BALL_SPEED_X;
    public float BALL_SPEED_Y;
    
    public ArrayList<Float> MOVING_PLATFORM_WIDTH;
    public ArrayList<Float> MOVING_PLATFORM_HEIGHT;
    public ArrayList<Float[]> MOVING_PLATFORM_PATTERN_X;
    public ArrayList<Float[]> MOVING_PLATFORM_PATTERN_Y;
    public ArrayList<Integer[]> MOVING_PLATFORM_PERIODS;
    
    public float GOAL_WIDTH;
    public float GOAL_HEIGHT;
    
    public float SPIKE_WIDTH;
    public float SPIKE_HEIGHT;
    
    public float GRAVITATIONAL_ACCELERATION;
    
    /**
     * Creates a new LevelConstants instance reading from the specified
     * input stream. If the stream isn't valid only default values
     * will be used.
     */
    public LevelConstants(InputStream f) throws IOException {
    	readConstants(MarioCraft.class.getResourceAsStream(DEFAULT_VALUES));
    	try {
    		readConstants(f);
    	} catch (Exception e) {}
    }
    
    /**
     * Reads all the constants from the specified input stream
     */
    private void readConstants(InputStream f) throws IOException {
    	BufferedReader in = new BufferedReader(new InputStreamReader(f));
    	String line;
    	int platformCount = 0;
    	while ((line = in.readLine()) != null) {
    		if (!line.startsWith("//")) {
    			StringTokenizer st = new StringTokenizer(line);
    			if (st.hasMoreTokens()) {
	    			String constant = st.nextToken();
	    			if (constant.equals("BLOCK_SIZE")) {
	    				BLOCK_SIZE = Integer.parseInt(st.nextToken());
	    			} else if (constant.equals("PROTAGONIST_WIDTH")) {
	    				PROTAGONIST_WIDTH = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("PROTAGONIST_HEIGHT")) {
	    				PROTAGONIST_HEIGHT = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("PROTAGONIST_SPEED_WALK")) {
	    				PROTAGONIST_SPEED_WALK = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("PROTAGONIST_SPEED_JUMP")) {
	    				PROTAGONIST_SPEED_JUMP = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("PROTAGONIST_RANGE")) {
	    				PROTAGONIST_RANGE = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("PROTAGONIST_START_DIRECTION")) {
	    				String direction = st.nextToken();
	    				if (direction.equals("RIGHT")) {
	    					PROTAGONIST_START_DIRECTION = Direction.RIGHT;
	    				} else if (direction.equals("LEFT")) {
	    					PROTAGONIST_START_DIRECTION = Direction.LEFT;
	    				}
	    			} else if (constant.equals("BALL_WIDTH")) {
	    				BALL_WIDTH = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("BALL_HEIGHT")) {
	    				BALL_HEIGHT = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("BALL_SPEED_X")) {
	    				BALL_SPEED_X = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("BALL_SPEED_Y")) {
	    				BALL_SPEED_Y = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("MOVING_PLATFORM_WIDTH")) {
                        if(platformCount == 0) {
                            MOVING_PLATFORM_WIDTH = new ArrayList<Float>();
                            MOVING_PLATFORM_HEIGHT = new ArrayList<Float>();
                            MOVING_PLATFORM_PATTERN_X = new ArrayList<Float[]>();
                            MOVING_PLATFORM_PATTERN_Y = new ArrayList<Float[]>();
                            MOVING_PLATFORM_PERIODS = new ArrayList<Integer[]>();
                        }
	    				MOVING_PLATFORM_WIDTH.add(Float.parseFloat(st.nextToken()));
                        platformCount++;
	    			} else if (constant.equals("MOVING_PLATFORM_HEIGHT")) {
	    				MOVING_PLATFORM_HEIGHT.add(Float.parseFloat(st.nextToken()));
	    			} else if (constant.equals("MOVING_PLATFORM_PATTERN_X")) {
	    			    Float[] nums = new Float[st.countTokens()];
	    				int i = 0;
	    				while (st.hasMoreTokens()) { // comments in input file will f*ck this up
	    					nums[i] = Float.parseFloat(st.nextToken());
	    					i++;
	    				}
	                    MOVING_PLATFORM_PATTERN_X.add(nums);
	    			} else if (constant.equals("MOVING_PLATFORM_PATTERN_Y")) {
	    			    Float[] nums = new Float[st.countTokens()];
                        int i = 0;
                        while (st.hasMoreTokens()) { // comments in input file will f*ck this up
                            nums[i] = Float.parseFloat(st.nextToken());
                            i++;
                        }
                        MOVING_PLATFORM_PATTERN_Y.add(nums);
	    			} else if (constant.equals("MOVING_PLATFORM_PERIODS")) {
	    			    Integer[] nums = new Integer[st.countTokens()];
                        int i = 0;
                        while (st.hasMoreTokens()) { // comments in input file will f*ck this up
                            nums[i] = Integer.parseInt(st.nextToken());
                            i++;
                        }
                        MOVING_PLATFORM_PERIODS.add(nums);
	    			} else if (constant.equals("GOAL_WIDTH")) {
	    				GOAL_WIDTH = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("GOAL_HEIGHT")) {
	    				GOAL_HEIGHT = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("SPIKE_WIDTH")) {
	    				SPIKE_WIDTH = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("SPIKE_HEIGHT")) {
	    				SPIKE_HEIGHT = Float.parseFloat(st.nextToken());
	    			} else if (constant.equals("GRAVITATIONAL_ACCELERATION")) {
	    				GRAVITATIONAL_ACCELERATION = Float.parseFloat(st.nextToken());
	    			} else {
	    				throw new IllegalArgumentException("Can't find constant "+constant);
	    			}
    			}
    		}
    	}
    }
}
