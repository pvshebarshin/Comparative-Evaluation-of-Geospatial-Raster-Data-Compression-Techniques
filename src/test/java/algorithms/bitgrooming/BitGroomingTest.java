package algorithms.bitgrooming;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class BitGroomingTest {
    private final TestParameter testParameter;
    private static final double PI = 3.14159265358979323846264338327950288;

    public BitGroomingTest(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.125}, NSD.NSD1));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.140625}, NSD.NSD2));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.140625}, NSD.NSD3));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.14154052734375}, NSD.NSD4));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415863037109375}, NSD.NSD5));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592025756836}, NSD.NSD6));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926218032837}, NSD.NSD7));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592651605606}, NSD.NSD8));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926534682512}, NSD.NSD9));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926535846666}, NSD.NSD10));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926535846666}, NSD.NSD11));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589214}, NSD.NSD12));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926535897825}, NSD.NSD13));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.1415926535897896}, NSD.NSD14));
        parameters.add(new TestParameter(new double[]{PI}, new double[]{3.141592653589793}, NSD.NSD15));
        return parameters;
    }

    @Test
    public void bitGroomingTest() {
        BitGrooming bitGrooming = new BitGrooming();
        double[] result = bitGrooming.encode(testParameter.inputData, testParameter.nsd);
        for (int i = 0; i < result.length; i++) {
            Assertions.assertEquals(result[i], testParameter.outputData[i], "Incorrect precision reduction");
        }
    }

    private static class TestParameter {
        private static int i = 0;
        private final double[] inputData;
        private final double[] outputData;
        private final NSD nsd;

        public TestParameter(double[] inputData, double[] outputData, NSD nsd) {
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
