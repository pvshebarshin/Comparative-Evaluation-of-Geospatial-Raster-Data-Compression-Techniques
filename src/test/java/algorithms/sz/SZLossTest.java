package algorithms.sz;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(value = Parameterized.class)
public class SZLossTest {
    private final TestParameter parameter;

    public SZLossTest(TestParameter parameter) {
        this.parameter = parameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();

        double[] mass = new double[10000];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = ThreadLocalRandom.current().nextDouble(99999, 100000);
        }
        parameters.add(new TestParameter(mass));

        mass = new double[1000000];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = ThreadLocalRandom.current().nextDouble(99999, 100000);
        }
        parameters.add(new TestParameter(mass));

        mass = new double[10];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = ThreadLocalRandom.current().nextDouble(99999, 100000);
        }
        parameters.add(new TestParameter(mass));

        mass = new double[777];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = ThreadLocalRandom.current().nextDouble(99999, 100000);
        }
        parameters.add(new TestParameter(mass));

        mass = new double[9987654];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = ThreadLocalRandom.current().nextDouble(99999, 100000);
        }
        parameters.add(new TestParameter(mass));

        return parameters;
    }

    @Test
    public void szLosslessTest() {
        SZ sz = new SZ(0.3);
        byte[] byteArray = sz.encode(parameter.ArrayToCompress);
        double[] resultArray = sz.decode(byteArray);
        Assertions.assertEquals(parameter.ArrayToCompress.length, resultArray.length);
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
