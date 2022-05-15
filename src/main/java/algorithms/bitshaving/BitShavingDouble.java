package algorithms.bitshaving;

public class BitShavingDouble {

    private static final long ALL_ONES_FOR_DOUBLE = 0xffffffffffffffffL;
    private static final int FRACTIONAL_64_BIT = 52;

    private final long bitMask;

    /**
     * Getting bit Mask for shaving
     * @param bitN number of bits
     * @return bit mask
     */
    private static long getBitMask(int bitN) {
        if (bitN >= FRACTIONAL_64_BIT) {
            return ALL_ONES_FOR_DOUBLE;
        }
        // rightmost bits get set to zero
        return ALL_ONES_FOR_DOUBLE << FRACTIONAL_64_BIT - bitN;
    }

    public BitShavingDouble(int bits) {
        bitMask = getBitMask(bits);
    }

    /**
     * Shave n bits off the double
     *
     * @param value   original floating point
     * @param bitMask bitMask from getBitMask()
     * @return modified double
     */
    private double bitShaveForDouble(double value, long bitMask) {
        if (Double.isNaN(value)) {
            return value;
        }
        long bits = Double.doubleToLongBits(value);
        long shave = bits & bitMask;
        return Double.longBitsToDouble(shave);
    }

    /**
     * Encoder of algorithm BitShaving
     *
     * @param data double data
     * @return shaved double data
     */
    public double[] encode(double[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = bitShaveForDouble(data[i], bitMask);
        }
        return data;
    }
}
