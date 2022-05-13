package compressors;

import algorithms.bitgrooming.NSD;
import algorithms.bitgrooming.BitGrooming;
import compressors.interfaces.DoubleCompressor;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class BitGroomingCompressor implements DoubleCompressor {

    private NSD nsd;

    public void setNsd(NSD nsd) {
        this.nsd = nsd;
    }

    public NSD getNsd() {
        return nsd;
    }

    public BitGroomingCompressor(NSD nsd) {
        this.nsd = nsd;
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        BitGrooming bitGrooming = new BitGrooming();
        data = bitGrooming.encode(data, nsd);
        return DeflaterUtils.convertLowerAccuracyDoublesToBits(data);
    }

    @Override
    public double[] decompress(byte[] data) throws DataFormatException, IOException {
        return DeflaterUtils.decompressByteToDoubles(data);
    }

    @Override
    public String toString() {
        return "BitGrooming";
    }

    @Override
    public String getParameters() {
        return nsd.toString();
    }
}
