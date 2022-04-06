package compressors;

import algorithms.digitrounding.DigitRouting;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class DigitRoutingCompressor implements Compressor {

    private final int significantBits;

    public DigitRoutingCompressor(int significantBits) {
        this.significantBits = significantBits;
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        DigitRouting digitRouting = new DigitRouting();
        data = digitRouting.encode(data, significantBits);
        return DeflaterUtils.convertLowerAccuracyDoublesToBits(data);
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        return DeflaterUtils.decompressByteToDoubles(data);
    }

    @Override
    public String getParameters() {
        return String.valueOf(significantBits);
    }

    @Override
    public String toString() {
        return "DigitRouting";
    }
}
