package mariocraft.util;

import java.awt.geom.Rectangle2D;

import mariocraft.geom.Vector2D;
import mariocraft.model.Entity;
import mariocraft.model.Protagonist;
import mariocraft.model.Ball;

/**
 * Utility class storing commonly used methods related to game physics.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-03
 */
public class PhysicsUtil {
    private static final Vector2D X_AXIS = new Vector2D(1,0);
    private static final Vector2D Y_AXIS = new Vector2D(0,1);
    
    /**
     * Returns true if and only if the two given entities intersect.
     * 
     * @param e1 Two-dimensional entity
     * @param e2 Two-dimensional entity
     * @return True if and only if e1 and e2 delimit a common area
     */
    public static boolean intersects(Entity e1, Entity e2) {
        Rectangle2D.Float r1 = e1.getRectangle();
        Rectangle2D.Float r2 = e2.getRectangle();
        return r1.intersects(r2);
    }
    
    /**
     * Returns true if and only if the two given entities touch each other.
     * 
     * @param e1 Two-dimensional entity
     * @param e2 Two-dimensional entity
     * @return True if and only if part of e1's border lies on part of that of e2
     */
    public static boolean touches(Entity e1, Entity e2) {
        Rectangle2D.Float r1 = e1.getRectangle();
        Rectangle2D.Float r2 = e2.getRectangle();
        final boolean RIGHT = r1.x+r1.width == r2.x && r1.y <= r2.y+r2.height && r1.y+r1.height >= r2.y;
        final boolean LEFT = r1.x == r2.x+r2.width && r1.y <= r2.y+r2.height && r1.y+r1.height >= r2.y;
        final boolean ABOVE = r1.y == r2.y+r2.height && r1.x <= r2.x+r2.width && r1.x+r1.width >= r2.x;
        final boolean BELOW = r1.y+r1.height == r2.y && r1.x <= r2.x+r2.width && r1.x+r1.width >= r2.x;
        return (LEFT || RIGHT || ABOVE || BELOW);
    }
    
    /**
     * Returns true if and only if entity e1 touches entity e2 from above.
     * 
     * @param e1 Two-dimensional entity
     * @param e2 Two-dimensional entity
     * @return True if and only if entity e1 touches entity e2 from above
     */
    public static boolean touchesAbove(Entity e1, Entity e2) {
        Rectangle2D.Float r1 = e1.getRectangle();
        Rectangle2D.Float r2 = e2.getRectangle();
        return r1.y == r2.y+r2.height && r1.x <= r2.x+r2.width && r1.x+r1.width >= r2.x;
    }
    
    /**
     * Returns a vector from the midpoint of one entity to the
     * midpoint of another entity.
     * 
     * @param e1 Initial point
     * @param e2 Terminal point
     * @return Distance vector from e1 to e2
     */
    public static Vector2D distance(Entity e1, Entity e2) {
        Vector2D d = new Vector2D(e2.getCenterX()-e1.getCenterX(),e2.getCenterY()-e1.getCenterY()); 
        return d;
    }

    /**
     * When the protagonist collides with a block, this method adjusts the position
     * and velocity of the sprite in a way so that they cease to intersect and
     * in a physically adequate manner.
     * 
     * @param sprite Colliding protagonist
     * @param block Intersecting block
     */
    public static void protagonistCollision(Protagonist sprite, Entity block) {
        sprite.retreat();
        Vector2D velocity = sprite.getVelocity();
        final boolean RIGHT = sprite.getCenterX()-sprite.getRectangleWidth()/2 >= block.getCenterX()+block.getRectangleWidth()/2;
        final boolean ABOVE = sprite.getCenterY()+sprite.getRectangleHeight()/2 <= block.getCenterY()-block.getRectangleHeight()/2;
        final boolean LEFT = sprite.getCenterX()+sprite.getRectangleWidth()/2 <= block.getCenterX()-block.getRectangleWidth()/2;
        final boolean BELOW = sprite.getCenterY()-sprite.getRectangleHeight()/2 >= block.getCenterY()+block.getRectangleHeight()/2;
        if(RIGHT) {
            sprite.setVelocity(velocity.project(Y_AXIS));
            sprite.setCenterX(block.getCenterX()+block.getRectangleWidth()/2+sprite.getRectangleWidth()/2);
        } else if(LEFT) {
            sprite.setVelocity(velocity.project(Y_AXIS));
            sprite.setCenterX(block.getCenterX()-block.getRectangleWidth()/2-sprite.getRectangleWidth()/2);
        } else if(ABOVE) {
            sprite.setVelocity(velocity.project(X_AXIS));
            sprite.setCenterY(block.getCenterY()-block.getRectangleHeight()/2-sprite.getRectangleHeight()/2);
            sprite.setGround(block);
            sprite.setAirborne(false);
            sprite.setJumping(false);
            sprite.updateTimeAirborne();
        } else if(BELOW) {
            sprite.setVelocity(velocity.project(X_AXIS));
            sprite.setCenterY(block.getCenterY()+block.getRectangleHeight()/2+sprite.getRectangleHeight()/2);
        } else {        // Recoil collision.
            Vector2D recoil = Vector2D.generate(block.getCenterpoint(), sprite.getCenterpoint()).normalize();
            sprite.setCenterX(sprite.getCenterX()+30*recoil.x);
            sprite.setCenterY(sprite.getCenterY()+30*recoil.y);
        }
        sprite.move();
    }
    
    /**
     * Makes a ball bounce off a block on contact.
     * 
     * @param sprite Colliding ball
     * @param block Intersecting block
     */
    public static void ballCollision(Ball sprite, Entity block) {
        sprite.retreat();
        final boolean RIGHT = sprite.getCenterX()-sprite.getRectangleWidth()/2 >= block.getCenterX()+block.getRectangleWidth()/2;
        final boolean ABOVE = sprite.getCenterY()+sprite.getRectangleHeight()/2 <= block.getCenterY()-block.getRectangleHeight()/2;
        final boolean LEFT = sprite.getCenterX()+sprite.getRectangleWidth()/2 <= block.getCenterX()-block.getRectangleWidth()/2;
        final boolean BELOW = sprite.getCenterY()-sprite.getRectangleHeight()/2 >= block.getCenterY()+block.getRectangleHeight()/2;
        if(RIGHT || LEFT) {
            sprite.bounceHorizontally();
        } else if(ABOVE || BELOW) {
            sprite.bounceVertically();
        } else {
            Vector2D recoil = Vector2D.generate(block.getCenterpoint(), sprite.getCenterpoint()).normalize();
            sprite.setCenterX(sprite.getCenterX()+15*recoil.x);
            sprite.setCenterY(sprite.getCenterY()+15*recoil.y);
            sprite.bounceHorizontally();
            sprite.bounceVertically();
        }
        sprite.move();
    }
}
