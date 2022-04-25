package algorithms.k2raster.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@RunWith(value = Parameterized.class)
public class TypeUtilsByteIntArraysTransformation {

    private final TestParameter testParameter;

    public TypeUtilsByteIntArraysTransformation(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestParameter> data() {
        ArrayList<TestParameter> parameters = new ArrayList<>();
        Random random = new Random();

        int[] data = new int[100];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }
        parameters.add(new TestParameter(data));

        data = new int[10000];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }
        parameters.add(new TestParameter(data));

        data = new int[10000000];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }
        parameters.add(new TestParameter(data));

        data = new int[45678];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }
        parameters.add(new TestParameter(data));

        data = new int[777];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }
        parameters.add(new TestParameter(data));

        return parameters;
    }

    @Test
    public void typeUtilsByteIntTransformation() {
        Assertions.assertArrayEquals(
                TypeUtils.byteArrayToIntArray(TypeUtils.intArrayToByteArray(testParameter.data)),
                testParameter.data
        );
    }

    private static class TestParameter {
        private static int i = 0;
        private final int[] data;

        public TestParameter(int[] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Array " + ++i + " Length = " + data.length;
        }
    }
}
