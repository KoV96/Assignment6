package com.shpp.p2p.cs.okurylyk.assignment6.arrays.tm;


import java.util.Arrays;


public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        double[][] results = new double[toneMatrix.length][result.length];
        int row = 0;
        boolean isPlaySilence = true;
        while (row < toneMatrix.length) {
            if (toneMatrix[row][column]) {
                results[row] = samples[row];
                isPlaySilence = false;
            }
            ++row;
        }
        if (isPlaySilence) {
            Arrays.fill(result, 0.0);
        } else {
            result = normalizeWaves(results);
        }
        return result;
    }

    /**
     * This method normalizing our sound by summing up all waves to one. And if params of this wave will be out of range
     * this method will normalize the sound for the range.
     * @param results Array with sound waves on each row in column.
     * @return Normalized wave.
     */
    private static double[] normalizeWaves(double[][] results) {
        double[] finalResult = new double[ToneMatrixConstants.sampleSize()];
        for (int col = 0; col < finalResult.length; col++) {
            for (int row = 0; row < results.length; row++) {
                if (results[row][col] != 0) {
                    finalResult[col] += results[row][col];
                }
            }
        }
        double max = findMax(finalResult);
        return checkRange(finalResult, max);
    }

    /**
     * Finds max value by module in wave array.
     * @param finalResult Summed wave.
     * @return Max value of the wave by module.
     */
    private static double findMax(double[] finalResult) {
        double max = finalResult[0];
        for (int i = 1; i < finalResult.length; i++) {
            if (Math.abs(finalResult[i]) > Math.abs(max)) {
                max = finalResult[i];
            }
        }
        return max;
    }

    /**
     * If max value is out of range each param of wave will divide by max value. If not, it will return natural
     * wave.
     * @param finalResult Summed wave.
     * @param max Max value of the wave by module
     * @return Resulting sound wave in right range.
     */
    private static double[] checkRange(double[] finalResult, double max) {
        if (Math.abs(max) > 1) {
            for (int i = 0; i < finalResult.length; i++) {
                finalResult[i] /= max;
            }
        }
        return finalResult;
    }
}
