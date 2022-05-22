package compressors.interfaces;

import java.io.IOException;

public interface DoubleMatrixCompressor {
    byte[] compressMatrix(double[][] data) throws IOException;

    double[][] decompressMatrix(byte[] data) throws IOException;
}
