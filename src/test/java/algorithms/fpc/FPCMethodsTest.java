package algorithms.fpc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FPCMethodsTest {

    @Test
    void toLong() {
        FPC comp = new FPC();
        Assertions.assertEquals(Long.parseLong("1521659230455082264"),
                comp.toLong(new byte[]{24, 45, 120, 12, 2, 5, 30, 21}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("80564524293948674"),
                comp.toLong(new byte[]{2, 5, 10, 12, 2, 57, 30, 1}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("9187201950435737471"),
                comp.toLong(new byte[]{127, 127, 127, 127, 127, 127, 127, 127}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("107806561077756287"),
                comp.toLong(new byte[]{127, 1, 127, 1, 127, 1, 127, 1}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("72058693566333184"),
                comp.toLong(new byte[]{0, 1, 0, 1, 0, 1, 0, 1}),
                "Incorrect byte array conversion to Long 64");
        Assertions.assertEquals(Long.parseLong("72057594037927936"),
                comp.toLong(new byte[]{0, 0, 0, 0, 0, 0, 0, 1}),
                "Incorrect byte array conversion to Long 64");
    }

    @Test
    void toByteArray() {
        FPC comp = new FPC();
        Assertions.assertArrayEquals(new byte[]{24, 45, 120, 12, 2, 5, 30, 21},
                comp.toByteArray(Long.parseLong("1521659230455082264")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{2, 5, 10, 12, 2, 57, 30, 1},
                comp.toByteArray(Long.parseLong("80564524293948674")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{127, 127, 127, 127, 127, 127, 127, 127},
                comp.toByteArray(Long.parseLong("9187201950435737471")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{127, 1, 127, 1, 127, 1, 127, 1},
                comp.toByteArray(Long.parseLong("107806561077756287")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{0, 1, 0, 1, 0, 1, 0, 1},
                comp.toByteArray(Long.parseLong("72058693566333184")),
                "Incorrect conversion of Long 64 to byte array");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 1},
                comp.toByteArray(Long.parseLong("72057594037927936")),
                "Incorrect conversion of Long 64 to byte array");
    }

    @Test
    void testLongToByteArray() {
        FPC comp = new FPC();
        for (long i = 0; i < 10000; i++) {
            Assertions.assertEquals(i, comp.toLong(comp.toByteArray(i)));
        }
    }
}