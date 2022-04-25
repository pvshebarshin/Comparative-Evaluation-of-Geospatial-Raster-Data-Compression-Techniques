package compressors.digitrouting;

import algorithms.digitrounding.DigitRouting;
import compressors.DigitRoutingCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.zip.DataFormatException;

@RunWith(value = Parameterized.class)
public class DigitRoutingCompressorTest {

    private final Parameters parameters;

    public DigitRoutingCompressorTest(Parameters parameters) {
        this.parameters = parameters;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameters> data() {
        ArrayList<Parameters> parameters = new ArrayList<>();
        parameters.add(new Parameters(1, 10000));
        parameters.add(new Parameters(2, 64));
        parameters.add(new Parameters(3, 87));
        parameters.add(new Parameters(4, 4235));
        parameters.add(new Parameters(5, 423));
        parameters.add(new Parameters(6, 999));
        parameters.add(new Parameters(7, 222));
        parameters.add(new Parameters(8, 111));
        parameters.add(new Parameters(9, 1234567));
        parameters.add(new Parameters(10, 98));
        parameters.add(new Parameters(11, 66));
        parameters.add(new Parameters(12, 8));
        parameters.add(new Parameters(13, 2));
        parameters.add(new Parameters(14, 46));
        parameters.add(new Parameters(15, 44));
        return parameters;
    }

    @Test
    public void digitRoutingCompressorTest() throws DataFormatException, IOException {
        Random random = new Random();
        DigitRoutingCompressor digitRoutingCompressor = new DigitRoutingCompressor(parameters.nsd);
        double[] data = new double[parameters.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt() + random.nextDouble();
        }

        double[] dataForCheckAccuracy = Arrays.copyOf(data, data.length);
        DigitRouting digitRouting = new DigitRouting();
        dataForCheckAccuracy = digitRouting.encode(dataForCheckAccuracy, parameters.nsd);

        byte[] compressedData = digitRoutingCompressor.compress(data);

        double[] decompressedData = digitRoutingCompressor.decompress(compressedData);
        Assertions.assertEquals(data.length, decompressedData.length);
        Assertions.assertArrayEquals(dataForCheckAccuracy, decompressedData);
    }

    private static class Parameters {
        private final int nsd;
        private final int length;

        public Parameters(int nsd, int length) {
            this.nsd = nsd;
            this.length = length;
        }

        @Override
        public String toString() {
            return "NSD - " + nsd + "; length - " + length;
        }
    }
}
