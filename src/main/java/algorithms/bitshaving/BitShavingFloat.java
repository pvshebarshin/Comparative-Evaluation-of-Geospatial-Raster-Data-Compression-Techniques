package algorithms.bitshaving;

public class BitShavingFloat {

    private static final int ALL_ONES_FOR_FLOAT = 0xffffffff;
    private static final int FRACTIONAL_32_BIT = 23;

    private final int bitMask;

    /**
     * Getting bit Mask for shaving
     * @param bitN number of bits
     * @return bit mask
     */
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
        return Float.intBitsToFloat(shave);
    }

    /**
     * Encoder of algorithm BitShaving
     *
     * @param data float data
     * @return shaved float data
     */
    public float[] encode(float[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = bitShave(data[i], bitMask);
        }
        return data;
    }
}
