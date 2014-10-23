package mariocraft.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mariocraft.MarioCraft;
/**
 * This class gives general utilities for the manipulation and control of images.
 * 
 * This is from the article "Ultimate Image Manipulation" by Josiah Hester found on JavaLobby.org at this link:
 * <a href="http://www.javalobby.org/articles/ultimate-image/">Ultimate Image Manipulation</a>
 * 
 * Here is the introduction:
 * "Images are the staple of any graphical application, whether on the web or on the desk, images are 
 * everywhere. Having the ability to control and manipulate these images is a crucial skill that 
 * is absolutely necessary for any graphical artist, designer, or Game Engineer. This article will 
 * get you, the aspiring artist, professional designer, or amateur hobbyist, the foundations to be 
 * able to manipulate any image to your will. The end result will be twofold, you will have a nice 
 * Image Utility class for use in any of your Projects (I found it was most useful for 2d gaming), 
 * and you will have a small, but powerful application that can perform most common, and many not 
 * so common operations on. jpg's, .png's, .gif's, and bmp image files."
 * 
 * This class may be used by anyone, just give a little credit somewhere to me if you dont mind.
 *  
 * @author Josiah Hester
 * @author Sebastian Olsson (minor tweaks)
 * @version 2011-05-06
 */
public class ImageUtil {

	private static final String IMAGE_PATH = "resources/images/";
	
	/**
	 * Loads an image from a file
	 * @param ref
	 * @return
	 */
	public static BufferedImage loadImage(String ref) {           //Note: Kan ha problem. Anv�nd ist makeColorTransparent().
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(MarioCraft.class.getResource(IMAGE_PATH + ref));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bimg;
	}
	/**
	 * Saves an image to a file
	 * @param img
	 * @param ref
	 */
	 public static void saveImage(BufferedImage img, String ref) {  
		     try {  
		         String format = (ref.endsWith(".png")) ? "png" : "jpg";  
		         ImageIO.write(img, format, new File(ref));  
		     } catch (IOException e) {  
		         e.printStackTrace();  
		     }  
		 }  
	 
	 /**
	  * This apllys translucency to an image specified by the url string.
	  * @param url
	  * @param transperancy
	  * @return
	  */
	 public static BufferedImage loadTranslucentImage(String url, float transperancy) {
			// Load the image
			BufferedImage loaded = loadImage(url);
			// Create the image using the 
			BufferedImage aimg = new BufferedImage(loaded.getWidth(), loaded.getHeight(), BufferedImage.TRANSLUCENT);
			// Get the images graphics
			Graphics2D g = aimg.createGraphics();
			// Set the Graphics composite to Alpha
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperancy));
			// Draw the LOADED img into the prepared reciver image
			g.drawImage(loaded, null, 0, 0);
			// let go of all system resources in this Graphics
			g.dispose();
			// Return the image
			return aimg;
		}
	 
	 /**
	  * Loads and image and makes a certain color transparent in the image.  THis is usually 
	  * used for masking purposes.
	  * @param ref The image location
	  * @param color THe masking color
	  * @return
	  */
	 public static BufferedImage makeColorTransparent(String ref, Color color) {
			BufferedImage image = loadImage(ref);
			BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = dimg.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.drawImage(image, null, 0, 0);
			g.dispose();
			for(int i = 0; i < dimg.getHeight(); i++) {
				for(int j = 0; j < dimg.getWidth(); j++) {
					if(dimg.getRGB(j, i) == color.getRGB()) {
					dimg.setRGB(j, i, 0x8F1C1C);
					}
				}
			}
			return dimg;
		}
	 /**
	  * Flips the image horizontally. Flipping horizontally is kind of like looking into a mirror.
	  * @param img
	  * @return
	  */
	 public static BufferedImage horizontalflip(BufferedImage img) {
			int w = img.getWidth();
			int h = img.getHeight();
			BufferedImage dimg = new BufferedImage(w, h, img.getType());
			Graphics2D g = dimg.createGraphics();
			g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
			g.dispose();
			return dimg;
		}
	 
	 /**
	  * Flips the image vertically. Flipping vertically could be compared to
	  * the reflection of yourself in a lake.
	  * @param img
	  * @return
	  */
	 public static BufferedImage verticalflip(BufferedImage img) {
			int w = img.getWidth();
			int h = img.getHeight();
			BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
			Graphics2D g = dimg.createGraphics();
			g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
			g.dispose();
			return dimg;
		}
	 
	 /**
	  * Rotates and iimage by the specified degree angle
	  * @param img
	  * @param angle
	  * @return
	  */
	 public static BufferedImage rotate(BufferedImage img, int angle) {
			int w = img.getWidth();
			int h = img.getHeight();
			BufferedImage dimg = new BufferedImage(w, h, img.getType());
			Graphics2D g = dimg.createGraphics();
			g.rotate(Math.toRadians(angle), w/2, h/2);
			g.drawImage(img, null, 0, 0);
			return dimg;
		}
	 
	 /**
	  * Resizes an image
	  * @param img
	  * @param newW  The new width of the image
	  * @param newH  The new width of the image
	  * @return
	  */
	 public static BufferedImage resize(BufferedImage img, int newW, int newH) {
			int w = img.getWidth();
			int h = img.getHeight();
			BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
			Graphics2D g = dimg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
			g.dispose();
			return dimg;
		}
	 
	 /**
	  * Splits an image int cols*rows images and puts them into an array of images.  This 
	  * is used for animation usually.
	  * @param img
	  * @param cols
	  * @param rows
	  * @return
	  */
	 public static BufferedImage[] splitImage(BufferedImage img, int cols, int rows) {
		int w = img.getWidth()/cols;
		int h = img.getHeight()/rows;
		int num = 0;
		BufferedImage imgs[] = new BufferedImage[cols*rows];
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				imgs[num] = new BufferedImage(w, h, img.getType());
				// Tell the graphics to draw only one block of the image
				Graphics2D g = imgs[num].createGraphics();
				g.drawImage(img, 0, 0, w, h, w*x, h*y, w*x+w, h*y+h, null);
				g.dispose();
				num++;
			}
		}
		return imgs;
	}
	 
	   /**
      * Splits an image into rows*cols images and puts them into a matrix of images.  This 
      * is used for animation usually.
      * @param img
      * @param cols
      * @param rows
      * @return
      */
     public static BufferedImage[][] splitImageMatrix(BufferedImage img, int cols, int rows) {
        int w = img.getWidth()/cols;
        int h = img.getHeight()/rows;
        BufferedImage imgs[][] = new BufferedImage[rows][cols];
        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < cols; x++) {
                imgs[y][x] = new BufferedImage(w, h, img.getType());
                // Tell the graphics to draw only one block of the image
                Graphics2D g = imgs[y][x].createGraphics();
                g.drawImage(img, 0, 0, w, h, w*x, h*y, w*x+w, h*y+h, null);
                g.dispose();
            }
        }
        return imgs;
    }
}
