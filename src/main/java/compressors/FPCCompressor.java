package compressors;

import algorithms.fpc.FPC;
import compressors.interfaces.DoubleCompressor;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class FPCCompressor implements DoubleCompressor {
    private static int massLength;

    private static void setMassLength(int value) {
        massLength = value;
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        FPC fpc = new FPC();
        byte[] buffer = new byte[data.length * 8 + data.length + 1];
        setMassLength(data.length);
        return DeflaterUtils.compressByteArray(fpc.encode(buffer, data));
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        FPC fpc = new FPC();
        double[] result = new double[massLength];
        return fpc.decode(DeflaterUtils.decompressByteArray(data), result);
    }

    @Override
    public String getParameters() {
        return "-";
    }

    @Override
    public String toString() {
        return "FPC";
    }
}
