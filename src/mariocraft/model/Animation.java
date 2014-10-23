package mariocraft.model;

import java.awt.image.BufferedImage;

/**
 * Contains a sequence of images that are displayed in a specific order.
 * 
 * @author Sebastian Olsson
 * @version 2011-14-25
 */
public class Animation {
    private BufferedImage[] images;
    private int counter;
    private int[] order;
    private boolean repeat; // If false, the animation will stop at the last image.
    private int timeScale;  // Determines how long the current image will be displayed.
    
    /**
     * Creates an animation.
     */
    public Animation(BufferedImage[] imgs, int[] order, boolean repeat, int timeScale) {
        images = imgs;
        this.order = order;
        this.repeat = repeat;
        if(timeScale <= 0) {
            throw new IllegalArgumentException("Time scale for animations should be > 0.");
        }
        this.timeScale = timeScale;
    }
    
    /**
     * Determines the image to be currently displayed.
     */
    public void update() {
        counter++;
        if(counter == order.length*timeScale) {
            counter--;
            if(repeat) {
                counter = 0;
            }
        }
    }
    
    /**
     * Sets the image counter back to zero.
     */
    public void reset() {
        counter = 0;
    }
    
    /**
     * @return The current image to be displayed
     */
    public BufferedImage getImage() {
        return images[order[counter/timeScale]];
    }
}