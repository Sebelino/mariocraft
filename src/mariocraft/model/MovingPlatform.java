package mariocraft.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import mariocraft.geom.Vector2D;
import mariocraft.model.attributes.Corporal;
import mariocraft.util.ImageUtil;
import mariocraft.util.PhysicsUtil;

/**
 * A platform that moves according to a pattern.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-03
 */
@SuppressWarnings("serial")
public class MovingPlatform extends Sprite
                            implements Corporal{
    private Point2D.Float relativeLocation;
    private MovingPattern movement;
    private BufferedImage image;
    
    /**
     * Creates a moving platform.
     */
    public MovingPlatform(Rectangle2D.Float rect, MovingPattern pattern) {
        this(rect.x, rect.y, rect.width, rect.height, pattern);
    }
    
    /**
     * Creates a moving platform.
     */
    public MovingPlatform(float x, float y, float width, float height, MovingPattern pattern) {
        super(x, y, width, height, new Vector2D(0, 0));
        relativeLocation = new Point2D.Float(0,0);
        image = ImageUtil.resize(Images.MOVING_PLATFORM, (int)getRectangleWidth(), (int)getRectangleHeight());
        this.movement = pattern;
    }
    
    /**
     * Updates the velocity.
     */
    public void updateMovement() {
        velocity = movement.update();
    }
    
    /**
     * @return The current velocity.
     */
    public Vector2D getVelocity() {
        return velocity;
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
     * Draws the ball.
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
