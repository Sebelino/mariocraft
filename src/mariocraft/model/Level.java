package mariocraft.model;

import java.awt.Graphics;
import java.awt.Color;
import java.util.HashSet;

import mariocraft.util.PhysicsUtil;
import mariocraft.geom.Vector2D;

/**
 * A game level.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-04
 */
public class Level {
	private Protagonist prot;
	private BlockManager blocks;
	private BackgroundManager bg;
	private HashSet<Ball> balls;
	private HashSet<Spikes> spikes;
	private HashSet<MovingPlatform> movingPlatforms;
	private Goal goal;
	private KeyState keys;
	private boolean finished;
	private boolean victorious;
	
	private float startPosX;
	private float startPosY;
	
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    
    private static final String BACKGROUND_IMAGE_NAME = "bg1.jpg";
	
	/**
	 * Creates a level out of a level designer.
	 * @param designer Level designer
	 */
	public Level(LevelDesigner designer) {
		bg = new BackgroundManager(BACKGROUND_IMAGE_NAME);
        prot = designer.protagonist;
        
        startPosX = prot.getCenterX() - prot.getWidth() / 2;
        startPosY = prot.getCenterY() - prot.getHeight() / 2;
        
        blocks = designer.blocks;
        balls = designer.balls;
        spikes = designer.spikes;
        movingPlatforms = designer.movingPlatforms;
        goal = designer.goal;
        updateRelativeLocations();
	}
	
    /**
     * Updates the entity locations relative to the protagonist.
     */
    private void updateRelativeLocations() {
        for(Ball ball:balls) {
            ball.setRelativeLocation(prot.getCenterpoint());
        }
        for(Spikes spike:spikes) {
            spike.setRelativeLocation(prot.getCenterpoint());
        }
        for(MovingPlatform platform:movingPlatforms) {
            platform.setRelativeLocation(prot.getCenterpoint());
        }
        goal.setRelativeLocation(prot.getCenterpoint());
    }
	
    /**
     * Updates gravity.
     */
    private void updateGravity() {
        prot.gravitate();
    }
	
    /**
     * Moves the entities.
     */
    private void updateMovement() {
        prot.move();
        for(Ball ball:balls) {
            ball.move();
        }
        for(MovingPlatform platform:movingPlatforms) {
            platform.move();
        }
    }
	
    /**
     * Collision detects all entities. Takes measures
     * if a collision happens to take place.
     */
    private void updateCollisions() {
        for(Block block:blocks.getBlocksAt(prot.getCenterX()-prot.getRectangleWidth()/2)){
            if(block.intersects(prot)) {
                PhysicsUtil.protagonistCollision(prot, block);
            }
        }
        for(Boulder boulder:blocks.getBouldersAt(prot.getCenterX()-prot.getRectangleWidth()/2)){
            if(boulder.intersects(prot)) {
                PhysicsUtil.protagonistCollision(prot, boulder);
            }
        }
        for(MovingPlatform platform:movingPlatforms){
            if(platform.intersects(prot)) {
                PhysicsUtil.protagonistCollision(prot, platform);
            }
        }
        for(Ball ball:balls) {
            for(Block block:blocks.getBlocksAt(ball.getCenterX()-ball.getRectangleWidth()/2)){
                if(block.intersects(ball)) {
                    PhysicsUtil.ballCollision(ball, block);
                }
            }
            for(Boulder boulder:blocks.getBouldersAt(ball.getCenterX()-ball.getRectangleWidth()/2)){
                if(boulder.intersects(ball)) {
                    PhysicsUtil.ballCollision(ball, boulder);
                }
            }
            for(MovingPlatform platform:movingPlatforms){
                if(platform.intersects(ball)) {
                    PhysicsUtil.ballCollision(ball, platform);
                }
            }
        }
    }
    
    /**
     * Updates the standing state of the protagonist.
     */
    public void updateGround() {
        if(!prot.isAirborne()) {
            if(!PhysicsUtil.touchesAbove(prot.getGround(), prot)) {
                prot.detach();
                prot.setAirborne(true);
                for(Block block:blocks.getBlocksAt(prot.getCenterX()-prot.getRectangleWidth()/2)) {
                    if(PhysicsUtil.touchesAbove(block, prot)) {
                        prot.setGround(block);
                        prot.setAirborne(false);
                        return;
                    }
                }
                for(Boulder boulder:blocks.getBouldersAt(prot.getCenterX()-prot.getRectangleWidth()/2)) {
                    if(PhysicsUtil.touchesAbove(boulder, prot)) {
                        prot.setGround(boulder);
                        prot.setAirborne(false);
                        return;
                    }
                }
                for(MovingPlatform platform:movingPlatforms) {
                    if(PhysicsUtil.touchesAbove(platform, prot)) {
                        prot.setGround(platform);
                        prot.setAirborne(false);
                        return;
                    }
                }
            }
        }
    }
	
	public void setKeyState(KeyState keys) {
	    this.keys = keys;
	}
	
