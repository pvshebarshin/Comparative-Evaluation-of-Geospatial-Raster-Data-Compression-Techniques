package algorithms.k2raster;

import algorithms.utils.BitMatrix;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;

public class K2RasterTest {

    @Test
    public void notThrowTest1() {
        boolean[][] mass = {
                {true, false},
                {false, true}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        Assertions.assertDoesNotThrow(() -> new K2Tree(bitMatrix));
    }

    @Test
    public void notThrowTest2() {
        boolean[][] mass = {
                {true, false, false, false},
                {false, true, false, false},
                {true, false, false, false},
                {false, true, false, false}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        Assertions.assertDoesNotThrow(() -> new K2Tree(bitMatrix));
    }

    @Test
    public void serializationTest1() {
        boolean[][] mass = {
                {true, false},
                {false, true}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        K2Tree serializationResult = K2Tree.deserialize(K2Tree.serialize(k2Tree));
        Assertions.assertEquals(k2Tree, serializationResult);
    }

    @Test
    public void serializationTest2() {
        boolean[][] mass = {
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        K2Tree serializationResult = K2Tree.deserialize(K2Tree.serialize(k2Tree));
        Assertions.assertEquals(k2Tree, serializationResult);
    }

    @Test
    public void serializationTest3() {
        boolean[][] mass = {
                {true, false, false, false},
                {false, true, false, false},
                {true, false, false, false},
                {false, true, false, false}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        K2Tree serializationResult = K2Tree.deserialize(K2Tree.serialize(k2Tree));
        Assertions.assertEquals(k2Tree, serializationResult);
    }

    @Test
    public void serializationTest4() {
        boolean[][] mass = {
                {true, false, false, false, true, true, true, true},
                {false, true, false, false, true, true, true, true},
                {true, false, false, false, true, true, true, true},
                {false, false, false, false, true, true, true, true},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        K2Tree serializationResult = K2Tree.deserialize(K2Tree.serialize(k2Tree));
        Assertions.assertEquals(k2Tree, serializationResult);
    }

    @Test
    public void serializationTest5() {
        boolean[][] mass = new boolean[1024][1024];
        Random random = new Random();
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[0].length; j++) {
                mass[i][j] = random.nextBoolean();
            }
        }
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        K2Tree serializationResult = K2Tree.deserialize(K2Tree.serialize(k2Tree));
        Assertions.assertEquals(k2Tree, serializationResult);
    }

    @Test
    public void toMatrixTest1() {
        boolean[][] mass = {
                {true, false},
                {false, true}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        BitMatrix result = k2Tree.toMatrix();
        Assertions.assertEquals(bitMatrix, result);
    }

    @Test
    public void toMatrixTest2() {
        boolean[][] mass = {
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        BitMatrix result = k2Tree.toMatrix();
        Assertions.assertEquals(bitMatrix, result);
    }

    @Test
    public void toMatrixTest3() {
        boolean[][] mass = {
                {true, false, false, false},
                {false, true, false, false},
                {true, false, false, false},
                {false, true, false, false}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        BitMatrix result = k2Tree.toMatrix();
        Assertions.assertEquals(bitMatrix, result);
    }

    @Test
    public void toMatrixTest4() {
        boolean[][] mass = {
                {true, false, false, false, true, true, true, true},
                {false, true, false, false, true, true, true, true},
                {true, false, false, false, true, true, true, true},
                {false, false, false, false, true, true, true, true},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        };
        BitMatrix bitMatrix = BitMatrix.parse(mass);
        K2Tree k2Tree = new K2Tree(bitMatrix);
        BitMatrix result = k2Tree.toMatrix();
        Assertions.assertEquals(bitMatrix, result);
    }
}
