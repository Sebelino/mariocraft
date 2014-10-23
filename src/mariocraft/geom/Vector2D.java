package mariocraft.geom;

import java.awt.geom.Point2D;

/**
 * Represents a immutable general vector in Euclidian 2D-space.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-05
 */
public class Vector2D {
    public final float x;
    public final float y;
    
    public static final Vector2D ZERO = new Vector2D(0, 0); //Zero vector
    
    /**
     * Creates a two-dimensional Euclidian vector.
     * 
     * @param x x-component
     * @param y y-component
     * @throws IllegalArgumentException iff one of the input values is not a number
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
        if(!isNumberVector()) {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * @return True iff the one of the vector components hold a real value
     */
    private boolean isNumberVector() {
        if(x == Float.NEGATIVE_INFINITY ||
           y == Float.NEGATIVE_INFINITY ||
           x == Float.POSITIVE_INFINITY ||
           y == Float.POSITIVE_INFINITY ||
           x == Float.NaN ||
           y == Float.NaN) {
            return false;
        }
        return true;
    }
    
    /**
     * @return Vector length
     */
    public float norm() {
        float norm = (float)Math.sqrt(x*x+y*y);
        return norm;
    }
    
    /**
     * @return This vector pointing in the opposite direction
     */
    public Vector2D reverse() {
        return new Vector2D(-x, -y);
    }
    
    /**
     * @return The dot product between two vectors.
     */
    public static float dotProduct(Vector2D v1, Vector2D v2) {
        return v1.x*v2.x+v1.y*v2.y;
    }
    
    /**
     * Returns the argument of the vector, i.e. the angle expressed from
     * -pi (exclusive) to pi (inclusive), where the angle is zero at the
     * positive x-axis and the argument increases in the anticlockwise
     * direction. An IllegalArgumentException is thrown should the
     * vector be the zero vector.
     * 
     * @return Argument
     */
    public float arg() {
        final float PI = (float)Math.PI;
        
        if (x == 0) {
        	 if(y < 0) {
                 return -PI/2;
             } else if (y > 0) {
                 return PI/2;
             }
        	 throw new IllegalArgumentException("The argument of the zero vector is undefined.");
        }
        
        float atan = (float)Math.atan(y/x);
        if(x > 0) {
            return atan;
        } else if(x < 0) {
        	if (y > 0) {
        		return atan + PI;
        	}
        	return atan - PI;
        }
        throw new IllegalArgumentException("SNAFU");
    }
    
    /**
     * Aims the vector in a direction specified by the input angle.
     * 
     * @param angle Argument in radians
     * @return A rotated vector
     */
    public Vector2D setArg(float angle) {
        float x = (float)(norm()*Math.cos(angle));
        float y = (float)(norm()*Math.sin(angle));
        return new Vector2D(x,y);
    }
    
    /**
     * Aims the vector in a direction specified by the current vector argument and the added angle.
     * 
     * @param angle Added angle radians
     * @return A rotated vector
     */
    public Vector2D addArg(float angle) {
        return setArg(arg()+angle);
    }
    
    /**
     * @param v The added vector
     * @return The sum of the two vectors
     */
    public Vector2D add(Vector2D v) {  
        return new Vector2D(x+v.x, y+v.y);
    }
    
    /**
     * @param v1 One of the two vectors to be added
     * @param v2 One of the two vectors to be added
     * @return The sum of the two given vectors
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {  
        return new Vector2D(v1.x+v2.x, v1.y+v2.y);
    }
    
    /**
     * Returns a vector extending from an initial point to a terminal point.
     * 
     * @param initial Initial point
     * @param terminal Terminal point
     * @return A vector extending from the initial point to the terminal point
     */
    public static Vector2D generate(Point2D.Float initial, Point2D.Float terminal) {
        return new Vector2D(terminal.x-initial.x, terminal.y-initial.y);
    }
    
    /**
     * Multiplies this vector by the input scalar.
     * 
     * @param scalar The multiplied scalar
     */
    public Vector2D scalarMultiply(float scalar) {
        return new Vector2D(x*scalar, y*scalar);
    }
    
    /**
     * Multiplies the x component by the x scalar and the y component with the y scalar.
     * 
     * @param scalarX The multiplied x scalar
     * @param scalarY The multiplied y scalar
     */
    public Vector2D scalarMultiply(float scalarX, float scalarY) {
        return new Vector2D(x*scalarX, y*scalarY);
    }
    
    /**
     * Normalizes the vector, i.e. keeps the direction but
     * sets the norm to 1.
     */
    public Vector2D normalize() {
    	if (equals(ZERO)) {
    		throw new UnsupportedOperationException("Can't normalize the zero vector");
    	}
        return scalarMultiply((float)1/norm());
    }
    
    /**
     * @param direction Specifies the direction of the returned vector
     * @return A vector with the same norm, but pointed in the given direction
     */
    public Vector2D aim(Vector2D direction) {
    	if (direction.equals(ZERO)) {
    		throw new IllegalArgumentException("The zero vector has no direction");
    	}
        float normedX = direction.x/direction.norm();
        float normedY = direction.y/direction.norm();
        Vector2D rotated = new Vector2D(norm()*normedX,norm()*normedY);
        return rotated;
    }
    
    /**
     * @param direction Direction of the projection
     * @return A projection of this vector on the given vector
     */
    public Vector2D project(Vector2D direction) {
    	if (direction.equals(ZERO)) {
    		throw new IllegalArgumentException("The aero vector have no direction");
    	}
        float norm = direction.norm();
        float scalar = dotProduct(this,direction)/norm/norm;
        return direction.scalarMultiply(scalar);
    }
    
    /**
     * Returns a vector with the same direction as this vector but
     * with the input vector length.
     * 
     * @param norm The new vector length.
     * @return A vector parallel to this vector
     */
    public Vector2D elongate(float norm) {
    	if (equals(ZERO)) {
    		throw new UnsupportedOperationException("The zero vector always has norm 0");
    	}
        return normalize().scalarMultiply(norm);
    }
    
    /**
     * Returns true if o is a Vector2D with the same coordinates as this vector.
     * 
     * @return true if o is a Vector2D with the same coordinates as this vector.
     */
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof Vector2D)) {
    		return false;
    	}
    	Vector2D v = (Vector2D) o;
    	return v.x == x && v.y == y;
    }
    
    /**
     * @return A string representation of the vector in the form "(x,y)".
     */
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
