package compressors.interfaces;

public interface IntMatrixCompressor {
    byte[] compressMatrix(int[][] data);

    int[][] decompressMatrix(byte[] data);
}
