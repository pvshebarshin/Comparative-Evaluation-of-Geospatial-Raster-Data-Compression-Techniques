package algorithms.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeUtilsByteLongTransformation {

    @Test
    void toLong() {
        Assertions.assertEquals(Long.parseLong("1521659230455082264"),
                TypeUtils.byteArrayToLong(new byte[]{24, 45, 120, 12, 2, 5, 30, 21}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("80564524293948674"),
                TypeUtils.byteArrayToLong(new byte[]{2, 5, 10, 12, 2, 57, 30, 1}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("9187201950435737471"),
                TypeUtils.byteArrayToLong(new byte[]{127, 127, 127, 127, 127, 127, 127, 127}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("107806561077756287"),
                TypeUtils.byteArrayToLong(new byte[]{127, 1, 127, 1, 127, 1, 127, 1}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("72058693566333184"),
                TypeUtils.byteArrayToLong(new byte[]{0, 1, 0, 1, 0, 1, 0, 1}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("72057594037927936"),
                TypeUtils.byteArrayToLong(new byte[]{0, 0, 0, 0, 0, 0, 0, 1}),
                "Incorrect byte array conversion to Long 64");
    }

    @Test
    void toByteArray() {
        Assertions.assertArrayEquals(new byte[]{24, 45, 120, 12, 2, 5, 30, 21},
                TypeUtils.longToByteArray(Long.parseLong("1521659230455082264")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{2, 5, 10, 12, 2, 57, 30, 1},
                TypeUtils.longToByteArray(Long.parseLong("80564524293948674")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{127, 127, 127, 127, 127, 127, 127, 127},
                TypeUtils.longToByteArray(Long.parseLong("9187201950435737471")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{127, 1, 127, 1, 127, 1, 127, 1},
                TypeUtils.longToByteArray(Long.parseLong("107806561077756287")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{0, 1, 0, 1, 0, 1, 0, 1},
                TypeUtils.longToByteArray(Long.parseLong("72058693566333184")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 1},
                TypeUtils.longToByteArray(Long.parseLong("72057594037927936")),
                "Incorrect conversion of Long 64 to byte array");
    }

    @Test
    void testLongToByteArray() {
        for (long i = 100000000000L; i < 100000001000L; i++) {
            Assertions.assertEquals(i, TypeUtils.byteArrayToLong(TypeUtils.longToByteArray(i)));
        }
    }
}