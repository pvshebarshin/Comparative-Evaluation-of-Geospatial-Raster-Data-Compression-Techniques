package compressors;

import algorithms.k2raster.K2Tree;
import algorithms.utils.mapping3D2D.MatrixConverter;
import compressors.interfaces.IntCompressor;
import compressors.interfaces.IntMatrixCompressor;
import compressors.utils.DeflaterUtils;

import java.io.IOException;

public class K2RasterCompressor extends KRasterCompressor implements IntCompressor, IntMatrixCompressor {

    @Override
    public byte[] compress(int[] data) throws IOException {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = new K2Tree(matrixConverter.encodeArray(data), data.length);
        return DeflaterUtils.getRatio(K2Tree.serialize(k2Tree), data.length * 4);
    }

    @Override
    public int[] decompress(byte[] data) {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = K2Tree.deserialize(data);
        return matrixConverter.decodeArray(k2Tree.toMatrix(), k2Tree.getZMassSize());
    }

    @Override
    public byte[] compressMatrix(int[][] data) {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = new K2Tree(matrixConverter.encodeMatrix(data), data.length * data[0].length);
        return DeflaterUtils.getRatio(
                K2Tree.serialize(k2Tree), data.length * data[0].length * 4
        );
    }

    @Override
    public int[][] decompressMatrix(byte[] data) {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = K2Tree.deserialize(data);
        return matrixConverter.decodeMatrix(k2Tree.toMatrix(), k2Tree.getZMassSize());
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
