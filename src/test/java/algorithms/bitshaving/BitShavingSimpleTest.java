package algorithms.bitshaving;

import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class BitShavingSimpleTest {

    private final Parameters parameters;

    public BitShavingSimpleTest(Parameters parameters) {
        this.parameters = parameters;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameters> data() {
        ArrayList<Parameters> parameters = new ArrayList<>();
        for (int i = 0; i <= 52; i++) {
            parameters.add(new Parameters(i));
        }
        return parameters;
    }

    @Test
    public void bitShavingTest() {
        double[] value = {Double.MAX_VALUE};
        BitShavingDouble bitShavingDouble = new BitShavingDouble(parameters.bits);
        if (parameters.bits < 52) {
            Assertions.assertNotEquals(Double.MAX_VALUE, bitShavingDouble.encode(value)[0]);
        } else {
            Assertions.assertEquals(Double.MAX_VALUE, bitShavingDouble.encode(value)[0]);
        }
    }

    @Test
    public void bitShavingFloatTest() {
        float[] value = {Float.MAX_VALUE};
        BitShavingFloat bitShavingDouble = new BitShavingFloat(parameters.bits);
        if (parameters.bits < 23) {
            Assertions.assertNotEquals(Float.MAX_VALUE, bitShavingDouble.encode(value)[0]);
        } else {
            Assertions.assertEquals(Float.MAX_VALUE, bitShavingDouble.encode(value)[0]);
        }
    }

    private static class Parameters {
        private final int bits;

        public Parameters(int bits) {
            this.bits = bits;
        }

        @Override
        public String toString() {
            return "bits - " + bits;
        }
    }
}
