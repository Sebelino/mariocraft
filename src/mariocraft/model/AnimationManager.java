package mariocraft.model;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import mariocraft.util.AnimationUtil;

/**
 * Holds the animations of an entity and determines which animation to be displayed.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-05
 */
public class AnimationManager {
	
    private HashMap<String, Animation> animations;
    private String current;
    
    /**
     * Create an animation manager.
     */
    public AnimationManager(int width, int height) {
        current = "RightGrounded";
        BufferedImage[][] imgs = Images.PROTAGONIST;
        AnimationUtil.resizeImages(imgs, new Dimension(width, height));
        animations = new HashMap<String, Animation>();
        
        addAnimation("LeftGrounded",          false, 1, getImages(imgs[0][0]), getInts(0));
        addAnimation("LeftWalking",           true,  5, getImages(imgs[0][0], imgs[0][1]), getInts(1, 0));
        addAnimation("LeftAirborne",          false, 1, getImages(imgs[0][2]), getInts(0));
        addAnimation("LeftGroundedCarrying",  false, 1, getImages(imgs[0][3]), getInts(0));
        addAnimation("LeftWalkingCarrying",   true,  5, getImages(imgs[0][2], imgs[0][3]), getInts(1, 0));
        addAnimation("LeftAirborneCarrying",  false, 1, getImages(imgs[0][2]), getInts(0));
        
        addAnimation("LeftUpGrounded",          false, 1, getImages(imgs[1][0]), getInts(0));
        addAnimation("LeftUpAirborne",          false, 1, getImages(imgs[1][2]), getInts(0));
        addAnimation("LeftUpGroundedCarrying",  false, 1, getImages(imgs[1][3]), getInts(0));
        addAnimation("LeftUpAirborneCarrying",  false, 1, getImages(imgs[1][2]), getInts(0));
        
        addAnimation("LeftDownGrounded",          false, 1, getImages(imgs[2][0]), getInts(0));
        addAnimation("LeftDownAirborne",          false, 1, getImages(imgs[2][2]), getInts(0));
        addAnimation("LeftDownGroundedCarrying",  false, 1, getImages(imgs[2][3]), getInts(0));
        addAnimation("LeftDownAirborneCarrying",  false, 1, getImages(imgs[2][2]), getInts(0));
        
        addAnimation("LeftUpDiagonalGrounded",          false, 1, getImages(imgs[3][0]), getInts(0));
        addAnimation("LeftUpDiagonalWalking",           true,  5, getImages(imgs[3][0], imgs[3][1]), getInts(1, 0));
        addAnimation("LeftUpDiagonalAirborne",          false, 1, getImages(imgs[3][2]), getInts(0));
        addAnimation("LeftUpDiagonalGroundedCarrying",  false, 1, getImages(imgs[3][3]), getInts(0));
        addAnimation("LeftUpDiagonalWalkingCarrying",   true,  5, getImages(imgs[3][2], imgs[3][3]), getInts(1, 0));
        addAnimation("LeftUpDiagonalAirborneCarrying",  false, 1, getImages(imgs[3][2]), getInts(0));
        
        addAnimation("LeftDownDiagonalGrounded",          false, 1, getImages(imgs[4][0]), getInts(0));
        addAnimation("LeftDownDiagonalWalking",           true,  5, getImages(imgs[4][0], imgs[4][1]), getInts(1, 0));
        addAnimation("LeftDownDiagonalAirborne",          false, 1, getImages(imgs[4][2]), getInts(0));
        addAnimation("LeftDownDiagonalGroundedCarrying",  false, 1, getImages(imgs[4][3]), getInts(0));
        addAnimation("LeftDownDiagonalWalkingCarrying",   true,  5, getImages(imgs[4][2], imgs[4][3]), getInts(1, 0));
        addAnimation("LeftDownDiagonalAirborneCarrying",  false, 1, getImages(imgs[4][2]), getInts(0));
        
        addAnimation("RightGrounded",          false, 1, getImages(imgs[0][4]), getInts(0));
        addAnimation("RightWalking",           true,  5, getImages(imgs[0][4], imgs[0][5]), getInts(1, 0));
        addAnimation("RightAirborne",          false, 1, getImages(imgs[0][6]), getInts(0));
        addAnimation("RightGroundedCarrying",  false, 1, getImages(imgs[0][7]), getInts(0));
        addAnimation("RightWalkingCarrying",   true,  5, getImages(imgs[0][6], imgs[0][7]), getInts(1, 0));
        addAnimation("RightAirborneCarrying",  false, 1, getImages(imgs[0][6]), getInts(0));
        
        addAnimation("RightUpGrounded",          false, 1, getImages(imgs[1][4]), getInts(0));
        addAnimation("RightUpAirborne",          false, 1, getImages(imgs[1][6]), getInts(0));
        addAnimation("RightUpGroundedCarrying",  false, 1, getImages(imgs[1][7]), getInts(0));
        addAnimation("RightUpAirborneCarrying",  false, 1, getImages(imgs[1][6]), getInts(0));
        
        addAnimation("RightDownGrounded",          false, 1, getImages(imgs[2][4]), getInts(0));
        addAnimation("RightDownAirborne",          false, 1, getImages(imgs[2][6]), getInts(0));
        addAnimation("RightDownGroundedCarrying",  false, 1, getImages(imgs[2][7]), getInts(0));
        addAnimation("RightDownAirborneCarrying",  false, 1, getImages(imgs[2][6]), getInts(0));
        
        addAnimation("RightUpDiagonalGrounded",          false, 1, getImages(imgs[3][4]), getInts(0));
        addAnimation("RightUpDiagonalWalking",           true,  5, getImages(imgs[3][4], imgs[3][5]), getInts(1, 0));
        addAnimation("RightUpDiagonalAirborne",          false, 1, getImages(imgs[3][6]), getInts(0));
        addAnimation("RightUpDiagonalGroundedCarrying",  false, 1, getImages(imgs[3][7]), getInts(0));
        addAnimation("RightUpDiagonalWalkingCarrying",   true,  5, getImages(imgs[3][6], imgs[3][7]), getInts(1, 0));
        addAnimation("RightUpDiagonalAirborneCarrying",  false, 1, getImages(imgs[3][6]), getInts(0));
        
        addAnimation("RightDownDiagonalGrounded",          false, 1, getImages(imgs[4][4]), getInts(0));
        addAnimation("RightDownDiagonalWalking",           true,  5, getImages(imgs[4][4], imgs[4][5]), getInts(1, 0));
        addAnimation("RightDownDiagonalAirborne",          false, 1, getImages(imgs[4][6]), getInts(0));
        addAnimation("RightDownDiagonalGroundedCarrying",  false, 1, getImages(imgs[4][7]), getInts(0));
        addAnimation("RightDownDiagonalWalkingCarrying",   true,  5, getImages(imgs[4][6], imgs[4][7]), getInts(1, 0));
        addAnimation("RightDownDiagonalAirborneCarrying",  false, 1, getImages(imgs[4][6]), getInts(0));
    }
    
