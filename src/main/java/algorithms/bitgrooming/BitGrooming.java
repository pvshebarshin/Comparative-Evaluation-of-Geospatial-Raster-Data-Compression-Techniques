package algorithms.bitgrooming;

public class BitGrooming {

    private static final double M_LN10 = 2.30258509299404568401799145468436421;
    private static final double M_LN2 = 0.693147180559945309417232121458176568;
    private static final long BIT_XPL_NBR_SGN_DBL = 53;
    private static final double BIT_PER_DCM_DGT_PRC = M_LN10 / M_LN2;

    public double[] encode(double[] data, NSD nsd) {
        short prcBnrXplRqr = (short) (Math.ceil(nsd.getValue() * BIT_PER_DCM_DGT_PRC) + 2);
        long bitXplNbrZro = BIT_XPL_NBR_SGN_DBL - prcBnrXplRqr;

        /* Create mask */
        long zeroMask = 0;
        zeroMask = ~zeroMask;
        zeroMask <<= bitXplNbrZro;
        long oneMask = ~zeroMask;

        long dataLongBits;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0.0) {
                dataLongBits = Double.doubleToLongBits(data[i]);
                if (i % 2 == 0) {
                    dataLongBits &= zeroMask;
                } else {
                    dataLongBits |= oneMask;
                }
                data[i] = Double.longBitsToDouble(dataLongBits);
            }
        }
        return data;
    }
}
