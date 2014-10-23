package mariocraft.geom;

import java.awt.geom.Point2D;


/**
 * An immutable mathematical ray, i.e. a "line segment" that has a initial point and a direction in
 * which the ray extends to infinity.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-13
 */
public class Ray {
    public final Point2D.Float initial; 
    public final Vector2D direction;
    
    /**
     * Creates a ray.
     * 
     * @param initial Initial point
     * @param direction Direction of the ray
     */
    public Ray(Point2D.Float initial, Vector2D direction) {
        this.initial = new Point2D.Float(initial.x, initial.y);
        this.direction = new Vector2D(direction.x, direction.y);
    }
    
    /**
     * Returns true if and only if the input point lies on the ray, given
     * an error limit of the angular difference between the ray vector and
     * a vector with the same initial point but with the input point
     * as terminal point. Should the angular difference happen
     * to be smaller than the given error limit, the point is considered
     * to lie on the ray.
     * 
     * @param point The analyzed point
     * @param errorLimit The angular error limit
     * @return True if and only if the point lies on the plane
     */
    public boolean isMember(Point2D.Float point, float errorLimit) {
        Vector2D pointDirection = Vector2D.generate(initial, point);
        float error = Math.abs(direction.arg()-pointDirection.arg());
        return error < errorLimit;
    }
}
