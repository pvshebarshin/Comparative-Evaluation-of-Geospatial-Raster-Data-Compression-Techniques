package algorithms.bitshaving;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class BitShavingTest {

    private final TestParameter testParameter;

    private static final double PI = 3.14159265358979323846264338327950288;

    public BitShavingTest(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.0}, 1));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.0}, 2));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.0}, 3));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.125}, 4));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.125}, 5));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.125}, 6));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.140625}, 7));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.140625}, 8));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.140625}, 9));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.140625}, 10));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.140625}, 11));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.14111328125}, 12));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141357421875}, 13));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1414794921875}, 14));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.14154052734375}, 15));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141571044921875}, 16));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415863037109375}, 17));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415863037109375}, 18));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141590118408203}, 19));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592025756836}, 20));
        return parameters;
    }

    @Test
    public void bitShavingDoubleTest() {
        BitShavingDouble bitGrooming = new BitShavingDouble(testParameter.bits);
        double[] result = bitGrooming.encode(testParameter.inputData);
        for (int i = 0; i < result.length; i++) {
            Assertions.assertEquals(result[i], testParameter.outputData[i], "Incorrect precision reduction");
        }
    }

    private static class TestParameter {
        private static int i = 0;
        private final double[] inputData;
        private final double[] outputData;
        private final int bits;

        public TestParameter(double[] inputData, double[] outputData, int bits) {
            this.inputData = inputData;
            this.outputData = outputData;
            this.bits = bits;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + inputData.length;
        }
    }
}
