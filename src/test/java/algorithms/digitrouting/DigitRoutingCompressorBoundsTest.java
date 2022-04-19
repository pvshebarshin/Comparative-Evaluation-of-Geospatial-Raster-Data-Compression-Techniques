package algorithms.digitrouting;

import compressors.DigitRoutingCompressor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class DigitRoutingCompressorBoundsTest {

    private static final double PI = 3.14159265358979323846264338327950288;

    @Test
    public void lessBoundDigitRoutingCompressorTest() throws IOException, DataFormatException {
        DigitRoutingCompressor digitRoutingCompressor = new DigitRoutingCompressor(-999);
        double[] result = digitRoutingCompressor.decompress(digitRoutingCompressor.compress(new double[]{PI}));
        Assertions.assertEquals(3.0, result[0], "Incorrect working of conditions");
    }

    @Test
    public void moreBoundDigitRoutingCompressorTest() throws IOException, DataFormatException {
        DigitRoutingCompressor digitRoutingCompressor = new DigitRoutingCompressor(999);
        double[] result = digitRoutingCompressor.decompress(digitRoutingCompressor.compress(new double[]{PI}));
        Assertions.assertEquals(3.141592653589793, result[0], "Incorrect working of conditions");
    }
}
