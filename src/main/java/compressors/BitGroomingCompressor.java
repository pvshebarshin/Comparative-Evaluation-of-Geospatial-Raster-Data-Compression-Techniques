package compressors;

import algorithms.Compressor;
import algorithms.bitgrooming.BitGrooming;
import algorithms.bitgrooming.NSD;
import compressors.utils.DeflaterUtils;

public class BitGroomingCompressor implements Compressor {
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
    public byte[] compress(double[] data) {
        BitGrooming bitGrooming = new BitGrooming();
        data = bitGrooming.encode(data, nsd);
        return DeflaterUtils.convertLowerAccuracyDoublesToBits(data);
    }

    @Override
    public double[] decompress(byte[] data) {
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
