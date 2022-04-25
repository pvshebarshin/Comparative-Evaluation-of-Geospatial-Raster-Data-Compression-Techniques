//package algorithms.k2raster.mapping3D2D;
//
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.concurrent.ThreadLocalRandom;
//
//@RunWith(value = Parameterized.class)
//public class MatrixConverterNDVITest {
//
//    private final TestParameter testParameter;
//
//    public MatrixConverterNDVITest(TestParameter testParameter) {
//        this.testParameter = testParameter;
//    }
//
//    @Parameterized.Parameters(name = "{0}")
//    public static Collection<TestParameter> data() {
//        ArrayList<TestParameter> parameters = new ArrayList<>();
//
////        double[] data = new double[4];
////        for (int i = 0; i < data.length; i++) {
////            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
////        }
////        parameters.add(new TestParameter(data));
//
////        data = new double[8];
////        for (int i = 0; i < data.length; i++) {
////            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
////        }
////        parameters.add(new TestParameter(data));
//
////        data = new double[16];
////        for (int i = 0; i < data.length; i++) {
////            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
////        }
////        parameters.add(new TestParameter(data));
////
////        data = new double[32];
////        for (int i = 0; i < data.length; i++) {
////            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
////        }
////        parameters.add(new TestParameter(data));
////
////        data = new double[64];
////        for (int i = 0; i < data.length; i++) {
////            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
////        }
////        parameters.add(new TestParameter(data));
//
////        data = new double[128];
////        for (int i = 0; i < data.length; i++) {
////            data[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
////        }
////        parameters.add(new TestParameter(data));
//
//        return parameters;
//    }
//
//    @Test
//    public void matrixConverterNDVITest() {
//        MatrixConverter matrixConverter = new MatrixConverter();
//        double[] res = matrixConverter.decodeNDVI(matrixConverter.encodeNDVI(testParameter.data));
//        Assertions.assertArrayEquals(
//                res,
//                testParameter.data
//        );
//    }
//
//    private static class TestParameter {
//        private static int i = 0;
//        private final double[] data;
//
//        public TestParameter(double[] data) {
//            this.data = data;
//        }
//
//        @Override
//        public String toString() {
//            return "Array " + ++i + " Length = " + data.length;
//        }
//    }
//}
