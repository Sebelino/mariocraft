package mariocraft.geom;

import java.awt.geom.Point2D;


/**
 * An immutable mathematical line segment. It has a initial and terminal point.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-13
 */
public class LineSegment {
    public final Point2D.Float initial; 
    public final Point2D.Float terminal;
    
    /**
     * Creates a line segment.
     * 
     * @param initial Initial point
     * @param terminal Terminal point
     * @throws IllegalArgumentException If the initial and terminal points are the same
     */
    public LineSegment(Point2D.Float initial, Point2D.Float terminal) {
        if(initial.equals(terminal)) {
            throw new IllegalArgumentException("The initial and terminal points of a line segment cannot have the same coordinates.");
        }
        this.initial = new Point2D.Float(initial.x, initial.y);
        this.terminal = new Point2D.Float(terminal.x, terminal.y);
    }
    
    /**
     * Returns true if and only if the input point lies on the line segment, given
     * an error limit of the angular difference between a vector from the initial
     * point to the terminal point and a vector with the same initial point but
     * with the input point as terminal point. Should the angular difference happen
     * to be smaller than the given error limit, the point is considered
     * to lie on the line segment. 
     * 
     * @param point The analyzed point
     * @param errorLimit The angular error limit
     * @return True if and only if the point lies on the line segment
     */
    public boolean isMember(Point2D.Float point, float errorLimit) {
        Vector2D lineDirection = Vector2D.generate(initial, terminal);
        Vector2D pointDirection = Vector2D.generate(initial, point);
        float error = Math.abs(lineDirection.arg()-pointDirection.arg());
        return error < errorLimit;
    }
}
