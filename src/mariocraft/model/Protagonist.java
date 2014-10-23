package mariocraft.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import mariocraft.geom.Vector2D;
import mariocraft.model.attributes.Corporal;
import mariocraft.model.attributes.Gravitated;
import mariocraft.util.ImageUtil;
import mariocraft.util.PhysicsUtil;

/**
 * The main character in the game. The player can move
 * the protagonist about the game field using the controls. 
 * 
 * @author Sebastian Olsson
 * @version 2011-05-03
 */
@SuppressWarnings("serial")
public class Protagonist extends Sprite
                         implements Gravitated,
                                    Corporal {	
    private float speedWalk;
    private float speedJump;
    private float range;
    private Condition condition;
    private AnimationManager animations;
    private BufferedImage boulder;
    private Entity ground;
    
    /**
     * Creates a protagonist.
     */
    public Protagonist(Rectangle2D.Float rect, float speedWalk, float speedJump, float range, float blockSize) {
        this(rect.x, rect.y, rect.width, rect.height, speedWalk, speedJump, range, blockSize);
    }
    
    /**
     * Creates a protagonist.
     */
    public Protagonist(float x, float y, float width, float height, float speedWalk, float speedJump, float range, float blockSize) {
        super(x, y, width, height, new Vector2D(0, 0));
        this.speedWalk = speedWalk;
        this.speedJump = speedJump;
        this.range = range;
        condition = new Condition();
        animations = new AnimationManager((int)width, (int)height);
        boulder = ImageUtil.resize(Images.BOULDER, (int)blockSize, (int)blockSize);
        detach();
        setCanLift(false);
    }
    
    /**
     * @return The pace at which the protagonist moves horisontally.
     */
    public float getSpeedWalk() {
        return speedWalk;
    }
    
    /**
     * Makes the protagonist start moving to the left.
     */
    public void moveLeft() {
        setVelocity(new Vector2D(-speedWalk, velocity.y));
        condition.direction = Direction.LEFT;
    }
    
    /**
     * Makes the protagonist start moving to the right.
     */
    public void moveRight() {
        setVelocity(new Vector2D(speedWalk, velocity.y));
        condition.direction = Direction.RIGHT;
    }
    
    /**
     * Halts the protagonist's velocity in the x-direction.
     */
    public void halt() {
        setVelocity(new Vector2D(0, velocity.y));
    }
    
    /**
     * The protagonist executes a jump.
     */
    public void jump() {
        setVelocity(new Vector2D(velocity.x, -speedJump));
        detach();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void gravitate() {
        if(isAirborne()) {
            velocity = velocity.add(GRAVITY);
        }
    }
    
    /**
     * {@inheritDoc Corporal}
     */
    @Override
    public boolean intersects(Entity entity) {
        return PhysicsUtil.intersects(this, entity);
    }
    
    /**
     * Sets the entity that the protagonist is standing on.
     */
    public void setGround(Entity entity) {
        ground = entity;
    }
    
    /**
     * Makes the protagonist stand on nothing.
     */
    public void detach() {
        setGround(null);
    }
    
    /**
     * @return The entity that the protagonist is currently standing on.
     */
    public Entity getGround() {
        return ground;
    }
    
    /**
     * @return True iff the protagonist is currently able to lift a boulder
     */
    public boolean canLift() {
        return condition.canLift;
    }
    
    /**
     * Sets the capability to lift a boulder.
     */
    public void setCanLift(boolean value) {
        condition.canLift = value;
    }

    /** 
     * @return True iff the protagonist is airborne
     */
    public boolean isAirborne() {
        return condition.airborne;
    }
    
    /** 
     * @return True iff the protagonist is in a jumping condition
     */
    public boolean isJumping() {
        return condition.jumping;
    }
    
    /** 
     * @return True iff the protagonist is walking
     */
    public boolean isWalking() {
        return condition.walking;
    }
    
    /** 
     * @return True iff the protagonist is carrying
     */
    public boolean isCarrying() {
        return condition.carrying;
    }
    
    /**
     * @return The direction that the protagonisten is turned to
     */
    public Direction getDirection() {
        return condition.direction;
    }
    
    /**
     * @param value Determines whether the protagonist will be airborne
     */
    public void setAirborne(boolean value) {
        condition.airborne = value;
    }
    
    /** 
     * @param value Determines whether the protagonist will be jumping
     */
    public void setJumping(boolean value) {
        condition.jumping = value;
    }
    
    /** 
     * @param value Determines whether the protagonist will be walking
     */
    public void setWalking(boolean value) {
        condition.walking = value;
    }
    
    /** 
     * Toggles the protagonist's carrying state between on/off
     */
    public void toggleCarrying() {
        condition.carrying = !condition.carrying;
    }
    
    /**
     * Updates the state of the animation.
     */
    public void updateAnimation() {
        animations.update(condition);
    }
    
    /**
     * Updates the direction in which to place boulders.
     */
    public void updateAim(KeyState keys) {
        condition.setAim(keys);
    }
    
    /**
     * @return A vector representing the direction and distance of boulder placement
     */
    public Vector2D getAimVector() {
        float dy = Math.abs(getRectangleHeight()-getRectangleWidth())/2;
        Vector2D aim = condition.getAim().scalarMultiply(range);
        if(aim.y < 0) {
            aim = aim.add(new Vector2D(0,-dy));
        } else if(aim.y > 0) {
            aim = aim.add(new Vector2D(0,dy));
        }
        return aim;
    }
    
    /**
     * @return The amount of time in which the protagonist has been airborne.
     */
    public int getTimeAirborne() {
        return condition.getTimeAirborne();
    }
    
    /**
     * Updates the amount of time in which the protagonist has been airborne.
     */
    public void updateTimeAirborne() {
        if(condition.airborne) {
            condition.increaseTimeAirborne();
        } else {
            condition.resetTimeAirborne();
        }
    }
    
    /**
     * Draws the protagonist.
     */
    @Override
    public void paint(Graphics g) {
        ((Graphics2D)g).drawImage(animations.getImage(),
                                  null,
                                  600-(int)getRectangleWidth()/2,
                                  400-(int)getRectangleHeight()/2);
        if(condition.carrying) {
        ((Graphics2D)g).drawImage(boulder,
                                  null,
                                  600-(int)(boulder.getWidth()/2),
                                  400-(int)(getRectangleHeight()/2+0.8*boulder.getHeight()));
        }
        drawAim(g);
    }
    
    /**
     * Draws a tiny point which shows the location of the supposedly placed boulder.
     */
    private void drawAim(Graphics g) {
        g.drawLine(600+(int)getAimVector().x,
                   400+(int)getAimVector().y,
                   600+(int)getAimVector().x,
                   400+(int)getAimVector().y);
    }
}
