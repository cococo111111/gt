package com.fu.common.util;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by manlm on 9/29/2016.
 */
public class ImageUtil {

    private static final Logger LOG = Logger.getLogger(AESUtil.class);

    private static final int DEFAULT_WIDTH = 475;
    private static final int DEFAULT_HEIGHT = 250;

    private ImageUtil() {
        // Default Constructor
    }

    public static byte[] generateThumbNail(String url) {
        LOG.info("[generateThumbNail] Start: url = " + url);

        try {
            BufferedImage image1 = ImageIO.read(new URL(url));
            BufferedImage image2 = ImageIO.read(new URL("https://manlm.s3.amazonaws.com/km.png"));

            int height = image1.getHeight() * DEFAULT_WIDTH / image1.getWidth();

            if (height < 250) {
                image1 = resize(image1, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            } else {
                image1 = resize(image1, DEFAULT_WIDTH, height);
                image1 = cropImage(image1, 0, (height - DEFAULT_HEIGHT) / 2, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            }

            image1 = drawImageOnImage(image1, image2, 0);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image1, "png", baos);
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            LOG.info("[generateThumbNail] End");
            return imageInByte;
        } catch (IOException e) {
            LOG.error("[generateThumbNail] IOException: " + e);
            return new byte[0];
        }
    }

    /**
     * Draw image2 on image1
     *
     * @param text
     * @param image1
     * @param image2
     * @param space
     * @return
     */
    private static BufferedImage drawImageOnImage(BufferedImage image1, BufferedImage image2, int space) {
        LOG.info("[drawImageOnImage] Start");
        BufferedImage bi = new BufferedImage(image1.getWidth(), image1.getHeight() + space, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.drawImage(image1, 0, 0, null);
        g2d.drawImage(image2, 0, 0, null);
        g2d.dispose();
        LOG.info("[drawImageOnImage] End");
        return bi;
    }

    private static int shiftNorth(int p, int distance) {
        return p - distance;
    }

    private static int shiftSouth(int p, int distance) {
        return p + distance;
    }

    private static int shiftEast(int p, int distance) {
        return p + distance;
    }

    private static int shiftWest(int p, int distance) {
        return p - distance;
    }

    /**
     * Resize image
     *
     * @param image
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage resize(BufferedImage image, int width, int height) {
        LOG.info(new StringBuilder("[resize] Start: width = ").append(width)
                .append(", height = ").append(height));

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        LOG.info("[resize] End");
        return bi;
    }

    /**
     * Crop image
     *
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height) {
        LOG.info(new StringBuilder("[cropImage] Start: x = ").append(x)
                .append(", y = ").append(y)
                .append(", width = ").append(width)
                .append(", height = ").append(height));
        LOG.info("[cropImage] End");
        return image.getSubimage(x, y, width, height);
    }
}
