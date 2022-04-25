package algorithms.k2raster.mapping3D2D;

import algorithms.k2raster.utils.BitMatrix;
import algorithms.k2raster.utils.TypeUtils;
import org.geotools.util.UnsupportedImplementationException;

import java.util.Arrays;

public class MatrixConverter {

    private int index = 0;
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

    public BitMatrix encodeNDVI(double[] rasterData) {
        int[] codedMatrix = TypeUtils.doubleToPositiveLong(rasterData);
        index = rasterData.length;
        int n = 1;
        while (n < rasterData.length
                || n <= Arrays.stream(codedMatrix).max().orElse(Integer.MAX_VALUE)) {
            n *= 2;
        }

        BitMatrix result = new BitMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int m = 0; m < codedMatrix.length; m++) {
                    if (j == m && i == rasterData[m] || result.get(i, j)) {
                        result.set(i, j);
                    }
                }
            }
        }
        return result;
    }

    public double[] decodeNDVI(BitMatrix codedData) {
        int[] codedMatrix = new int[index];
        for (int i = 0; i < codedData.getWidth(); i++) {
            for (int j = 0; j < codedData.getWidth(); j++) {
                if (codedData.get(i, j)) {
                    codedMatrix[j] = i;
                }
            }
        }
        return TypeUtils.positiveLongToDouble(codedMatrix);
    }

    public int[] decode(BitMatrix codedData) {
        int[] codedMatrix = new int[index];
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
        index = rasterData.length;
        int n = 1;
        while (n < rasterData.length
                || n <= Arrays.stream(rasterData).max().orElse(Integer.MAX_VALUE)) {
            n *= 2;
        }
        BitMatrix result = new BitMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int m = 0; m < rasterData.length; m++) {
                    if (j == m && i == rasterData[m] || result.get(i, j)) {
                        result.set(i, j);
                    }
                }
            }
        }
        return result;
    }
}
