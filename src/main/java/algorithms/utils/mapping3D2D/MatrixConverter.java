package algorithms.utils.mapping3D2D;

import algorithms.utils.BitMatrix;
import algorithms.utils.TypeUtils;
import org.geotools.util.UnsupportedImplementationException;

import java.util.Arrays;

public class MatrixConverter {

    private static int index = 0;
    private final ZCurve zCurveCoder = new ZCurve();

    public boolean[][] encode(double[][] rasterData) {
        long[] mortonMassive = new long[rasterData.length * rasterData[0].length];
        for (int i = 0; i < rasterData.length; i++) {
            for (int j = 0; j < rasterData[i].length; j++) {
                mortonMassive[zCurveCoder.encode(i, j)] = Double.doubleToLongBits(rasterData[i][j]);
            }
        }
        throw new UnsupportedImplementationException("");
    }

    public int[] decode(BitMatrix codedData, int size) {
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

    public BitMatrix encode(final int[] rasterData) {
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
}
