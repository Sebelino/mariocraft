package mariocraft.model;

import mariocraft.geom.Vector2D;

/**
 * Stores all possible conditions of the protagonist.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-03
 */
public class Condition {
    public boolean walking;     // Left or right arrow key is pressed.
    public boolean airborne;    // Only the gravitational force has effect.
    public boolean jumping;     // Velocity is pointing upwards.
    public boolean carrying;    // Carries a boulder.
    public boolean canLift;     // Is in a state of being able to lift a boulder.
    public Direction direction; // The direction in which the protagonist is turned.
    public Direction aim;       // The direction in which the protagonist is to place boulders.
    private int timeAirborne;   // How long the protagonist has been airborne.
    
    /**
     * Creates a Condition.
     */
    public Condition() {
        walking = false;
        airborne = true;
        jumping = false;
        carrying = false;
        canLift = true;
        direction = Direction.RIGHT;
        aim = direction;
        timeAirborne = 0;
    }
    
    /**
     * Returns a vector describing the aim.
     */
    public Vector2D getAim() {
        switch(aim) {
            case UP: return new Vector2D(0,-1);
            case DOWN: return new Vector2D(0,1);
            case LEFT: return new Vector2D(-1,0);
            case RIGHT: return new Vector2D(1,0);
            case UP_LEFT: return new Vector2D(-1,-1);
            case UP_RIGHT: return new Vector2D(1,-1);
            case DOWN_LEFT: return new Vector2D(-1,1);
            case DOWN_RIGHT: return new Vector2D(1,1);
        }
        return null;
    }
    
    /**
     * Sets the aim according to the pressed keys.
     */
    public void setAim(KeyState keys) {
        if(keys.left) {
            if(keys.up) {
                aim = Direction.UP_LEFT;
            } else if(keys.down) {
                aim = Direction.DOWN_LEFT;
            } else {
                aim = Direction.LEFT;
            }
        } else if(keys.right) {
            if(keys.up) {
                aim = Direction.UP_RIGHT;
            } else if(keys.down) {
                aim = Direction.DOWN_RIGHT;
            } else {
                aim = Direction.RIGHT;
            }
        } else {
            if(keys.up) {
                aim = Direction.UP;
            } else if(keys.down) {
                aim = Direction.DOWN;
            } else {
                aim = direction;
            }
        }
    }
    
    /**
     * Increases the number of time instances passed since the protagonist left ground.
     */
    public void increaseTimeAirborne() {
        timeAirborne++;
    }
    
    /**
     * Sets the number of time instances passed since the protagonist left ground to zero.
     */
    public void resetTimeAirborne() {
        timeAirborne = 0;
    }
    
    /**
     * @return The number of time instances passed since the protagonist left ground
     */
    public int getTimeAirborne() {
        return timeAirborne;
    }
    
    /**
     * Turns the protagonist the other way.
     */
    public void turn() {
        direction = direction.back();
    }
}
