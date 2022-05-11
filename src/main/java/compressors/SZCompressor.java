package compressors;

import algorithms.sz.SZ;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class SZCompressor implements DoubleCompressor {

    private final SZ sz;

    public SZCompressor(double error) {
        sz = new SZ(error);
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        return DeflaterUtils.compressByteArray(sz.encode(data));
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        return sz.decode(DeflaterUtils.decompressByteArray(data));
    }

    @Override
    public String getParameters() {
        return String.valueOf(sz.getError());
    }

    @Override
    public String toString() {
        return "SZ";
    }
}
