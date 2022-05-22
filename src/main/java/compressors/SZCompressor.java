package compressors;

import algorithms.sz.SZ;
import algorithms.sz.SZByteCodeException;
import algorithms.utils.mapping3D2D.MatrixConverter;
import compressors.interfaces.DoubleCompressor;
import compressors.interfaces.DoubleMatrixCompressor;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class SZCompressor implements DoubleCompressor, DoubleMatrixCompressor {

    private final SZ sz;

    private int size;

    public SZCompressor(double error) {
        sz = new SZ(error);
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        size = data.length * 8;
        return DeflaterUtils.compressByteArrayForSZ(sz.encode(data), size);
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        return sz.decode(DeflaterUtils.decompressByteArrayForSZ(data, size));
    }

    @Override
    public byte[] compressMatrix(double[][] data) throws SZByteCodeException {
        MatrixConverter matrixConverter = new MatrixConverter();
        return sz.encode(matrixConverter.matrixToArray(data));
    }

    @Override
    public double[][] decompressMatrix(byte[] data) throws SZByteCodeException {
        MatrixConverter matrixConverter = new MatrixConverter();
        return matrixConverter.arrayToMatrix(sz.decode(data));
    }
    @Override
    public String getParameters() {
        return String.valueOf(sz.getError());
    }

    @Override
    public String toString() {
        return "SZ";
    }
}
