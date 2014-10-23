package mariocraft.util;

import java.awt.image.BufferedImage;
import java.awt.Dimension;

import mariocraft.model.Images;

/**
 * Utility for animation.
 * 
 * @author Sebastian Olsson
 * @version 2011-05-05
 */
public class AnimationUtil {
    /**
     * Creates an array of resized, transparent sprite images from a sprite sheet.
     * 
     * @param ref Image file path
     * @param cols The number of columns that the sheet will be split into
     * @param rows The number of rows that the sheet will be split into
     * @param size The size of each smaller image
     * @return An array of resized, transparent sprite images
     */
    public static BufferedImage[][] createImages(String ref, int cols, int rows, Dimension size){
        BufferedImage[][] imgs = createImages(ref, cols, rows);
        resizeImages(imgs, size);
        return imgs;
    }
    
    /**
     * Creates an array of transparent sprite images from a sprite sheet.
     * 
     * @param ref Image file path
     * @param cols The number of columns that the sheet will be split into
     * @param rows The number of rows that the sheet will be split into
     * @param size The size of each smaller image
     * @return An array of transparent sprite images
     */
    public static BufferedImage[][] createImages(String ref, int cols, int rows){
        BufferedImage image = ImageUtil.makeColorTransparent(ref, Images.TRANSPARENT);
        BufferedImage[][] imgs = ImageUtil.splitImageMatrix(image, cols, rows);
        return imgs;
    }
    
    /**
     * Resizes a matrix of images.
     */
    public static void resizeImages(BufferedImage[][] images, Dimension size){
        for(int i = 0;i < images.length;i++) {
            for(int j = 0;j < images[0].length;j++) {
                images[i][j] = ImageUtil.resize(images[i][j], size.width, size.height);
            }
        }
    }
}
