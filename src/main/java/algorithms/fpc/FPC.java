package algorithms.fpc;

import algorithms.fpc.predictors.DfcmPredictor;
import algorithms.fpc.predictors.FcmPredictor;

public class FPC {

    private static final int LOG_OF_TABLE_SIZE = 16;

    private final FcmPredictor predictor1 = new FcmPredictor(LOG_OF_TABLE_SIZE);
    private final DfcmPredictor predictor2 = new DfcmPredictor(LOG_OF_TABLE_SIZE);

    private int indexEncode = 0;
    private int indexDecode = 0;

    public byte[] compress(byte[] buffer, double[] data) {
        for (int i = 0; i < data.length; i += 2) {
            if (i == data.length - 1) {
                encodeAndPad(buffer, data[i]);
            } else {
                encode(buffer, data[i], data[i + 1]);
            }
        }
        return buffer;
    }

    public double[] decompress(byte[] buffer, double[] data) {
        for (int i = 0; i < data.length; i += 2) {
            decode(buffer, data, i);
        }
        return data;
    }

    private void decode(byte[] buffer, double[] data, int i) {
        byte headByte = buffer[indexDecode];
        indexDecode++;

        long prediction;

        if ((headByte & 0x80) != 0) {
            prediction = predictor2.getPrediction();
        } else {
            prediction = predictor1.getPrediction();
        }

        int numZeroBytes = (headByte & 0x70) >> 4;
        if (numZeroBytes > 3) {
            numZeroBytes++;
        }
        byte[] dst = new byte[8 - numZeroBytes];
        for (int j = 0; j < dst.length; j++) {
            dst[j] = buffer[indexDecode];
            indexDecode++;
        }
        long diff = toLong(dst);
        long actual = prediction ^ diff;

        predictor1.update(actual);
        predictor2.update(actual);

        data[i] = Double.longBitsToDouble(actual);

        if ((headByte & 0x08) != 0) {
            prediction = predictor2.getPrediction();
        } else {
            prediction = predictor1.getPrediction();
        }

        numZeroBytes = (headByte & 0x07);
        if (numZeroBytes > 3)
            numZeroBytes++;

        dst = new byte[8 - numZeroBytes];
        for (int j = 0; j < dst.length; j++) {
            dst[j] = buffer[indexDecode];
            indexDecode++;
        }
        diff = toLong(dst);

        if (numZeroBytes == 7 && diff == 0) {
            return;
        }

        actual = prediction ^ diff;

        predictor1.update(actual);
        predictor2.update(actual);

        data[i + 1] = Double.longBitsToDouble(actual);
    }

    public long toLong(byte[] data) {
        long result = 0L;
        for (int i = data.length; i > 0; i--) {
            result <<= 8;
            result |= data[i - 1] & 0xff;
        }
        return result;
    }

    private void encodeAndPad(byte[] buffer, double d) {
        long dBits = Double.doubleToLongBits(d);
        long diff1d = predictor1.getPrediction() ^ dBits;
        long diff2d = predictor2.getPrediction() ^ dBits;

        boolean predictor1BetterForD = updatePredictors(dBits, diff1d, diff2d);
        byte code = 0;
        if (predictor1BetterForD) {
            int zeroBytes = encodeZeroBytes(diff1d);
            code |= zeroBytes << 4;
        } else {
            code |= 0x80;
            int zeroBytes = encodeZeroBytes(diff2d);
            code |= zeroBytes << 4;
        }

        code |= 0x06;

        buffer[indexEncode] = code;
        indexEncode++;
        checkPrediction(buffer, diff1d, diff2d, predictor1BetterForD);

        buffer[indexEncode] = (byte) 0;
        indexEncode++;
    }

    private int encodeZeroBytes(long diff1d) {
        int leadingZeroBytes = Long.numberOfLeadingZeros(diff1d) / 8;
        if (leadingZeroBytes >= 4)
            leadingZeroBytes--;
        return leadingZeroBytes;
    }

    private void encode(byte[] buffer, double d, double e) {

        long dBits = Double.doubleToLongBits(d);
        long diff1d = predictor1.getPrediction() ^ dBits;
        long diff2d = predictor2.getPrediction() ^ dBits;

        boolean predictor1BetterForD = updatePredictors(dBits, diff1d, diff2d);

        long eBits = Double.doubleToLongBits(e);
        long diff1e = predictor1.getPrediction() ^ eBits;
        long diff2e = predictor2.getPrediction() ^ eBits;

        boolean predictor1BetterForE = updatePredictors(eBits, diff1e, diff2e);

        byte code = 0;
        if (predictor1BetterForD) {
            int zeroBytes = encodeZeroBytes(diff1d);
            code |= zeroBytes << 4;
        } else {
            code |= 0x80;
            int zeroBytes = encodeZeroBytes(diff2d);
            code |= zeroBytes << 4;
        }

        if (predictor1BetterForE) {
            int zeroBytes = encodeZeroBytes(diff1e);
            code |= zeroBytes;
        } else {
            code |= 0x08;
            int zeroBytes = encodeZeroBytes(diff2e);
            code |= zeroBytes;
        }

        buffer[indexEncode] = code;
        indexEncode++;
        checkPrediction(buffer, diff1d, diff2d, predictor1BetterForD);
        checkPrediction(buffer, diff1e, diff2e, predictor1BetterForE);
    }

    private boolean updatePredictors(long bits, long diff1, long diff2) {
        predictor1.update(bits);
        predictor2.update(bits);
        return Long.numberOfLeadingZeros(diff1) >= Long.numberOfLeadingZeros(diff2);
    }

    private void checkPrediction(byte[] buffer, long diff1, long diff2, boolean isPredictorBetter) {
        if (isPredictorBetter) {
            byte[] temp = toByteArray(diff1);
            for (byte b : temp) {
                buffer[indexEncode] = b;
                indexEncode++;
            }
        } else {
            byte[] temp = toByteArray(diff2);
            for (byte b : temp) {
                buffer[indexEncode] = b;
                indexEncode++;
            }
        }
    }

    public byte[] toByteArray(long data) {
        int encodedZeroBytes = encodeZeroBytes(data);
        if (encodedZeroBytes > 3)
            encodedZeroBytes++;
        byte[] array = new byte[8 - encodedZeroBytes];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) (data & 0xff);
            data >>= 8;
        }
        return array;
    }
}
