package mariocraft.model;

import java.awt.Color;
import java.awt.image.BufferedImage;

import mariocraft.util.AnimationUtil;
import mariocraft.util.ImageUtil;

/**
 * Contains all images.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-05
 */
public class Images {
    public static final Color TRANSPARENT = new Color(255,128,128);
    
    public static final BufferedImage BLOCK = ImageUtil.makeColorTransparent("brick1.png", TRANSPARENT);
    public static final BufferedImage SPIKES_UP = ImageUtil.makeColorTransparent("spikesUp.png", TRANSPARENT);
    public static final BufferedImage SPIKES_DOWN = ImageUtil.makeColorTransparent("spikesDown.png", TRANSPARENT);
    public static final BufferedImage SPIKES_LEFT = ImageUtil.makeColorTransparent("spikesLeft.png", TRANSPARENT);
    public static final BufferedImage SPIKES_RIGHT = ImageUtil.makeColorTransparent("spikesRight.png", TRANSPARENT);
    public static final BufferedImage BOULDER = ImageUtil.makeColorTransparent("boulder.png", TRANSPARENT);
    public static final BufferedImage MOVING_PLATFORM = ImageUtil.makeColorTransparent("movingPlatform.png", TRANSPARENT);
    public static final BufferedImage GOAL = ImageUtil.makeColorTransparent("goal.png", TRANSPARENT);
    public static final BufferedImage[][] BALL = AnimationUtil.createImages("ball.png", 4, 1);
    public static final BufferedImage[][] PROTAGONIST = AnimationUtil.createImages("protagonist.png", 8, 5);
}
