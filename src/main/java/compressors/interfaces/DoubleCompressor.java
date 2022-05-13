package compressors.interfaces;

import java.io.IOException;
import java.util.zip.DataFormatException;

public interface DoubleCompressor extends ICompressor {
    byte[] compress(double[] data) throws IOException;

    double[] decompress(byte[] data) throws DataFormatException, IOException;
}
