package com.shpp.p2p.cs.okurylyk.assignment6.arrays.sg;

import acm.graphics.*;

public class SteganographyLogic {
    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {
        int[][] imageByPixels;
        imageByPixels = source.getPixelArray();
        boolean[][] message = new boolean[imageByPixels.length][imageByPixels[0].length];
        for (int i = 0; i < imageByPixels.length; i++) {
            checkPresentRow(i, imageByPixels, message);
        }
        return message;
    }

    /**
     * Takes one row of pixel array of image and check each pixel in this row. Result of this check write up to message
     * array.
     *
     * @param row           Present row of an array.
     * @param imageByPixels Image array of pixels.
     * @param message       A row of hidden message, expressed as a boolean array.
     */
    private static void checkPresentRow(int row, int[][] imageByPixels, boolean[][] message) {
        for (int col = 0; col < imageByPixels[row].length; col++) {
            message[row][col] = checkEachPixel(imageByPixels[row][col]);
        }
    }

    /**
     * Takes a value of red param present pixel and check if it is odd or even.
     *
     * @param imagePixel Present pixel from the row.
     * @return True if red param is od or false if even.
     */
    private static boolean checkEachPixel(int imagePixel) {
        int redPixelValue = GImage.getRed(imagePixel);
        return (redPixelValue % 2 != 0);
    }

    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     * You can assume that the dimensions of the message and the image are the same.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {
        int[][] imageByPixels = source.getPixelArray();
        int[][] hideImage = new int[imageByPixels.length][imageByPixels[0].length];
        for (int i = 0; i < imageByPixels.length; i++) {
            hideOneRow(i, imageByPixels, message, hideImage);
        }
        return new GImage(hideImage);
    }

    /**
     * Takes one row of pixel array and one row of message array and check each RED element of image pixel in row.
     * Result of this check write up to new image array.
     *
     * @param row           Present row.
     * @param imageByPixels Image represented as array of pixels.
     * @param message       The message to hide.
     * @param hideImage     New hidden image represented as array of pixels.
     */
    private static void hideOneRow(int row, int[][] imageByPixels, boolean[][] message, int[][] hideImage) {
        int red, green, blue;
        for (int col = 0; col < imageByPixels[row].length; col++) {
            red = checkRed(imageByPixels[row][col], message[row][col]);
            green = GImage.getGreen(imageByPixels[row][col]);
            blue = GImage.getBlue(imageByPixels[row][col]);
            hideImage[row][col] = GImage.createRGBPixel(red, green, blue);
        }
    }

    /**
     * Check value of red pixel param. If it needs makes it odd or even.
     *
     * @param imageRedPixel  Element from pixel row.
     * @param messageElement Element of message to hide.
     * @return New value of red pixel param.
     */
    private static int checkRed(int imageRedPixel, boolean messageElement) {
        int redPixelValue;
        imageRedPixel = GImage.getRed(imageRedPixel);
        if (messageElement) {
            redPixelValue = (imageRedPixel % 2 == 0 ? imageRedPixel + 1 : imageRedPixel);
        } else {
            redPixelValue = (imageRedPixel % 2 != 0 ? imageRedPixel - 1 : imageRedPixel);
        }
        return redPixelValue;
    }
}
