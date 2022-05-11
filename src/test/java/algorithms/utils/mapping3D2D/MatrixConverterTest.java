package algorithms.utils.mapping3D2D;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(value = Parameterized.class)
public class MatrixConverterTest {

    private final TestParameter testParameter;

    public MatrixConverterTest(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();

        int[][] parameter = new int[8][8];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextInt(1, 16);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new int[16][16];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextInt(0, 128);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new int[32][32];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextInt(0, 256);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new int[64][64];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextInt(0, 512);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new int[128][128];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextInt(0, 1024);
            }
        }
        parameters.add(new TestParameter(parameter));

        return parameters;
    }

    @Test
    public void matrixConverterTest() {
        MatrixConverter matrixConverter = new MatrixConverter();
        int[][] res = matrixConverter.decodeMatrix(
                matrixConverter.encodeMatrix(testParameter.data),
                (int) Math.pow(testParameter.data.length, 2.0)
        );
        Assertions.assertArrayEquals(
                res,
                testParameter.data
        );
    }

    private static class TestParameter {

        private static int i = 0;
        private final int[][] data;

        public TestParameter(int[][] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + data.length;
        }
    }
}
