package algorithms.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public final class TypeUtils {

    private TypeUtils() {
    }

    /**
     * Turns double number into an array of bytes
     *
     * @param value Double value
     * @return Representation of the double value in bytes
     */
    public static byte[] doubleToByteArray(double value) {
        long data = Double.doubleToRawLongBits(value);
        return new byte[]{
                (byte) ((data >> 56) & 0xff),
                (byte) ((data >> 48) & 0xff),
                (byte) ((data >> 40) & 0xff),
                (byte) ((data >> 32) & 0xff),
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    /**
     * Turns an array of bytes into double
     *
     * @param array bytes of double value
     * @return double value
     */
    public static double byteArrayToDouble(byte[] array) {
        return ByteBuffer.wrap(array).getDouble();
    }

    /**
     * Converting byte array to long
     *
     * @param data Bytes of long
     * @return Long value
     */
    public static long byteArrayToLong(byte[] data) {
        long result = 0L;
        for (int i = data.length; i > 0; i--) {
            result <<= 8;
            result |= data[i - 1] & 0xff;
        }
        return result;
    }

    /**
     * Returns an array of bits representing a long number
     *
     * @param data Long value
     * @return Long's bytes
     */
    public static byte[] longToByteArray(long data) {
        int encodedZeroBytes = encodeZeroBytes(data);
        if (encodedZeroBytes > 3) {
            encodedZeroBytes++;
        }
        byte[] array = new byte[8 - encodedZeroBytes];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) (data & 0xff);
            data >>= 8;
        }
        return array;
    }

    /**
     * Auxiliary method for the method longToByteArray.
     * Returns the number of zero bytes of long.
     *
     * @param value Long value
     * @return number of zero bytes
     */
    public static int encodeZeroBytes(long value) {
        int leadingZeroBytes = Long.numberOfLeadingZeros(value) / 8;
        if (leadingZeroBytes >= 4) {
            leadingZeroBytes--;
        }
        return leadingZeroBytes;
    }

    /**
     * Turns an int array into a byte array
     *
     * @param array int array
     * @return byte array
     */
    public static byte[] intArrayToByteArray(int[] array) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(array.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(array);
        return byteBuffer.array();
    }

    /**
     * Turns a byte array into an int array
     *
     * @param byteArray byte array
     * @return int array
     */
    public static int[] byteArrayToIntArray(byte[] byteArray) {
        IntBuffer intBuf = ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        for (int i = 0; i < array.length; i++) {
            array[i] = intBuf.get(i);
        }
        return array;
    }

    /**
     * Turns an array of bytes into int
     *
     * @param data bytes of int value
     * @return int value
     */
    public static int byteArrayToInt(byte[] data) {
        return data[3] & 0xFF |
                (data[2] & 0xFF) << 8 |
                (data[1] & 0xFF) << 16 |
                (data[0] & 0xFF) << 24;
    }

    /**
     * Turns int number into an array of bytes
     *
     * @param value int value
     * @return Representation of the int value in bytes
     */
    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }
}
