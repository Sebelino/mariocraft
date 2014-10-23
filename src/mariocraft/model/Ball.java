package mariocraft.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

import mariocraft.geom.Vector2D;
import mariocraft.util.PhysicsUtil;
import mariocraft.util.AnimationUtil;
import mariocraft.model.attributes.Corporal;
import mariocraft.model.Images;

/**
 * Bounces against the blocks in the level at a constant pace. Deadly on contact.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-05
 */
@SuppressWarnings("serial")
public class Ball extends Sprite
                  implements Corporal {
    private Point2D.Float relativeLocation;
    private Animation animation;
    
    /**
     * Creates a ball.
     */
    public Ball(Rectangle2D.Float rect, Vector2D velocity) {
        this(rect.x, rect.y, rect.width, rect.height, velocity);
    }
    
    /**
     * Creates a ball.
     */
    public Ball(float x, float y, float width, float height, Vector2D velocity) {
        super(x, y, width, height, velocity);
        relativeLocation = new Point2D.Float(0,0);
        BufferedImage[][] imgs = Images.BALL;
        AnimationUtil.resizeImages(imgs, new Dimension((int)width, (int)height));
        int[] order = new int[]{0,1,2,3};
        animation = new Animation(imgs[0], order, true,1);
    }
    
    /**
     * Sets the relative coordinates.
     * 
     * @param subjective The location of the protagonist
     */
    public void setRelativeLocation(Point2D.Float subjective) {
        relativeLocation.setLocation(getCenterX()-subjective.x, getCenterY()-subjective.y);
    }
    
    /**
     * Updates the state of the animation.
     */
    public void updateAnimation() {
        animation.update();
    }
    
    /**
     * {@inheritDoc Corporal}
     */
    @Override
    public boolean intersects(Entity entity) {
        return PhysicsUtil.intersects(this, entity);
    }
    
    /**
     * Makes the ball bounce horizontally.
     */
    public void bounceHorizontally() {
        velocity = new Vector2D(-velocity.x, velocity.y);
        tweakDirection();
    }
    
    /**
     * Makes the ball bounce vertically.
     */
    public void bounceVertically() {
        velocity = new Vector2D(velocity.x, -velocity.y);
        tweakDirection();
    }
    
    /**
     * Rotates the velocity slightly and randomly.
     */
    private void tweakDirection() {
        velocity = velocity.addArg((float)(Math.PI/30*(Math.random()-0.5)));
    }
    
    /**
     * Draws the ball.
     */
    @Override
    public void paint(Graphics g) {
        int topLeftX = (int)(relativeLocation.x-getRectangleWidth()/2);
        int topLeftY = (int)(relativeLocation.y-getRectangleHeight()/2);
        ((Graphics2D)g).drawImage(animation.getImage(),
                                  null,
                                  600+topLeftX,
                                  400+topLeftY);
    }
}
