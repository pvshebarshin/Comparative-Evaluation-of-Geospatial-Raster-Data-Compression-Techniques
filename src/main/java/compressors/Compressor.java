package compressors;

import java.io.IOException;
import java.util.zip.DataFormatException;

public interface Compressor {
    byte[] compress(double[] data) throws IOException;
    double[] decompress(byte[] data) throws DataFormatException, IOException;
    String getParameters();
}
