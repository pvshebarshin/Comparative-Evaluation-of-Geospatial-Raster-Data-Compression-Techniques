package algorithms.digitrounding;

public class DigitRouting {

    private static final double LOG10_2 = 0.301029996;
    private static final double LOG2_10 = 3.321928095;

    private static final double[][] TABLE = {
            {0.6, -LOG10_2},
            {0.7, -0.221848749},
            {0.8, -0.154901959},
            {0.9, -0.096910013},
            {1.0, -0.045757490},
    };

    public double[] encode(double[] data, int nsd) {
        for (int i = 0; i < data.length; i++) {
            MantissaExponent mantissaExponent = frexp(data[i]);

            int index = 0;
            while (TABLE[index][0] < mantissaExponent.getMantissa()) {
                index++;
            }
            double log10m = TABLE[index][1];

            // convert the binary exponent to a number of digits: d = floor(e*log10(2) + log10(m)) + 1
            int d = (int) Math.floor(mantissaExponent.getExponent() * LOG10_2 + log10m) + 1;

            // compute the power of the quantization step: q = 2^p
            int p = (int) Math.floor(LOG2_10 * (d - nsd));
            // compute quantization step: q = 2^p
            double q = 2 * Math.pow(2, p);

            // apply the quantization step depending on the bias
            data[i] = Math.signum(data[i]) * (Math.floor(Math.abs(data[i]) / q) + 0.5) * q;
        }
        return data;
    }

    private static class MantissaExponent {
        private final int exponent;
        private final double mantissa;

        public MantissaExponent(int exponent, double mantissa) {
            this.exponent = exponent;
            this.mantissa = mantissa;
        }

        public int getExponent() {
            return exponent;
        }

        public double getMantissa() {
            return mantissa;
        }
    }

    public MantissaExponent frexp(double value) {
        long bits = Double.doubleToLongBits(value);

        if (Double.isNaN(value) || value + value == value || Double.isInfinite(value)) {
            return new MantissaExponent(0, value);
        } else {
            boolean neg = (bits < 0);
            int exponent = (int) ((bits >> 52) & 0x7ffL);
            long mantissa = bits & 0xfffffffffffffL;

            if (exponent == 0) {
                exponent++;
            } else {
                mantissa |= (1L << 52);
            }

            // bias the exponent - actually biased by 1023.
            // we are treating the mantissa as m.0 instead of 0.m
            //  so subtract another 52.
            exponent -= 1075;
            double realMantissa = mantissa;

            // normalize
            while (realMantissa > 1.0) {
                mantissa >>= 1;
                realMantissa /= 2.;
                exponent++;
            }

            if (neg) {
                realMantissa *= -1;
            }

            return new MantissaExponent(exponent, realMantissa);
        }
    }
}
