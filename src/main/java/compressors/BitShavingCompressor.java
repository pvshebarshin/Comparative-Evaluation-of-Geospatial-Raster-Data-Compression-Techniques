package compressors;

import algorithms.bitshaving.BitShavingDouble;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class BitShavingCompressor implements Compressor {

    private final int bits;

    public BitShavingCompressor(int bits) {
        this.bits = bits;
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        BitShavingDouble bitShavingDouble = new BitShavingDouble(bits);
        data = bitShavingDouble.encode(data);
        return DeflaterUtils.convertLowerAccuracyDoublesToBits(data);
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        return DeflaterUtils.decompressByteToDoubles(data);
    }

    @Override
    public String toString() {
        return "BitShaving";
    }

    @Override
    public String getParameters() {
        return Integer.toString(bits);
    }
}
