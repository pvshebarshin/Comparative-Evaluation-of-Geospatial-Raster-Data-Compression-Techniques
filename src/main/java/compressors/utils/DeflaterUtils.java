package compressors.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class DeflaterUtils {

    private static final Logger LOG = LogManager.getLogger(DeflaterUtils.class);

    private static double ratio = -1;

    private DeflaterUtils() {
    }

    public static byte[] compressByteArray(byte[] data) throws IOException {
        ByteArrayOutputStream outputStream = getArrayOutputStreamForCompression(data);

        byte[] output = outputStream.toByteArray();
        LOG.debug(() -> "Original: " + data.length / 1024f + " Kb");
        LOG.debug(() -> "Compressed: " + output.length / 1024f + " Kb");

        ratio = ((double) data.length / (double) output.length);

        return output;
    }

    public static byte[] decompressByteArray(byte[] data) throws IOException, DataFormatException {
        ByteArrayOutputStream outputStream = getByteArrayOutputStreamForDecompression(data);

        byte[] output = outputStream.toByteArray();
        LOG.debug(() -> "Compressed: " + data.length / 1024f + " Kb");
        LOG.debug(() -> "Original: " + output.length / 1024f + " Kb");

        ratio = ((double) output.length) / ((double) data.length);

        return output;
    }

    public static byte[] convertLowerAccuracyDoublesToBits(double[] data) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(data.length * 8);
        for (double value : data) {
            bb.putDouble(value);
        }
        return DeflaterUtils.compressByteArray(bb.array());
    }

    public static double[] decompressByteToDoubles(byte[] data) throws DataFormatException, IOException {
        byte[] decompressedData = DeflaterUtils.decompressByteArray(data);
        int times = Double.SIZE / Byte.SIZE;
        double[] result = new double[decompressedData.length / times];
        for (int i = 0; i < result.length; i++) {
            result[i] = ByteBuffer.wrap(decompressedData, i * times, times).getDouble();
        }
        return result;
    }

    public static double getRatio() {
        return ratio;
    }

    public static byte[] getRatio(byte[] data, int size) {
        LOG.debug(() -> "Original: " + size / 1024f + " Kb");
        LOG.debug(() -> "Compressed: " + data.length / 1024f + " Kb");
        ratio = ((double) size) / ((double) data.length);
        return data;
    }

    public static byte[] compressByteArrayForSZ(byte[] data, int size) throws IOException {
        ByteArrayOutputStream outputStream = getArrayOutputStreamForCompression(data);

        byte[] output = outputStream.toByteArray();
        LOG.debug(() -> "Original: " + size / 1024f + " Kb");
        LOG.debug(() -> "Compressed: " + output.length / 1024f + " Kb");

        ratio = ((double) size / (double) output.length);

        return output;
    }

    public static byte[] decompressByteArrayForSZ(byte[] data, int size) throws IOException, DataFormatException {
        ByteArrayOutputStream outputStream = getByteArrayOutputStreamForDecompression(data);

        LOG.debug(() -> "Compressed: " + data.length / 1024f + " Kb");
        LOG.debug(() -> "Original: " + size / 1024f + " Kb");

        ratio = ((double) size) / ((double) data.length);

        return outputStream.toByteArray();
    }

    private static ByteArrayOutputStream getByteArrayOutputStreamForDecompression(byte[] data)
            throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream;
    }

    private static ByteArrayOutputStream getArrayOutputStreamForCompression(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream;
    }
}
