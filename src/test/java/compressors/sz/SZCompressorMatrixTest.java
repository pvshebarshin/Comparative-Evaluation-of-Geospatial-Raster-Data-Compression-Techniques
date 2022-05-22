package compressors.sz;

import compressors.SZCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(value = Parameterized.class)
public class SZCompressorMatrixTest {

    private final Parameters parameters;

    public SZCompressorMatrixTest(Parameters parameters) {
        this.parameters = parameters;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameters> data() {
        ArrayList<Parameters> parameters = new ArrayList<>();
        for (int i = 2; i < 8; i++) {
            double[][] matrix = new double[(int) Math.pow(2, i)][(int) Math.pow(2, i)];
            for (int j = 0; j < matrix.length; j++) {
                for (int m = 0; m < matrix[0].length; m++) {
                    matrix[j][m] = ThreadLocalRandom.current().nextDouble(9999, 100000);
                }
            }
            parameters.add(new Parameters(matrix));
        }
        return parameters;
    }

    @Test
    public void k2RasterCompressorMatrixTest() throws IOException {
        SZCompressor szCompressor = new SZCompressor(0.05);
        byte[] compressedData = szCompressor.compressMatrix(parameters.matrix);
        double[][] decompressedData = szCompressor.decompressMatrix(compressedData);
        for(int i = 0; i < decompressedData.length; i++) {
            Assertions.assertArrayEquals(parameters.matrix[i], decompressedData[i], 0.3);
        }
    }

    private static class Parameters {

        private final double[][] matrix;

        private Parameters(double[][] matrix) {
            this.matrix = matrix;
        }

        @Override
        public String toString() {
            return "size=" + matrix.length * matrix[0].length;
        }
    }
}
