package algorithms.digitrouting;

import algorithms.digitrounding.DigitRouting;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class DigitRoutingTest {

    private final TestParameter testParameter;

    private static final double PI = 3.14159265358979323846264338327950288;

    public DigitRoutingTest(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.0}, 1));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1875}, 2));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1484375}, 3));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1416015625}, 4));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.14154052734375}, 5));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415939331054688}, 6));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415929794311523}, 7));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926814079285}, 8));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926590561867}, 9));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592654399574}, 10));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653642874}, 11));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926535919425}, 12));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589214}, 13));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926535898393}, 14));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926535897896}, 15));
        return parameters;
    }

    @Test
    public void bitGroomingTest() {
        DigitRouting bitGrooming = new DigitRouting();
        double[] result = bitGrooming.encode(testParameter.inputData, testParameter.nsd);
        for (int i = 0; i < result.length; i++) {
            Assertions.assertEquals(result[i], testParameter.outputData[i], "Incorrect precision reduction");
        }
    }

    private static class TestParameter {
        private static int i = 0;
        private final double[] inputData;
        private final double[] outputData;
        private final int nsd;

        public TestParameter(double[] inputData, double[] outputData, int nsd) {
            this.inputData = inputData;
            this.outputData = outputData;
            this.nsd = nsd;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + inputData.length;
        }
    }
}
