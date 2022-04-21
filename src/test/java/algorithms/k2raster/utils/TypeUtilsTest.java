package algorithms.k2raster.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(value = Parameterized.class)
public class TypeUtilsTest {

    private final TestParameter testParameter;

    public TypeUtilsTest(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();

        double[] data = new double[100];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
        parameters.add(new TestParameter(data));

        data = new double[10000];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
        parameters.add(new TestParameter(data));

        data = new double[10000000];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
        parameters.add(new TestParameter(data));

        data = new double[45678];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
        parameters.add(new TestParameter(data));

        data = new double[777];
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
        parameters.add(new TestParameter(data));

        return parameters;
    }

    @Test
    public void typeUtilsTest() {
        Assertions.assertArrayEquals(
                TypeUtils.positiveLongToDouble(TypeUtils.doubleToPositiveLong(testParameter.data)),
                testParameter.data
        );
    }

    private static class TestParameter {
        private static int i = 0;
        private final double[] data;

        public TestParameter(double[] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + data.length;
        }
    }
}
