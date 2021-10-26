package algorithms.bitshaving;

import compressors.BitShavingCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@RunWith(value = Parameterized.class)
public class BitShavingCompressorTest {

    private final Parameters parameters;

    public BitShavingCompressorTest(Parameters parameters) {
        this.parameters = parameters;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameters> data() {
        ArrayList<Parameters> parameters = new ArrayList<>();
        int i = 0;
        parameters.add(new Parameters(i++, 10000));
        parameters.add(new Parameters(i++, 64));
        parameters.add(new Parameters(i++, 87));
        parameters.add(new Parameters(i++, 4235));
        parameters.add(new Parameters(i++, 423));
        parameters.add(new Parameters(i++, 999));
        parameters.add(new Parameters(i++, 222));
        parameters.add(new Parameters(i++, 111));
        parameters.add(new Parameters(i++, 1234567));
        parameters.add(new Parameters(i++, 98));
        parameters.add(new Parameters(i++, 66));
        parameters.add(new Parameters(i++, 8));
        parameters.add(new Parameters(i++, 2));
        parameters.add(new Parameters(i++, 46));
        parameters.add(new Parameters(i++, 44));
        parameters.add(new Parameters(i++, 76));
        parameters.add(new Parameters(i++, 953));

        parameters.add(new Parameters(i++, 100));
        parameters.add(new Parameters(i++, 643));
        parameters.add(new Parameters(i++, 847));
        parameters.add(new Parameters(i++, 4358));
        parameters.add(new Parameters(i++, 43));
        parameters.add(new Parameters(i++, 99));
        parameters.add(new Parameters(i++, 422));
        parameters.add(new Parameters(i++, 161));
        parameters.add(new Parameters(i++, 13457));
        parameters.add(new Parameters(i++, 98));
        parameters.add(new Parameters(i++, 676));
        parameters.add(new Parameters(i++, 85));
        parameters.add(new Parameters(i++, 23));
        parameters.add(new Parameters(i++, 426));
        parameters.add(new Parameters(i++, 64));
        parameters.add(new Parameters(i++, 96));
        parameters.add(new Parameters(i++, 933));

        parameters.add(new Parameters(i++, 8000));
        parameters.add(new Parameters(i++, 645));
        parameters.add(new Parameters(i++, 837));
        parameters.add(new Parameters(i++, 42235));
        parameters.add(new Parameters(i++, 4273));
        parameters.add(new Parameters(i++, 9969));
        parameters.add(new Parameters(i++, 222));
        parameters.add(new Parameters(i++, 1151));
        parameters.add(new Parameters(i++, 14567));
        parameters.add(new Parameters(i++, 928));
        parameters.add(new Parameters(i++, 666));
        parameters.add(new Parameters(i++, 83));
        parameters.add(new Parameters(i++, 25));
        parameters.add(new Parameters(i++, 436));
        parameters.add(new Parameters(i++, 644));
        parameters.add(new Parameters(i++, 764));
        parameters.add(new Parameters(i++, 9353));

        parameters.add(new Parameters(i++, 700));
        parameters.add(new Parameters(i++, 674));
        parameters.add(new Parameters(i++, 787));
        parameters.add(new Parameters(i++, 42357));
        parameters.add(new Parameters(i++, 4723));
        parameters.add(new Parameters(i++, 9979));
        parameters.add(new Parameters(i++, 722));
        parameters.add(new Parameters(i++, 117));
        parameters.add(new Parameters(i++, 7567));
        parameters.add(new Parameters(i++, 987));
        parameters.add(new Parameters(i++, 766));
        parameters.add(new Parameters(i++, 77));
        parameters.add(new Parameters(i++, 27));
        parameters.add(new Parameters(i++, 746));
        parameters.add(new Parameters(i++, 447));
        parameters.add(new Parameters(i++, 776));
        parameters.add(new Parameters(i, 7953));
        return parameters;
    }

    @Test
    public void test() {
        Random random = new Random();
        BitShavingCompressor bitShavingCompressor = new BitShavingCompressor(parameters.bits);
        double[] data = new double[parameters.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt() + random.nextDouble();
        }

        double[] dataForCheckAccuracy = Arrays.copyOf(data, data.length);
        BitShavingDouble bitShavingDouble = new BitShavingDouble(parameters.bits);
        dataForCheckAccuracy = bitShavingDouble.encode(dataForCheckAccuracy);

        byte[] compressedData = bitShavingCompressor.compress(data);

        double[] decompressedData = bitShavingCompressor.decompress(compressedData);
        Assertions.assertEquals(data.length, decompressedData.length);
        Assertions.assertArrayEquals(dataForCheckAccuracy, decompressedData);
    }

    private static class Parameters {
        private final int bits;
        private final int length;

        public Parameters(int bits, int length) {
            this.bits = bits;
            this.length = length;
        }

        @Override
        public String toString() {
            return "bits - " + bits + "; length - " + length;
        }
    }
}
