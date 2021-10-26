package algorithms.bitshaving;

public class BitShavingFloat {
    private static final int ALL_ONES_FOR_FLOAT = 0xffffffff;
    private static final int FRACTIONAL_32_BIT = 23;
    private final int bitMask;

    private static int getBitMaskForFloat(int bitN) {
        if (bitN >= FRACTIONAL_32_BIT) {
            return ALL_ONES_FOR_FLOAT;
        }
        // rightmost bits get set to zero
        return ALL_ONES_FOR_FLOAT << FRACTIONAL_32_BIT - bitN;
    }

    public BitShavingFloat(int bits) {
        bitMask = getBitMaskForFloat(bits);
    }

    /**
     * Shave n bits off the float
     *
     * @param value   original floating point
     * @param bitMask bitMask from getBitMask()
     * @return modified float
     */
    public static float bitShave(float value, int bitMask) {
        if (Float.isNaN(value)) {
            return value;
        }

        int bits = Float.floatToRawIntBits(value);
        int shave = bits & bitMask;
        System.out.printf("0x%s -> 0x%s : ", Integer.toBinaryString(bits),
                Integer.toBinaryString(shave));
        float result = Float.intBitsToFloat(shave);
        System.out.printf("%f -> %f %n", value, result);
        return result;
    }

    public float[] encode(float[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = bitShave(data[i], bitMask);
        }
        return data;
    }

    public static void checkShavedPrecisionForFloat(float[] data, int bits) {
        double expectedPrecision = Math.pow(2.0, -bits);
        float max_pdiff = -Float.MAX_VALUE;
        int bitMask = getBitMaskForFloat(bits);
        for (float value : data) {
            float shaved = bitShave(value, bitMask);
            float diff = Math.abs(value - shaved);
            float pdiff = (value != 0.0) ? diff / value : 0.0f;
            assert pdiff < expectedPrecision;
            max_pdiff = Math.max(max_pdiff, pdiff);
        }

        if (max_pdiff != 0.0) {   // usually max precision lies between 1/2^N and 1/2^(N+1)
            if (max_pdiff < expectedPrecision / 2 || max_pdiff > expectedPrecision) {
                System.out.printf("nbits=%d max_pdiff=%g expect=%g%n", bits, max_pdiff, expectedPrecision);
            }
        }
    }
}