    /**
     * Updates keyboard input. 
     */
    private void updateKeyState() {
        if(keys.cheat) {
            finished = true;
            victorious = true;
        }
        if(keys.left) {
            prot.moveLeft();
            prot.setWalking(true);
        }
        if(keys.right) {
            prot.moveRight();
            prot.setWalking(true);
        }
        if(!keys.left && !keys.right) {
            prot.halt();
            prot.setWalking(false);
        }
        prot.updateAim(keys);
        if(keys.jump) {
            if(!prot.isAirborne()) {
                prot.jump();
            }
            prot.setJumping(true);
            prot.setAirborne(true);
            prot.updateTimeAirborne();
        }
        if(prot.getTimeAirborne() < 5 && prot.getTimeAirborne() != 0) {
            prot.setCanLift(false);
        } else if(!keys.lift) {
            prot.setCanLift(true);
        }
        if(!prot.isCarrying() && keys.lift && prot.canLift()) {
            if(blocks.popBoulderAtPos(prot.getCenterX()+prot.getAimVector().x, prot.getCenterY()+prot.getAimVector().y) != null) {
                prot.toggleCarrying();
            }
            prot.setCanLift(false);
            prot.setAirborne(true);
        } else if(keys.lift && prot.canLift()) {
            dropBoulder();
        }
    }
    
    /**
     * Makes the protagonist drop the boulder it is carrying, if any.
     */
    private void dropBoulder() {
        if(prot.isCarrying()) {
            blocks.addBoulderAtPos((int)(prot.getCenterX()+prot.getAimVector().x), (int)(prot.getCenterY()+prot.getAimVector().y));
            prot.toggleCarrying();
            prot.setCanLift(false);
        }
    }
    
    /**
     * Updates the current state of the animations of the entities.
     */
    private void updateAnimations() {
        prot.updateAnimation();
        for(Ball ball:balls) {
            ball.updateAnimation();
        }
    }
	
    /**
     * Determines when the level ends.
     */
    private void updateTermination() {
        if(prot.intersects(goal)) {
            victorious = true;
        }
        if(isVictorious()) {
            finished = true;
        } else {
            for(Ball ball:balls) {
                if(prot.intersects(ball)) {
                    finished = true;
                    break;
                }
            }
            for(Spikes spike:spikes) {
                if(prot.intersects(spike)) {
                    finished = true;
                    break;
                }
            }
        }
    }
    
    /**
     * Updates the movement pattern of the moving platforms.
     */
    private void updateMovingPlatforms() {
        for(MovingPlatform platform:movingPlatforms) {
            platform.updateMovement();
            if(prot.getGround() == platform) {
                prot.move(platform.getVelocity());
            }
        }
    }
    
    /**
     * Updates various conditions.
     */
    private void updateConditions() {
        if(prot.getVelocity().y < 0) {
            prot.setJumping(true);
        } else {
            prot.setJumping(false);
        }
    }
    
    /**
     * Updates the amount of time in which the protagonist has been airborne.
     */
    public void updateTimeAirborne() {
        prot.updateTimeAirborne();
    }
    
    /**
     * Updates the screen.
     */
    public void update() {
        updateGravity();
        updateKeyState();
        updateConditions();
        updateTimeAirborne();
        updateMovingPlatforms();
        updateMovement();
        updateCollisions();
        updateGround();
        
        updateRelativeLocations();
        updateAnimations();
        updateTermination();
    }

    /**
     * Returns true if the level has ended.
     */
	public boolean isFinished() {
	    return finished;
	}
	
	/**
	 * Returns true when the level is beat.
	 */
	public boolean isVictorious() {
	    return victorious;
	}
	
	/**
	 * Resets the position of the protagonist.
	 */
	public void resetProtagonist() {
	    dropBoulder();
	    prot.setVelocity(Vector2D.ZERO);
		prot.setLocation(startPosX, startPosY);
		updateRelativeLocations();
		finished = false;
		victorious = false;
	}

	/**
	 * Changes the screen size.
	 */
   public void setScreenSize(int w, int h) {
       SCREEN_WIDTH = w;
	   SCREEN_HEIGHT = h;
    }
	
    /**
     * Paints the level.
     */
    public void paint(Graphics g) {
    	int xstart = (int) prot.getCenterX() - SCREEN_WIDTH/2;
    	int ystart = (int) prot.getCenterY() - SCREEN_HEIGHT/2;
    	
    	bg.paint(g, xstart, ystart);
        prot.paint(g);
        blocks.paint(g, xstart, prot.getCenterpoint());
        for(Ball ball:balls) {
            ball.paint(g);
        }
        for(Spikes spike:spikes) {
            spike.paint(g);
        }
        for(MovingPlatform platform:movingPlatforms) {
            platform.paint(g);
        }
        goal.paint(g);
        g.drawString(prot.canLift()+","+prot.getTimeAirborne(), 700, 20);
        g.drawString("Version 4.2", 300, 20);
        g.drawString("Walking:"+prot.isWalking(), 900, 70);
        g.drawString("Airborne:"+prot.isAirborne(), 900, 90);
        g.drawString("Jumping:"+prot.isJumping(), 900, 110);
        g.drawString("Direction:"+prot.getDirection(), 900, 130);
        g.drawString(""+prot.getVelocity(), 300, 50);
        g.drawString(""+prot.getCenterX()+","+prot.getCenterY(), 400, 90);
        if(isFinished() && !isVictorious()) {
            g.setColor(Color.RED);
            g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
        }
    }
}
