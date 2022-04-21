package algorithms.k2raster.mapping3D2D;

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

    public boolean[][] encode(double[] rasterData) {
        long[] codedMatrix = TypeUtils.doubleToPositiveLong(rasterData);
        index = codedMatrix.length;
        int n = 1;
        while (n * 2 < rasterData.length * rasterData.length
                && n * 2L < Arrays.stream(codedMatrix).max().orElse(Long.MAX_VALUE)) {
            n *= 2;
        }
        boolean[][] result = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int m = 0; m < codedMatrix.length; m++) {
                    result[i][j] = j == m && i == codedMatrix[m];
                }
            }
        }
        return result;
    }

    public double[] decode(boolean[][] codedData) {
        long[] codedMatrix = new long[index];
        for (int i = 0; i < codedData.length; i++) {
            for (int j = 0; j < codedData[0].length; j++) {
                if (codedData[i][j]) {
                    codedMatrix[j] = i;
                }
            }
        }
        return TypeUtils.positiveLongToDouble(codedMatrix);
    }
}
