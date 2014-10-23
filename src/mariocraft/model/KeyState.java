package mariocraft.model;
import java.awt.event.*;

/**
 * A class used to keep track of the keys pressed by the user.
 * 
 * @author Erik Odenman
 * @author Sebastian Olsson
 */
public class KeyState implements KeyListener {
	
	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;
	public boolean jump;
	public boolean lift;
	public boolean escape;
	public boolean cheat; // Player warps to the next level.
	
    /**
     * Change the state according to the key pressed
     */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_LEFT :	  left = 	true; 	 break;
			case KeyEvent.VK_RIGHT :  right = 	true;	 break;
	        case KeyEvent.VK_UP :     up =      true;    break;
	        case KeyEvent.VK_DOWN :   down =    true;    break;
			case KeyEvent.VK_X :      jump =    true;	 break;
			case KeyEvent.VK_Z :      lift =    true;    break;
			case KeyEvent.VK_ESCAPE : escape = 	!escape; break;
			case KeyEvent.VK_PLUS :   cheat =   true;    break;
		}
	}
	
	/**
     * Change the state according to the key released
     */
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_LEFT :	 left = 	false; break;
			case KeyEvent.VK_RIGHT : right = 	false; break;
	        case KeyEvent.VK_UP :    up =       false; break;
	        case KeyEvent.VK_DOWN :  down =     false; break;
			case KeyEvent.VK_X :     jump = 	false; break;
			case KeyEvent.VK_Z :     lift =     false; break;
			case KeyEvent.VK_PLUS :  cheat =    false; break;
		}
	}
	
	/**
	 * Nothing happens when a key is typed.
	 */
	public void keyTyped(KeyEvent e) {}
	
	/**
     * Reset the key state, i.e. set all the keys to false.
     */
	public void reset() {
		left = false;
		right = false;
	    up = false;
	    down = false;
		jump = false;
		lift = false;
		escape = false;
		cheat = false;
	}
}
