package compressors.bitgrooming;

import algorithms.bitgrooming.BitGrooming;
import algorithms.bitgrooming.NSD;
import compressors.BitGroomingCompressor;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.zip.DataFormatException;

@RunWith(value = Parameterized.class)
public class BitGroomingCompressorTest {

    private final Parameters parameters;

    public BitGroomingCompressorTest(Parameters parameters) {
        this.parameters = parameters;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameters> data() {
        ArrayList<Parameters> parameters = new ArrayList<>();
        parameters.add(new Parameters(NSD.NSD1, 10000));
        parameters.add(new Parameters(NSD.NSD2, 64));
        parameters.add(new Parameters(NSD.NSD3, 87));
        parameters.add(new Parameters(NSD.NSD4, 4235));
        parameters.add(new Parameters(NSD.NSD5, 423));
        parameters.add(new Parameters(NSD.NSD6, 999));
        parameters.add(new Parameters(NSD.NSD7, 222));
        parameters.add(new Parameters(NSD.NSD8, 111));
        parameters.add(new Parameters(NSD.NSD9, 1234567));
        parameters.add(new Parameters(NSD.NSD10, 98));
        parameters.add(new Parameters(NSD.NSD11, 66));
        parameters.add(new Parameters(NSD.NSD12, 8));
        parameters.add(new Parameters(NSD.NSD13, 2));
        parameters.add(new Parameters(NSD.NSD14, 46));
        parameters.add(new Parameters(NSD.NSD15, 44));
        return parameters;
    }

    @Test
    public void bitGroomingCompressorTest() throws DataFormatException, IOException {
        Random random = new Random();
        BitGroomingCompressor bitGroomingCompressor = new BitGroomingCompressor(parameters.BGNsd);
        double[] data = new double[parameters.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt() + random.nextDouble();
        }

        double[] dataForCheckAccuracy = Arrays.copyOf(data, data.length);
        BitGrooming bitGrooming = new BitGrooming();
        dataForCheckAccuracy = bitGrooming.encode(dataForCheckAccuracy, parameters.BGNsd);

        byte[] compressedData = bitGroomingCompressor.compress(data);

        double[] decompressedData = bitGroomingCompressor.decompress(compressedData);
        Assertions.assertEquals(data.length, decompressedData.length);
        Assertions.assertArrayEquals(dataForCheckAccuracy, decompressedData);
    }

    private static class Parameters {
        private final NSD BGNsd;
        private final int length;

        public Parameters(NSD BGNsd, int length) {
            this.BGNsd = BGNsd;
            this.length = length;
        }

        @Override
        public String toString() {
            return "NSD - " + BGNsd + "; length - " + length;
        }
    }
}
