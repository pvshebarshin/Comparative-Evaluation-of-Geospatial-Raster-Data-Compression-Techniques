package compressors;

import algorithms.fpc.FPC;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class FpcCompressor implements Compressor {
    private static int massLength;

    public void setMassLength(int value) {
        massLength = value;
    }

    @Override
    public byte[] compress(double[] data) {
        FPC fpc = new FPC();
        byte[] buffer = new byte[data.length * 8 + data.length + 1];
        massLength = data.length;
        fpc.compress(buffer, data);
        try {
            return DeflaterUtils.compressByteArray(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[1];
    }

    @Override
    public double[] decompress(byte[] data) {
        FPC fpc = new FPC();
        byte[] decompressedBytes = null;
        try {
            decompressedBytes = DeflaterUtils.decompressByteArray(data);
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
        double[] result = new double[massLength];
        fpc.decompress(decompressedBytes, result);
        return result;
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
