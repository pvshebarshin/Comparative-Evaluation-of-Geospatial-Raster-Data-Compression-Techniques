package compressors;

import algorithms.sz.SZ;
import compressors.interfaces.DoubleCompressor;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class SZCompressor implements DoubleCompressor {

    private final SZ sz;

    private int size;

    public SZCompressor(double error) {
        sz = new SZ(error);
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        size = data.length * 8;
        return DeflaterUtils.compressByteArrayForSZ(sz.encode(data), size);
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        return sz.decode(DeflaterUtils.decompressByteArrayForSZ(data, size));
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
