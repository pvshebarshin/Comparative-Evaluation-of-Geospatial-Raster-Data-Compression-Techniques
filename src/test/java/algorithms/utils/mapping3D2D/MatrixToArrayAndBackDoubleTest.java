package algorithms.utils.mapping3D2D;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(value = Parameterized.class)
public class MatrixToArrayAndBackDoubleTest {

    private final TestParameter testParameter;

    public MatrixToArrayAndBackDoubleTest(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();

        double[][] parameter = new double[8][8];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextDouble(-100, 100);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new double[16][16];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextDouble(-1000, 1000);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new double[32][32];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextDouble(-1000, 1000);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new double[64][64];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextDouble(-1000, 1000);
            }
        }
        parameters.add(new TestParameter(parameter));

        parameter = new double[128][128];
        for (int i = 0; i < parameter.length; i++) {
            for (int j = 0; j < parameter[0].length; j++) {
                parameter[i][j] = ThreadLocalRandom.current().nextDouble(-1000, 1000);
            }
        }
        parameters.add(new TestParameter(parameter));

        return parameters;
    }

    @Test
    public void matrixConverterTest() {
        MatrixConverter matrixConverter = new MatrixConverter();
        double[][] res = matrixConverter.arrayToMatrix(matrixConverter.matrixToArray(testParameter.data));
        Assertions.assertArrayEquals(
                res,
                testParameter.data
        );
    }

    private static class TestParameter {

        private static int i = 0;

        private final double[][] data;

        public TestParameter(double[][] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + data.length * data[0].length;
        }
    }
}
