package compressors;

import algorithms.bitgrooming.NSD;
import algorithms.bitgrooming.BitGrooming;
import compressors.utils.DeflaterUtils;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class BitGroomingCompressor implements Compressor {

    private NSD BGNsd;

    public void setNsd(NSD BGNsd) {
        this.BGNsd = BGNsd;
    }

    public NSD getNsd() {
        return BGNsd;
    }

    public BitGroomingCompressor(NSD BGNsd) {
        this.BGNsd = BGNsd;
    }

    @Override
    public byte[] compress(double[] data) throws IOException {
        BitGrooming bitGrooming = new BitGrooming();
        data = bitGrooming.encode(data, BGNsd);
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
        return BGNsd.toString();
    }
}
