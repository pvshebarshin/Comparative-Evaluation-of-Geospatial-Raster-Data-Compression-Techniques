package algorithms;

public interface Compressor {
    byte[] compress(double[] data);
    double[] decompress(byte[] data);
    String getParameters();
}
