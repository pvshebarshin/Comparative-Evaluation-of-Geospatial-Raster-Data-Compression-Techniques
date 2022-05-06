package compressors;

import algorithms.k2raster.K2Tree;
import algorithms.utils.mapping3D2D.MatrixConverter;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class K2RasterCompressor implements Compressor {

    private String parameter = "-";

    @Override
    public byte[] compress(double[] data) throws IOException {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = new K2Tree(matrixConverter.encodeNDVI(data));
        return DeflaterUtils.compressByteArray(
                K2Tree.serialize(k2Tree)
        );
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        MatrixConverter matrixConverter = new MatrixConverter();
        K2Tree k2Tree = K2Tree.deserialize(
                DeflaterUtils.decompressByteArray(data)
        );
        return matrixConverter.decodeNDVI(k2Tree.toMatrix());
    }

    @Override
    public String toString() {
        return "K2Raster";
    }

    @Override
    public String getParameters() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
