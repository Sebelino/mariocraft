package mariocraft.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import mariocraft.model.attributes.Corporal;
import mariocraft.util.PhysicsUtil;
import mariocraft.util.ImageUtil;

/**
 * Represents a static block with a fixed position and size.
 * Moving objects cannot move through these and get pushed back if they try.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-05
 */
@SuppressWarnings("serial")
public class Block extends Entity
                   implements Corporal {
    
    private static BufferedImage image = Images.BLOCK;
    private static boolean imageResized = false;
    
    private Point2D.Float relativeLocation;
    
    /**
     * Creates a static block.
     * 
     * @param rectangle Block rectangle
     */
    public Block(Rectangle2D.Float rectangle) {
        this(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
    /**
     * Creates a static block.
     * 
     * @param x x-coordinate for the middle of the block
     * @param y y-coordinate for the middle of the block
     * @param width Block width in pixels
     * @param height Block height in pixels
     */
    public Block(float x, float y, float width, float height) {
        super(x, y, width, height);
        if(!imageResized) {
            image = ImageUtil.resize(image, (int)width, (int)height);
            imageResized = true;
        }
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
     * {@inheritDoc Corporal}
     */
    @Override
    public boolean intersects(Entity entity) {
        return PhysicsUtil.intersects(this, entity);
    }
    
    /**
     * Puts the block image into an unresized state. 
     */
    public static void unresize() {
        imageResized = false;
    }
    
    /**
     * Draws the block.
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
