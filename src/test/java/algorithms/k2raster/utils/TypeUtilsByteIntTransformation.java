package algorithms.k2raster.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;

public class TypeUtilsByteIntTransformation {
    @Test
    public void typeUtilsByteIntTransformation() {
        Random random = new Random();
        int value, result;
        for (int i = 0; i < 300; i++) {
            value = random.nextInt();
            result = TypeUtils.byteArrayToInt(TypeUtils.intToByteArray(value));
            Assertions.assertEquals(value, result);
        }
    }
}
