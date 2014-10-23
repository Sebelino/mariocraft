package mariocraft.model;

import mariocraft.geom.Vector2D;

/**
 * A specific order of movements, defined by a ordered set of velocity vectors.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-22
 */
public class MovingPattern {
    private Vector2D[] velocities;
    private int[] periods; // Determines the amount of time for each velocity.
    private int current; // Index of current velocity. 
    private int counter; // Elapsed time of the current velocity.

    /**
     * Creates a moving pattern.
     * 
     * @param velocities The ordered velocities
     * @param periods The time period for each velocity
     */
    public MovingPattern(Vector2D[] velocities, int[] periods) {
        this.periods = periods;
        for(Integer period:periods) {
            if(period <= 0) {
                throw new IllegalArgumentException("TIME PERIOD WAS NON-POSITIVE.");
            }
        }
        this.velocities = velocities;
        current = 0;
        counter = 0;
    }
    
    /**
     * Updates the pattern, returns the current velocity.
     */
    public Vector2D update() {
        counter++;
        if(counter == periods[current]) {
            current++;
            counter = 0;
        }
        if(current == periods.length) {
            current = 0;
        }
        return velocities[current];
    }
}
