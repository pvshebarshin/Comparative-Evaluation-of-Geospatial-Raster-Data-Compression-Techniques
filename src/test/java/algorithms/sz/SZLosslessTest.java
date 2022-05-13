package algorithms.sz;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(value = Parameterized.class)
public class SZLosslessTest {

    private final TestParameter parameter;

    public SZLosslessTest(TestParameter parameter) {
        this.parameter = parameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();
        parameters.add(new TestParameter(new double[]{-0.12431551621, -0.073, 0.3, 0.12431551621, 0.73}));
        parameters.add(new TestParameter(new double[]{
                -0.12431551621, -0.73, 0.073,
                0.12431551621, 0.3, 234564, 2333333, 2.0,
                0.434526234875, 0.98765431345}));

        double[] mass = new double[10000];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
        parameters.add(new TestParameter(mass));

        mass = new double[1000000];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = ThreadLocalRandom.current().nextDouble(-10000, 10000);
        }
        parameters.add(new TestParameter(mass));

        return parameters;
    }

    @Test
    public void szLosslessTest() throws SZByteCodeException {
        SZ sz = new SZ(0.0);
        byte[] byteArray = sz.encode(parameter.ArrayToCompress);
        double[] resultArray = sz.decode(byteArray);
        for (int i = 0; i < parameter.ArrayToCompress.length; i++) {
            Assertions.assertEquals(parameter.ArrayToCompress[i], resultArray[i], 1E-6);
        }
    }

    private static class TestParameter {
        private static int i = 0;
        private final double[] ArrayToCompress;

        public TestParameter(double[] ArrayToCompress) {
            this.ArrayToCompress = ArrayToCompress;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + ArrayToCompress.length;
        }
    }
}
