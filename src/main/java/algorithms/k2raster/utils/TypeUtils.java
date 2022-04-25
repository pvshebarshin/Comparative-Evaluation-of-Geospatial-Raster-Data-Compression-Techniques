package algorithms.k2raster.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;

public final class TypeUtils {

    private static long offset = 0;

    private TypeUtils() {
    }

    public static byte[] intArrayToByteArray(int[] array) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(array.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(array);
        return byteBuffer.array();
    }

    public static int[] byteArrayToIntArray(byte[] byteArray) {
        IntBuffer intBuf = ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        for (int i = 0; i < array.length; i++) {
            array[i] = intBuf.get(i);
        }
        return array;
    }

    public static int byteArrayToInt(byte[] bytes) {
        return bytes[3] & 0xFF |
                (bytes[2] & 0xFF) << 8 |
                (bytes[1] & 0xFF) << 16 |
                (bytes[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    public static int[] doubleToPositiveLong(final double[] data) {
        int[] result = new int[data.length];

        double temp;
        for (int i = 0; i < result.length; i++) {
            temp = Math.abs(data[i]);
            if (temp < 0.1) {
                result[i] = 0;
            } else if (temp < 0.2) {
                result[i] = 1;
            } else if (temp < 0.3) {
                result[i] = 2;
            } else if (temp < 0.4) {
                result[i] = 3;
            } else if (temp < 0.5) {
                result[i] = 4;
            } else if (temp < 0.6) {
                result[i] = 5;
            } else if (temp < 0.7) {
                result[i] = 6;
            } else if (temp < 0.8) {
                result[i] = 7;
            } else if (temp < 0.9) {
                result[i] = 8;
            } else {
                result[i] = 9;
            }
        }

        return result;
    }

    public static double[] positiveLongToDouble(int[] data) {
        double[] result = new double[data.length];

        int temp;
        for (int i = 0; i < data.length; i++) {
            temp = data[i];
            if (temp == 0) {
                result[i] = 0.1;
            } else if (temp == 1) {
                result[i] = 0.2;
            } else if (temp == 2) {
                result[i] = 0.3;
            } else if (temp == 3) {
                result[i] = 0.4;
            } else if (temp == 4) {
                result[i] = 0.5;
            } else if (temp == 5) {
                result[i] = 0.6;
            } else if (temp == 6) {
                result[i] = 0.7;
            } else if (temp == 7) {
                result[i] = 0.8;
            } else if (temp == 8) {
                result[i] = 0.9;
            } else {
                result[i] = 1;
            }
        }

        return result;
    }
}