    /**
     * Adds an animation to the collection.
     */
    private void addAnimation(String key, boolean repeat, int wait, BufferedImage[] images, int[] order) {
        animations.put(key, new Animation(images, order, repeat, wait));
    }
    
    /**
     * Returns an array of images.
     */
    private BufferedImage[] getImages(BufferedImage...imgs) {
        return imgs;
    }
    
    /**
     * Returns an array of numbers.
     */
    private int[] getInts(int...numbers) {
        return numbers;
    }
    
    /**
     * Updates the animation manager.
     */
    public void update(Condition condition) {
        animations.get(current).update();
        current = "";
        if(condition.direction == Direction.LEFT) {
            current += "Left";
        } else {
            current += "Right";
        }
        if(condition.aim == Direction.UP ||
           condition.aim == Direction.UP_LEFT ||
           condition.aim == Direction.UP_RIGHT) {
            current += "Up";
        } else if(condition.aim == Direction.DOWN ||
                  condition.aim == Direction.DOWN_LEFT ||
                  condition.aim == Direction.DOWN_RIGHT) {
            current += "Down";
        } if(condition.aim == Direction.UP_LEFT ||
             condition.aim == Direction.UP_RIGHT ||
             condition.aim == Direction.DOWN_LEFT ||
             condition.aim == Direction.DOWN_RIGHT) {
            current += "Diagonal";
        }
        if(condition.airborne) {
            current += "Airborne";
        } else if(condition.walking) {
            current += "Walking";
        } else {
            current += "Grounded";
        }
        if(condition.carrying) {
            current += "Carrying";
        }
    }
    
    /**
     * @param The condition of the entity
     * @return The current image to be displayed determined by the condition of the entity
     */
    public BufferedImage getImage() {
        return animations.get(current).getImage();
    }
}
