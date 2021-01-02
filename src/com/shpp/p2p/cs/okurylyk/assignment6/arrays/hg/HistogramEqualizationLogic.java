package com.shpp.p2p.cs.okurylyk.assignment6.arrays.hg;

public class HistogramEqualizationLogic {
    private static final int MAX_LUMINANCE = 255;

    /**
     * Given the luminances of the pixels in an image, returns a histogram of the frequencies of
     * those luminances.
     * <p/>
     * You can assume that pixel luminances range from 0 to MAX_LUMINANCE, inclusive.
     *
     * @param luminances The luminances in the picture.
     * @return A histogram of those luminances.
     */
    public static int[] histogramFor(int[][] luminances) {
        int[] histogram = new int[MAX_LUMINANCE + 1];
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = checkLumOfPixels(luminances, i);
        }
        return histogram;
    }

    /**
     * Checking Luminances of each pixel row by row.
     *
     * @param luminances  The luminances in the picture.
     * @param neededValue Present number of histogram position.
     * @return Number of pixels in which luminances are equals to histogram position.
     */
    private static int checkLumOfPixels(int[][] luminances, int neededValue) {
        int numberOfLum = 0;
        for (int row = 0; row < luminances.length; row++) {
            numberOfLum += checkRowForNeededValue(luminances[row], neededValue);
        }
        return numberOfLum;
    }

    /**
     * Checking Luminances of each pixel in a row.
     *
     * @param luminanceRow Present row of luminances array.
     * @param neededValue  Present number of histogram position.
     * @return Number of pixels in a row which luminances are equals to histogram position
     */
    private static int checkRowForNeededValue(int[] luminanceRow, int neededValue) {
        int valueNumber = 0;
        for (int col = 0; col < luminanceRow.length; col++) {
            if (neededValue == luminanceRow[col]) {
                valueNumber++;
            }
        }
        return valueNumber;
    }

    /**
     * Given a histogram of the luminances in an image, returns an array of the cumulative
     * frequencies of that image.  Each entry of this array should be equal to the sum of all
     * the array entries up to and including its index in the input histogram array.
     * <p/>
     * For example, given the array [1, 2, 3, 4, 5], the result should be [1, 3, 6, 10, 15].
     *
     * @param histogram The input histogram.
     * @return The cumulative frequency array.
     */
    public static int[] cumulativeSumFor(int[] histogram) {
        int[] cumulativeHistogram = new int[MAX_LUMINANCE + 1];
        for (int i = 0; i < cumulativeHistogram.length; i++) {
            if (i == 0) {
                cumulativeHistogram[i] = histogram[i];
            } else {
                cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
            }
        }
        return cumulativeHistogram;
    }

    /**
     * Returns the total number of pixels in the given image.
     *
     * @param luminances A matrix of the luminances within an image.
     * @return The total number of pixels in that image.
     */
    public static int totalPixelsIn(int[][] luminances) {
        int totalPixels;
        totalPixels = luminances.length * luminances[0].length;
        return totalPixels;
    }

    /**
     * Applies the histogram equalization algorithm to the given image, represented by a matrix
     * of its luminances.
     * <p/>
     * You are strongly encouraged to use the three methods you have implemented above in order to
     * implement this method.
     *
     * @param luminances The luminances of the input image.
     * @return The luminances of the image formed by applying histogram equalization.
     */
    public static int[][] equalize(int[][] luminances) {
        int totalPixels = totalPixelsIn(luminances);
        int[][] newLuminances = new int[luminances.length][luminances[0].length];
        int[] cumulativeHistogram = cumulativeSumFor(histogramFor(luminances));
        for (int row = 0; row < newLuminances.length; row++) {
            newLuminances[row] = makeNewRow(row, luminances, totalPixels, cumulativeHistogram);
        }
        return newLuminances;
    }

    /**
     * Creating a row of new array luminances of the image.
     *
     * @param row                 Present row of this array.
     * @param luminances          Former array of luminances.
     * @param totalPixels         The number of total pixels in image.
     * @param cumulativeHistogram Array of the cumulative frequencies of that image.
     * @return Array which consist luminances values of one row.
     */
    private static int[] makeNewRow(int row, int[][] luminances, int totalPixels, int[] cumulativeHistogram) {
        int[] newRow = new int[luminances[row].length];
        for (int cols = 0; cols < newRow.length; cols++) {
            newRow[cols] = MAX_LUMINANCE * cumulativeHistogram[(luminances[row][cols])] / totalPixels;
        }
        return newRow;
    }
}
