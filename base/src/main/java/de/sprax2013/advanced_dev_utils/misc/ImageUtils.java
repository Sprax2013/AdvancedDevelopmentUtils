package de.sprax2013.advanced_dev_utils.misc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    /**
     * Resize an image while keeping its aspect ratio.<br>
     * <br>
     * Short for:
     * <code>ImageUtils.resizeImage(ImageIO.read(file), maxX, maxY)</code>
     *
     * @param file The image to resize
     * @param maxX The max value of X
     * @param maxY The max value of Y
     *
     * @return The resized image
     *
     * @throws IOException If an error occurs during reading.
     * @see #resizeImage(BufferedImage, int, int)
     */
    public static BufferedImage resizeImage(File file, int maxX, int maxY) throws IOException {
        return resizeImage(ImageIO.read(file), maxX, maxY);
    }

    /**
     * Resize an image while keeping its aspect ratio.
     *
     * @param img  The image to resize
     * @param maxX The max value of X
     * @param maxY The max value of Y
     *
     * @return The resized image
     */
    public static BufferedImage resizeImage(BufferedImage img, int maxX, int maxY) {
        Dimension dim = getScaledDimension(new Dimension(img.getWidth(), img.getHeight()), new Dimension(maxX, maxY));

        BufferedImage result = new BufferedImage((int) dim.getWidth(), (int) dim.getHeight(), img.getType());

        Graphics2D g = result.createGraphics();
        g.drawImage(img, 0, 0, result.getWidth(), result.getHeight(), null);
        g.dispose();

        return result;
    }

    private static Dimension getScaledDimension(Dimension imageSize, Dimension maxSize) {
        double ratio = Math.min(maxSize.getWidth() / imageSize.getWidth(), maxSize.getHeight() / imageSize.getHeight());

        return new Dimension((int) (imageSize.width * ratio), (int) (imageSize.height * ratio));
    }
}