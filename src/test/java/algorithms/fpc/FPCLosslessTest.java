package algorithms.fpc;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@RunWith(value = Parameterized.class)
public class FPCLosslessTest {

    private final WrapperForTestArray wrapper;

    public FPCLosslessTest(WrapperForTestArray wrapper) {
        this.wrapper = wrapper;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<WrapperForTestArray> data() {
        ArrayList<WrapperForTestArray> parameters = new ArrayList<>();
        parameters.add(new WrapperForTestArray(new double[]{157649563.012431551621}));
        parameters.add(new WrapperForTestArray(new double[]{0.0000000000000000000000000073}));
        parameters.add(new WrapperForTestArray(new double[]{
                157649563.012431551621, 0.0000000000000000000000000073}));
        parameters.add(new WrapperForTestArray(new double[]{
                -157649563.012431551621, -0.0000000000000000000000000073}));
        parameters.add(new WrapperForTestArray(new double[]{
                -15352435.009436452, 0.00000000000000001, 1111111111111110.5}));
        parameters.add(new WrapperForTestArray(new double[]{
                -157649563.012431551621, -0.0000000000000000000000000073, 0.0000000000000000000000000073}));
        parameters.add(new WrapperForTestArray(new double[]{
                -157649563.012431551621, -0.0000000000000000000000000073, 0.0000000000000000000000000073,
                157649563.012431551621, 0.0000000000000000000000000073}));
        parameters.add(new WrapperForTestArray(new double[]{
                -157649563.012431551621, -0.0000000000000000000000000073, 0.0000000000000000000000000073,
                157649563.012431551621, 0.0000000000000000000000000073, 234564, 2333333, 2.0,
                0.434526234875, 1234567865.98765431345}));
        Random random = new Random();
        double[] mass = new double[10000];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = random.nextInt() + random.nextDouble();
        }
        parameters.add(new WrapperForTestArray(mass));

        mass = new double[1000000];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = random.nextInt() + random.nextDouble();
        }
        parameters.add(new WrapperForTestArray(mass));

        return parameters;
    }

    @Test
    public void compressorLosslessTest() {
        FPC compressor = new FPC();

        byte[] buffer = new byte[wrapper.ArrayToCompress.length * 8 + wrapper.ArrayToCompress.length + 1];
        compressor.compress(buffer, wrapper.ArrayToCompress);
        FPC decompressor = new FPC();

        double[] resultArray = new double[wrapper.ArrayToCompress.length];
        decompressor.decompress(buffer, resultArray);

        for (int i = 0; i < wrapper.ArrayToCompress.length; i++) {
            Assertions.assertEquals(wrapper.ArrayToCompress[i], resultArray[i]);
        }
    }

    private static class WrapperForTestArray {
        private static int i = 0;
        private final double[] ArrayToCompress;

        public WrapperForTestArray(double[] ArrayToCompress) {
            this.ArrayToCompress = ArrayToCompress;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + ArrayToCompress.length;
        }
    }
}
