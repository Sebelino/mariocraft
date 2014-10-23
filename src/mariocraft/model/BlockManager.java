package mariocraft.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.awt.*;
import java.awt.geom.Point2D;

import mariocraft.ui.ScreenController;

/**
 * A class for controlling the blocks and boulders in the game
 * (boulders are liftable blocks).
 * @author Erik
 *
 */
public class BlockManager {
	
	private LinkedList<Block>[] blocks;
	private LinkedList<Boulder>[] boulders;
	private float blockSize;
	
	/**
     * Create a new BlockManager with the specified level width.
     * @param length
     */
	@SuppressWarnings("unchecked")
	public BlockManager(int length, float blockSize) {
		blocks = new LinkedList[length];
		boulders = new LinkedList[length];
		for (int i = 0; i < length; i++) {
			blocks[i] = new LinkedList<Block>();
			boulders[i] = new LinkedList<Boulder>();
		}
		this.blockSize = blockSize;
	}
	
	/**
     * Adds a block at the specified index in the map. The absolute
     * position on the map is determined by the index and the block size.
     * @param b The block to add
     * @param index The index
     */
	public void addBlockAtIndex(Block b, int index) {
	    if (indexInRange(index)) {
	        blocks[index].add(b);
        }
	}
	
    /**
     * Adds a boulder at the specified index in the map. The absolute
     * position on the map is determined by the index and the block size.
     * @param b The boulder to add
     * @param index The index
     */
	public void addBoulderAtIndex(Boulder b, int index) {
	    if (indexInRange(index)) {
	        boulders[index].add(b);
	    }
	}
	
    /**
     * Add a boulder at the specified position.
     * @param xpos
     * @param ypos
     */
    public void addBoulderAtPos(int xpos, int ypos) {
        int index = (int)(xpos/blockSize);
        addBoulderAtIndex(new Boulder(xpos, ypos, blockSize, blockSize), index);
    }
	
    /**
     * Return the block at the specified position
     * @param xpos
     * @param ypos
     * @return The block at the position
     */
	public Block getBlockAtPos(float xpos, float ypos) {
		for (Block b : getBlocksAt(xpos)) {
			if (b.getRectangle().contains(xpos, ypos)) {
				return b;
			}
		}
		return null;
	}
	
    /**
     * Return the boulder at the specified position
     * @param xpos
     * @param ypos
     * @return The boulder at the position
     */
	public Boulder getBoulderAtPos(float xpos, float ypos) {
	    for (Boulder b : getBouldersAt(xpos)) {
	        if (b.getRectangle().contains(xpos, ypos)) {
	            return b;
	        }
	    }
	    return null;
	}
	
	/**
     * Remove and return the boulder at the specified position
     * @param xpos
     * @param ypos
     * @return The boulder at the position
     */
	public Boulder popBoulderAtPos(float xpos, float ypos) {
	    Boulder sought = null;
	    int index = (int)(xpos/blockSize);
	    for(int i = index-1;i <= index+1;i++) {
	        if (indexInRange(i)) {
    	        LinkedList<Boulder> boulderList = boulders[i];
    	        Iterator<Boulder> it = boulderList.iterator();
    	        while(it.hasNext()) {
    	            Boulder boulder = it.next();
    	            if(boulder.getRectangle().contains(xpos, ypos)) {
    	                sought = boulder;
    	                it.remove();
    	                i = index+1;
    	                break;
    	            }
    	        }
	        }
	    }
	    return sought;
    }
	
    /**
     * Returns all the blocks at the specified x position
     * @param xpos
     * @return All the blocks at xpos
     */
	public LinkedList<Block> getBlocksAt(float xpos) {
		int index = (int) (xpos/blockSize);
		LinkedList<Block> total = new LinkedList<Block>();
		for (int i = -2; i < 3; i++) { // How many blocks ahead are we looking at? Depends on the protagonist's size relative the block size
			if (indexInRange(index + i)) {
				total.addAll(blocks[index + i]);
			}
		}
		return total;
	}
	
    /**
     * Returns all the boulders at the specified x position
     * @param xpos
     * @return All the boulders at xpos
     */
	public LinkedList<Boulder> getBouldersAt(float xpos) {
        int index = (int) (xpos/blockSize);
        LinkedList<Boulder> total = new LinkedList<Boulder>();
        for (int i = -2; i < 3; i++) { // How many boulders ahead are we looking at? Depends on the protagonist's size relative the boulder size
            if (indexInRange(index + i)) {
                total.addAll(boulders[index + i]);
            }
        }
        return total;
    }
	
    /**
     * Paints all the block from the specified start position and
     * one screen length forward.
     * @param g The draw graphics
     * @param xstart The start x position
     * @param protPos The position of the protagonist
     */
	public void paint(Graphics g, int xstart, Point2D.Float protPos) {
		int xstop = xstart + ScreenController.SCREEN_WIDTH;
		
		int start = (int)(xstart/blockSize);
		int stop = (int)(xstop/blockSize) + 2;
		if (start < 0) {
			start = 0;
		}
		if (stop >= blocks.length) {
			stop = blocks.length - 1;
		}
		for (int i = start; i <= stop; i++) {
			for (Block b : blocks[i]) {
				b.setRelativeLocation(protPos);
				b.paint(g);
			}
	        for (Boulder b : boulders[i]) {
	            b.setRelativeLocation(protPos);
	            b.paint(g);
	        }
		}
	}
	
    /**
     * Determines whether an index is valid
     */
	private boolean indexInRange(int index) {
	    return index >= 0 && index < boulders.length;
	}
}
