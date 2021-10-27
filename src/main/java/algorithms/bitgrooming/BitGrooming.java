package algorithms.bitgrooming;

public class BitGrooming {
    private static final double M_LN10 = 2.30258509299404568401799145468436421;
    private static final double M_LN2 = 0.693147180559945309417232121458176568;
    private static final int BIT_XPL_NBR_SGN_DBL = 53;
    private static final double BIT_PER_DCM_DGT_PRC = M_LN10 / M_LN2;

    public double[] encode(double[] data, NSD nsd) {
        double prc_bnr_xct = nsd.getValue() * BIT_PER_DCM_DGT_PRC;
        short prc_bnr_ceil = (short) Math.ceil(prc_bnr_xct);
        short prc_bnr_xpl_rqr = (short) (prc_bnr_ceil + 2);
        int bit_xpl_nbr_sgn = BIT_XPL_NBR_SGN_DBL;
        long bit_xpl_nbr_zro = bit_xpl_nbr_sgn - prc_bnr_xpl_rqr;
        assert (bit_xpl_nbr_zro <= bit_xpl_nbr_sgn - 2);
        /* Create mask */
        long zeroMask = 0; /* Zero all bits */
        zeroMask = ~zeroMask; /* Turn all bits to ones */
        /* Bit Shave mask for AND: Left shift zeros into bits to be rounded, leave ones in untouched bits */
        zeroMask <<= bit_xpl_nbr_zro;
        /* Bit Set   mask for OR:  Put ones into bits to be set, zeros in untouched bits */
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
