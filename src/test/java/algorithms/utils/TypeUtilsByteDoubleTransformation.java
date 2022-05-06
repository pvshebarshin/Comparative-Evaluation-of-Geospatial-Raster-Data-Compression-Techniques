package algorithms.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;

public class TypeUtilsByteDoubleTransformation {

    @Test
    public void typeUtilsByteIntTransformation() {
        Random random = new Random();
        double value, result;
        for (int i = 0; i < 300; i++) {
            value = random.nextDouble();
            result = TypeUtils.byteArrayToDouble(TypeUtils.doubleToByteArray(value));
            Assertions.assertEquals(value, result, "Incorrect working of double/byte convert");
        }
    }
}
