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
        Deflater deflater = new Deflater();
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();

        byte[] output = outputStream.toByteArray();
        ratio = ((double) data.length / (double) output.length);

        LOG.debug(() -> "Original: " + data.length / 1024f + " Kb");
        LOG.debug(() -> "Compressed: " + output.length / 1024f + " Kb");

        return outputStream.toByteArray();
    }

    public static byte[] decompressByteArray(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();

        byte[] output = outputStream.toByteArray();
        ratio = ((double) output.length) / ((double) data.length);

        LOG.debug(() -> "Compressed: " + output.length / 1024f + " Kb");
        LOG.debug(() -> "Original: " + data.length / 1024f + " Kb");

        return outputStream.toByteArray();
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
}
