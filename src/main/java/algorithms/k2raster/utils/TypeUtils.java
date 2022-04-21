package algorithms.k2raster.utils;

import java.util.Arrays;

public final class TypeUtils {

    private static long offset = 0;

    private TypeUtils() {
    }

    public static long[] doubleToPositiveLong(final double[] data) {
        long[] result = new long[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = Double.doubleToLongBits(data[i]);
        }
        offset = Arrays.stream(result).min().orElse(0L);
        for (int i = 0; i < data.length; i++) {
            result[i] -= offset;
        }
        return result;
    }

    public static double[] positiveLongToDouble(long[] data) {
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            data[i] += offset;
        }
        for (int i = 0; i < data.length; i++) {
            result[i] = Double.longBitsToDouble(data[i]);
        }
        return result;
    }
}
