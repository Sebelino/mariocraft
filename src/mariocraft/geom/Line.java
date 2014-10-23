package mariocraft.geom;

import java.awt.geom.Point2D;



/**
 * A line in Euclidean 2D space. Represented by a point on the line and a direction.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-14
 */
public class Line {
    public final Point2D.Float point; 
    public final Vector2D direction;
    
    /**
     * Creates a ray.
     * 
     * @param initial Initial point
     * @param direction Direction of the ray
     */
    public Line(Point2D.Float point, Vector2D direction) {
        this.point = new Point2D.Float(point.x, point.y);
        if(!(-Math.PI/2 < direction.arg() && direction.arg() <= Math.PI/2)) {
            this.direction = direction.reverse();
        } else {
            this.direction = direction;
        }
    }
    
    /**
     * @param x The given x-coordinate
     * @return The y-coordinate associated with the given x-coordinate
     * @throws IllegalArgumentException If the x-coordinate is not associated with exactly one y-coordinate
     */
    public float y(float x) {
        if(direction.x == 0) {
            throw new IllegalArgumentException("y is not a function of x.");
        }
        return point.x+(x-point.x)/direction.x*direction.y;
    }
    
    /**
     * @param y The given y-coordinate
     * @return The x-coordinate associated with the given y-coordinate
     * @throws IllegalArgumentException If the y-coordinate is not associated with exactly one x-coordinate
     */
    public float x(float y) {
        if(direction.y == 0) {
            throw new IllegalArgumentException("x is not a function of y.");
        }
        return point.y+(y-point.y)/direction.y*direction.x;
    }
    
    /**
     * Returns true if and only if the input point lies on the line, given
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
        Vector2D pointDirection = Vector2D.generate(this.point, point);
        float error = Math.abs(direction.arg()-pointDirection.arg());
        return (error < errorLimit) || (error-Math.PI < errorLimit);
    }
    
    /**
     * Returns the argument of the line, i.e. the angle expressed from
     * -pi/2 (inclusive) to pi/2 (exclusive), where the angle is zero at the
     * positive x-axis and the argument increases in the anticlockwise
     * direction.
     *
     * @return Argument
     */
    public float arg() {
        return direction.arg();
    }
    
    /**
     * Returns true iff the point is located above the line.
     */
    public boolean isBelow(Point2D.Float point) {
        return point.y > y(point.x);
    }
    
    /**
     * @return A string representation of the line in the form "(a,b)+t(c,d)"
     */
    @Override
    public String toString() {
        return "("+point.x+","+point.y+")+("+direction.x+","+direction.y+")";
    }
}
