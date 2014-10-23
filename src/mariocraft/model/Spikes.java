package mariocraft.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import mariocraft.util.ImageUtil;

/**
 * Deadly on contact. Can point in either of the four directions.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-22
 */
@SuppressWarnings("serial")
public class Spikes extends Entity {
	
    private Point2D.Float relativeLocation;
    private BufferedImage image;
    
    /**
     * Creates a static set of spikes.
     * 
     * @param rectangle Spikes rectangle
     */
    public Spikes(Rectangle2D.Float rectangle, Direction direction) {
        this(rectangle.x, rectangle.y, rectangle.width, rectangle.height, direction);
    }
    
    /**
     * Sets the relative coordinates.
     * 
     * @param subjective The location of the protagonist
     */
    public void setRelativeLocation(Point2D.Float subjective) {                                 //TODO
        relativeLocation.setLocation(getCenterX()-subjective.x, getCenterY()-subjective.y);
    }
    
    /**
     * Creates a static set of spikes.
     * 
     * @param x x-coordinate for the middle of the spikes
     * @param y y-coordinate for the middle of the spikes
     * @param width Spike set width in pixels
     * @param height Spike set height in pixels
     */
    public Spikes(float x, float y, float width, float height, Direction direction) {
        super(x, y, width, height);
        relativeLocation = new Point2D.Float(0,0);
        if(direction == Direction.LEFT) {
            image = Images.SPIKES_LEFT;
        } else if(direction == Direction.RIGHT) {
        	image = Images.SPIKES_RIGHT;
        } else if(direction == Direction.UP) {
        	image = Images.SPIKES_UP;
        } else { // DOWN
        	image = Images.SPIKES_DOWN;
        }
        image = ImageUtil.resize(image, (int)getRectangleWidth(), (int)getRectangleHeight());
    }
    
    /**
     * Draws the spikes.
     */
    @Override
    public void paint(Graphics g) {
        int topLeftX = (int)(relativeLocation.x-getRectangleWidth()/2);
        int topLeftY = (int)(relativeLocation.y-getRectangleHeight()/2);
        ((Graphics2D)g).drawImage(image,
                                  null,
                                  600+topLeftX,
                                  400+topLeftY);
    }
}
