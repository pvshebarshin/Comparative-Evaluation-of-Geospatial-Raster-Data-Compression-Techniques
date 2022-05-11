package compressors;

import algorithms.k2raster.K2Tree;
import algorithms.utils.mapping3D2D.MatrixConverter;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class K2RasterCompressor extends KRasterCompressor implements IntCompressor {

    @Override
    public byte[] compress(int[] data) throws IOException {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = new K2Tree(matrixConverter.encode(data), data.length);
        return DeflaterUtils.compressByteArray(
                K2Tree.serialize(k2Tree)
        );
    }

    @Override
    public int[] decompress(byte[] data) throws DataFormatException, IOException {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = K2Tree.deserialize(
                DeflaterUtils.decompressByteArray(data)
        );
        return matrixConverter.decode(k2Tree.toMatrix(), k2Tree.getZMassSize());
    }

    @Override
    public String toString() {
        return "K2Raster";
    }

    @Override
    public String getParameters() {
        return "-";
    }
}
