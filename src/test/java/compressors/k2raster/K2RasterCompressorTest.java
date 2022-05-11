package compressors.k2raster;

import compressors.K2RasterCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.DataFormatException;

@RunWith(value = Parameterized.class)
public class K2RasterCompressorTest {

    private final int[] parameters;

    public K2RasterCompressorTest(int[] parameters) {
        this.parameters = parameters;
    }

    private static int getRandomNumber() {
        return (int) ((Math.random() * 128) + 0);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<int[]> data() {
        ArrayList<int[]> parameters = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            int[] mass = new int[(int) Math.pow(2, i)];
            for (int j = 0; j < mass.length; j++) {
                mass[j] = getRandomNumber();
            }
            parameters.add(mass);
        }
        return parameters;
    }

    @Test
    public void k2RasterCompressorTest() throws IOException, DataFormatException {
        K2RasterCompressor k2RasterCompressor = new K2RasterCompressor();
        byte[] compressedData = k2RasterCompressor.compress(parameters);
        int[] decompressedData = k2RasterCompressor.decompress(compressedData);
        Assertions.assertArrayEquals(parameters, decompressedData);
    }
}
