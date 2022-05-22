package compressors.sz;

import compressors.SZCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.DataFormatException;

@RunWith(value = Parameterized.class)
public class SZCompressorTest {

    private final double[] parameters;

    public SZCompressorTest(double[] parameters) {
        this.parameters = parameters;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<double[]> data() {
        ArrayList<double[]> parameters = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            double[] mass = new double[i * 10];
            for (int j = 0; j < mass.length; j++) {
                mass[j] = ThreadLocalRandom.current().nextDouble(9999, 100000);
            }
            parameters.add(mass);
        }
        return parameters;
    }

    @Test
    public void szCompressorTest() throws IOException, DataFormatException {
        SZCompressor szCompressor = new SZCompressor(0.1);
        byte[] compressedData = szCompressor.compress(parameters);
        double[] decompressedData = szCompressor.decompress(compressedData);
        Assertions.assertArrayEquals(parameters, decompressedData, 0.6);
    }
}
