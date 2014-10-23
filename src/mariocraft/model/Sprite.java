package mariocraft.model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

import mariocraft.geom.Vector2D;
import mariocraft.util.PhysicsUtil;

/**
 * A sprite is a moving object. It can move about a region and
 * has an inconstant velocity. 
 * 
 * @author Sebelino
 * @version 2011-04-20
 */
@SuppressWarnings("serial")
public abstract class Sprite extends Entity{
    protected Point2D.Float preLocation;
    protected Vector2D velocity;

    /**
     * Creates a general sprite.
     */
    public Sprite(Rectangle2D.Float rectangle, Vector2D velocity) {
        this(rectangle.x, rectangle.y, rectangle.width, rectangle.height, velocity);
    }
    
    /**
     * Creates a general sprite.
     */
    public Sprite(float x, float y, float width, float height, Vector2D velocity) {
        super(x, y, width, height);
        preLocation = new Point2D.Float(x, y);
        this.velocity = velocity;
    }

    /**
     * @return The previous x-coordinate of the sprite
     */
    public float getPreviousX() {
        return preLocation.x;
    }
    
    /**
     * @return The previous y-coordinate of the sprite
     */
    public float getPreviousY() {
        return preLocation.y;
    }
    
    /**
     * Sets the previous location.
     */
    private void updatePreLocation() {
        preLocation.x = getCenterX();
        preLocation.y = getCenterY();
    }
    
    /**
     * @return The velocity of the moving sprite
     */
    public Vector2D getVelocity() {
        return velocity;
    }
    
    /**
     * @return The speed, i.e. magnitude of the velocity
     */
    public float getSpeed() {
        return velocity.norm();
    }
    
    /**
     * Returns true if and only if the two entities intersect.
     *
     * @param entity The sprite which may be intersecting this object
     * @return True if and only if the two entities intersect
     */
    public boolean intersects(Entity entity) {
        return PhysicsUtil.intersects(this, entity);
    }
    
    /**
     * Sets the velocity.
     * 
     * @param velocity New velocity
     */
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
    
    /**
     * Moves the sprite in a direction and distance specified by the given vector.
     * 
     * @param Added position to the sprite
     */
    public void move(Vector2D addition) {
        updatePreLocation(); 
        addLocation(addition);
    }
    
    /**
     * Moves the object, i.e. adds the velocity to the current position.
     */
    public void move() {
        move(velocity);
    }
    
    /**
     * Moves the sprite to its previous location.
     */
    public final void retreat() {
        move(velocity.reverse());
//        setLocation(preLocation.x, preLocation.y);
    }
    
    /**
     * {@inheritDoc Entity}
     */
    public abstract void paint(Graphics g);
}
