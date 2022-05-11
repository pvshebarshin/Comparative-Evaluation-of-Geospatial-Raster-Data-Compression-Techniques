package algorithms.utils.mapping3D2D;

import algorithms.utils.BitMatrix;

import java.util.Arrays;

public class MatrixConverter {

    private static final ZCurve zCurveCoder = new ZCurve();

    public int[] decodeArray(BitMatrix codedData, int size) {
        int[] codedMatrix = new int[size];
        for (int i = 0; i < codedData.getHeight(); i++) {
            for (int j = 0; j < codedData.getWidth(); j++) {
                if (codedData.get(i, j)) {
                    codedMatrix[j] = i;
                }
            }
        }
        return codedMatrix;
    }

    public BitMatrix encodeArray(final int[] rasterData) {
        int n = 1;
        while (n < rasterData.length
                || n <= Arrays.stream(rasterData).max().orElse(Integer.MAX_VALUE)) {
            n *= 2;
        }
        BitMatrix result = new BitMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < rasterData.length; j++) {
                if (i == rasterData[j]) {
                    result.set(i, j);
                }
            }
        }
        return result;
    }

    public BitMatrix encodeMatrix(int[][] rasterData) {
        int[] mortonMassive = new int[rasterData.length * rasterData[0].length];
        for (int i = 0; i < rasterData.length; i++) {
            for (int j = 0; j < rasterData[i].length; j++) {
                mortonMassive[zCurveCoder.encode(i, j)] = rasterData[i][j];
            }
        }
        return encodeArray(mortonMassive);
    }

    public int[][] decodeMatrix(BitMatrix codedData, int size) {
        int[] codedArray = decodeArray(codedData, size);
        int[][] result = new int[(int) Math.sqrt(codedArray.length)][(int) Math.sqrt(codedArray.length)];
        int[] xy;
        for (int i = 0; i < codedArray.length; i++) {
            xy = zCurveCoder.decode(i);
            result[xy[0]][xy[1]] = codedArray[i];
        }
        return result;
    }
}
