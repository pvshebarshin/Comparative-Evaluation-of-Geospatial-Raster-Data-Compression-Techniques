package compressors.interfaces;

import java.io.IOException;
import java.util.zip.DataFormatException;

public interface IntCompressor extends ICompressor {
    byte[] compress(int[] data) throws IOException;

    int[] decompress(byte[] data) throws DataFormatException, IOException;
}
