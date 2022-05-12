package compressors.k2raster;

import compressors.K2RasterCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.DataFormatException;

@RunWith(value = Parameterized.class)
public class K2RasterCompressorMatrixTest {

    private final Parameters parameters;

    public K2RasterCompressorMatrixTest(Parameters parameters) {
        this.parameters = parameters;
    }

    private static int getRandomNumber() {
        return (int) ((Math.random() * 16) + 0);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameters> data() {
        ArrayList<Parameters> parameters = new ArrayList<>();
        for (int i = 2; i < 8; i++) {
            int[][] matrix = new int[(int) Math.pow(2, i)][(int) Math.pow(2, i)];
            for (int j = 0; j < matrix.length; j++) {
                for (int m = 0; m < matrix[0].length; m++) {
                    matrix[j][m] = getRandomNumber();
                }
            }
            parameters.add(new Parameters(matrix));
        }
        return parameters;
    }

    @Test
    public void k2RasterCompressorMatrixTest() throws IOException, DataFormatException {
        K2RasterCompressor k2RasterCompressor = new K2RasterCompressor();
        byte[] compressedData = k2RasterCompressor.compressMatrix(parameters.matrix);
        int[][] decompressedData = k2RasterCompressor.decompressMatrix(compressedData);
        Assertions.assertArrayEquals(parameters.matrix, decompressedData);
    }

    private static class Parameters {
        private final int[][] matrix;

        private Parameters(int[][] matrix) {
            this.matrix = matrix;
        }

        @Override
        public String toString() {
            return "size=" + matrix.length * matrix[0].length;
        }
    }
}
