package algorithms.k2raster.mapping3D2D;

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

        int[] data = new int[8];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        parameters.add(new TestParameter(data));

        data = new int[16];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        parameters.add(new TestParameter(data));

        data = new int[32];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 100);
        }
        parameters.add(new TestParameter(data));

        data = new int[64];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 50);
        }
        parameters.add(new TestParameter(data));

        data = new int[128];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        parameters.add(new TestParameter(data));

        data = new int[256];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 1000);
        }
        parameters.add(new TestParameter(data));

        data = new int[512];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        parameters.add(new TestParameter(data));

        data = new int[1024];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        parameters.add(new TestParameter(data));

        return parameters;
    }

    @Test
    public void matrixConverterTest() {
        MatrixConverter matrixConverter = new MatrixConverter();
        int[] res = matrixConverter.decode(matrixConverter.encode(testParameter.data));
        Assertions.assertArrayEquals(
                res,
                testParameter.data
        );
    }

    private static class TestParameter {
        private static int i = 0;
        private final int[] data;

        public TestParameter(int[] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + data.length;
        }
    }
}
