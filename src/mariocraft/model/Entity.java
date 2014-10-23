package mariocraft.model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

import javax.swing.JComponent;

import mariocraft.geom.Vector2D;

/**
 * Represents a graphical object in the game.
 * The object has a rectangular shape.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-10
 */
@SuppressWarnings("serial")
public abstract class Entity extends JComponent {
    protected Rectangle2D.Float rectangle;
    
    /**
     * Initializes the entity.
     * 
     * @param x x-coordinate for the middle of the entity
     * @param y y-coordinate for the middle of the entity
     * @param width Entity width in pixels
     * @param height Entity height in pixels
     */
    public Entity(Rectangle2D.Float rectangle) {
        this(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
    /**
     * Initializes the entity.
     * 
     * @param x x-coordinate for the middle of the entity
     * @param y y-coordinate for the middle of the entity
     * @param width Entity width in pixels
     * @param height Entity height in pixels
     */
    public Entity(float midX, float midY, float width, float height) {
        rectangle = new Rectangle2D.Float(midX-width/2, midY-height/2, width, height);
        setSize((int)width,(int)height);
    }
    
    /**
     * @return Entity rectangle
     */
    public Rectangle2D.Float getRectangle() {
        return rectangle;
    }
    
    /**
     * @return The midpoint of the rectangle
     */
    public Point2D.Float getCenterpoint() {
        return new Point2D.Float(getCenterX(), getCenterY());
    }
    
    /**
     * @return x-coordinate in the middle of the rectangle
     */
    public float getCenterX(){
        return (float)rectangle.getCenterX();
    }
    
    /**
     * @return y-coordinate in the middle of the rectangle
     */
    public float getCenterY(){
        return (float)rectangle.getCenterY();
    }
    
    /**
     * @return Rectangle width
     */
    public float getRectangleWidth() {
        return rectangle.width;
    }
    
    /**
     * @return Rectangle height
     */
    public float getRectangleHeight() {
        return rectangle.height;
    }
    
    /**
     * Sets the x-coordinate of the middle of the entity rectangle
     * 
     * @param value The new x-coordinate
     */
    public void setCenterX(float value) {
        rectangle.x = value-rectangle.width/2;
    }
    
    /**
     * Sets the y-coordinate of the middle of the entity rectangle
     * 
     * @param value The new y-component
     */
    public void setCenterY(float value) {
        rectangle.y = value-rectangle.height/2;
    }
    
    /**
     * Sets the width of the component.
     * 
     * @param value The new width
     */
    public void setRectangleWidth(float value) {
        rectangle.width = value;
        setSize((int)rectangle.width,(int)rectangle.height);
    }
    
    /**
     * Sets the height of the component.
     * 
     * @param value The new width
     */
    public void setRectangleHeight(float value) {
        rectangle.height = value;
        setSize((int)rectangle.width,(int)rectangle.height);
    }
    
    /**
     * Sets the location.
     */
    public void setLocation(float a, float b) {
        rectangle.x = a;
        rectangle.y = b;
    }
    
    /**
     * Adds the components of the vector to the coordinates.
     */
    public void addLocation(Vector2D v) {
        setLocation(rectangle.x+v.x,rectangle.y+v.y);
    }
    
    /**
     * Draws the entity.
     */
    public abstract void paint(Graphics g);
}