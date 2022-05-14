package algorithms.sz;

import java.util.Arrays;
import java.util.zip.DataFormatException;

import static algorithms.utils.TypeUtils.byteArrayToDouble;
import static algorithms.utils.TypeUtils.byteArrayToInt;
import static algorithms.utils.TypeUtils.doubleToByteArray;
import static algorithms.utils.TypeUtils.intToByteArray;

public class SZ {

    private final double error;

    public SZ(double error) {
        this.error = error;
    }

    public byte[] encode(double[] data) throws SZByteCodeException {
        if (data.length < 4) {
            throw new ArithmeticException("Array size is too small. It must be more then 3");
        }
        int indexEncode = 0;

        double median = (Arrays.stream(data).max().orElseThrow(() -> new ArithmeticException("Empty data"))
                + Arrays.stream(data).min().orElseThrow(() -> new ArithmeticException("Empty data"))) / 2;
        byte[] result = new byte[8 * data.length + data.length + 8 + 4 + 1];

        byte[] byteArray = doubleToByteArray(median);
        for (byte _byte : byteArray) {
            result[indexEncode++] = _byte;
        }

        byteArray = intToByteArray(data.length);
        for (byte _byte : byteArray) {
            result[indexEncode++] = _byte;
        }

        for (int i = 0; i < 3; i++) {
            byteArray = doubleToByteArray(data[i] - median);
            for (byte _byte : byteArray) {
                result[indexEncode++] = _byte;
            }
        }

        double pnf;
        double lcf;
        double qcf;
        for (int i = 3; i < data.length; i++) {
            pnf = data[i - 1];
            lcf = 2 * data[i - 1] - data[i - 2];
            qcf = 3 * data[i - 1] - 3 * data[i - 2] + data[i - 3];
            char bestFitSolution = getBestFitSolution(pnf, lcf, qcf, data[i]);
            switch (bestFitSolution) {
                case 'N':
                    result[indexEncode++] = (byte) 1;
                    break;
                case 'L':
                    result[indexEncode++] = (byte) 2;
                    break;
                case 'Q':
                    result[indexEncode++] = (byte) 3;
                    break;
                case '-':
                    result[indexEncode++] = (byte) 0;
                    byteArray = doubleToByteArray(data[i] - median);
                    for (byte _byte : byteArray) {
                        result[indexEncode++] = _byte;
                    }
                    break;
                default:
                    throw new SZByteCodeException("Incorrect double data");
            }
        }

        return result;
    }

    public double[] decode(byte[] data) throws SZByteCodeException {
        int indexDecode = 0;
        byte[] byteArray = new byte[8];
        for (; indexDecode < 8; indexDecode++) {
            byteArray[indexDecode] = data[indexDecode];
        }
        double median = byteArrayToDouble(byteArray);

        byteArray = new byte[4];
        for (int i = 0; i < 4; i++) {
            byteArray[i] = data[indexDecode++];
        }
        double[] result = new double[byteArrayToInt(byteArray)];

        for (int i = 0; i < 3; i++) {
            byteArray = new byte[8];
            for (int j = 0; j < 8; j++) {
                byteArray[j] = data[indexDecode++];
            }
            result[i] = byteArrayToDouble(byteArray) + median;
        }

        for (int indexResult = 3; indexDecode < data.length; indexDecode++, indexResult++) {
            if (indexResult == result.length) {
                break;
            }
            switch (data[indexDecode]) {
                case 0:
                    byteArray = new byte[8];
                    for (int i = 0; i < 8; i++) {
                        byteArray[i] = data[++indexDecode];
                    }
                    result[indexResult] = byteArrayToDouble(byteArray) + median;
                    break;
                case 1:
                    result[indexResult] = result[indexResult - 1];
                    break;
                case 2:
                    result[indexResult] = 2 * result[indexResult - 1] - result[indexResult - 2];
                    break;
                case 3:
                    result[indexResult] = 3 * result[indexResult - 1]
                            - 3 * result[indexResult - 2]
                            + result[indexResult - 3];
                    break;
                default:
                    throw new SZByteCodeException("Incorrect byte code");
            }
        }
        return result;
    }

    private char getBestFitSolution(double pnf, double lcf, double qcf, double value) {
        if (Math.abs(pnf - value) < Math.abs(lcf - value)
                && Math.abs(pnf - value) < Math.abs(qcf - value)
                && Math.abs(pnf - value) < error) {
            return 'N';
        } else if (Math.abs(lcf - value) < Math.abs(pnf - value)
                && Math.abs(lcf - value) < Math.abs(qcf - value)
                && Math.abs(lcf - value) < error) {
            return 'L';
        } else if (Math.abs(qcf - value) < error) {
            return 'Q';
        } else {
            return '-';
        }
    }

    public double getError() {
        return error;
    }
}
