package mariocraft.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import mariocraft.util.ImageUtil;

/**
 * The actual level is beat when the protagonist reaches the goal. 
 * 
 * @author Sebastian Olsson
 * @version 2011-04-15
 */
@SuppressWarnings("serial")
public class Goal extends Entity{
	
    private Point2D.Float relativeLocation;
    private final BufferedImage image;
    
    /**
     * Creates a goal.
     * 
     * @param rectangle Goal rectangle
     */
    public Goal(Rectangle2D.Float rect) {
        this(rect.x, rect.y, rect.width, rect.height);
    }
    
    /**
     * Creates a goal.
     * 
     * @param x x-coordinate for the middle of the entity
     * @param y y-coordinate for the middle of the entity
     * @param width Entity width in pixels
     * @param height Entity height in pixels
     */
    public Goal(float x, float y, float width, float height) {
        super(x, y, width, height);
        image = ImageUtil.resize(Images.GOAL, (int)getRectangleWidth(), (int)getRectangleHeight());
        relativeLocation = new Point2D.Float(0,0);
    }
    
    /**
     * Sets the relative coordinates.
     * 
     * @param subjective The location of the protagonist
     */
    public void setRelativeLocation(Point2D.Float subjective) {
        relativeLocation.setLocation(getCenterX()-subjective.x, getCenterY()-subjective.y);
    }
    
    /**
     * Draws the goal.
     */
    public void paint(Graphics g) {
        int topLeftX = (int)(relativeLocation.x-getRectangleWidth()/2);
        int topLeftY = (int)(relativeLocation.y-getRectangleHeight()/2);
        ((Graphics2D)g).drawImage(image,
                null,
                600+topLeftX,
                400+topLeftY);
    }
}
