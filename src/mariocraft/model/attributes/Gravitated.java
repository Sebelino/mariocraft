package mariocraft.model.attributes;

import mariocraft.geom.Vector2D;

/**
 * Gravitated entities obey the law of gravity, i.e. they are
 * pulled downwards with constant acceleration.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-03
 */
public interface Gravitated {
    final float ACCELERATION = 0.5f;    // Gravitational acceleration
    final Vector2D GRAVITY = new Vector2D(0,ACCELERATION); // Acceleration vector
    /**
     * Increases the velocity according to the gravitational constant.
     */
    void gravitate();
}
