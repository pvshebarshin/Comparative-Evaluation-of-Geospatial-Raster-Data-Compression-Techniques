package algorithms.fpc;

import compressors.FpcCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.zip.DataFormatException;

@RunWith(value = Parameterized.class)
public class FpcCompressorTest {
    private final double[] parameters;

    public FpcCompressorTest(double[] parameters) {
        this.parameters = parameters;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<double[]> data() {
        ArrayList<double[]> parameters = new ArrayList<>();
        Random random = new Random();
        parameters.add(new double[]{3.14159265358979323846264338327950288});
        for (int i = 1; i < 10; i++) {
            double[] mass = new double[i * 10];
            for (int j = 0; j < mass.length; j++) {
                mass[j] = random.nextInt() + random.nextDouble();
            }
            parameters.add(mass);
        }
        return parameters;
    }

    @Test
    public void fpcCompressorTest() throws IOException, DataFormatException {
        FpcCompressor fpcCompressor = new FpcCompressor();
        byte[] compressedData = fpcCompressor.compress(parameters);
        double[] decompressedData = fpcCompressor.decompress(compressedData);
        Assertions.assertArrayEquals(parameters, decompressedData);
    }
}
