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
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589794}, 16));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 17));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 18));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 19));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 20));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 21));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 22));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 23));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 24));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 25));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 26));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 27));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 28));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 29));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 30));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 31));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 32));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 33));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 34));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 35));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 36));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 37));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 38));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 39));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 40));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 41));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 42));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 43));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 44));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 45));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 46));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 47));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 48));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 49));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 50));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 51));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, 52));

        return parameters;
    }

    @Test
    public void digitRoutingTest() {
        DigitRouting digitRouting = new DigitRouting();
        double[] result = digitRouting.encode(testParameter.inputData, testParameter.nsd);
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
