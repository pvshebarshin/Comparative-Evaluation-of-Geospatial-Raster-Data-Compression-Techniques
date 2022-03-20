package calculations;

import algorithms.Compressor;
import algorithms.bitgrooming.NSD;
import compressors.BitGroomingCompressor;
import compressors.BitShavingCompressor;
import compressors.FpcCompressor;
import compressors.utils.DeflaterUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.DataFormatException;

public final class AlgorithmsResultCalculator {

    private static final Logger LOG = LogManager.getLogger(AlgorithmsResultCalculator.class);

    public void makeCalculations(String directory) throws IOException, DataFormatException {
        List<Compressor> compressors = initList();
        File file = createResultFile();
        try (PrintWriter out = new PrintWriter(file)) {
            out.println("Name,Ratio,Time,Size,Parameters,Type");

            List<String> fileNameMass = listFilesForFolder(new File(directory));

            for (String fileName : fileNameMass) {
                for (Compressor compressor : compressors) {
                    LOG.info(() -> compressor.toString() + " " + compressor.getParameters());
                    LOG.info(() -> "Begin of Measuring");
                    makeMeasuring(out, fileName, compressor);
                    LOG.info(() -> "End of Measuring");
                }
            }
        }
    }

    private void makeMeasuring(PrintWriter out, String fileName, Compressor compressor)
            throws IOException, DataFormatException {
        double[] data;
        byte[] compressedData;
        long time;
        data = parseRasterFile(fileName);

        time = System.nanoTime();
        compressedData = compressor.compress(data);
        time = System.nanoTime() - time;
        out.println(Measuring.newBuilder()
                .setName(compressor.toString())
                .setRatio(DeflaterUtils.getRatio())
                .setTime(time)
                .setSize(data.length * 8L)
                .setParameters(compressor.getParameters())
                .setType("C")
                .build()
                .toString());

        time = System.nanoTime();
        compressor.decompress(compressedData);
        time = System.nanoTime() - time;
        out.println(Measuring.newBuilder()
                .setName(compressor.toString())
                .setRatio(DeflaterUtils.getRatio())
                .setTime(time)
                .setSize(data.length * 8L)
                .setParameters(compressor.getParameters())
                .setType("D")
                .build()
                .toString());
    }

    private List<String> listFilesForFolder(final File folder) {
        List<String> fileNameMass = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                fileNameMass.add(fileEntry.getName());
            }
        }
        return fileNameMass;
    }

    private File createResultFile() {
        File file = new File("calculations" + File.separator + "calculations.csv");

        try {
            if (file.createNewFile()) {
                LOG.info(() -> "The new result file has been created");
            } else {
                LOG.info(() -> "The result file has already been created");
            }
        } catch (IOException e) {
            throw new CalculationException("Unable to create result file: " + file.getAbsolutePath(), e);
        }

        return file;
    }

    private static double[] parseRasterFile(String fileName) throws IOException {
        GeoTiffReader reader = new GeoTiffReader(new File(fileName));
        GridCoverage2D coverage = reader.read(null);

        if (coverage == null) {
            LOG.error(() -> "GridCoverage2D is null");
            throw new CalculationException("Incorrect file name:" + fileName);
        }

        RenderedImage image = coverage.getRenderedImage();
        Raster inputRaster = image.getData();

        DataBuffer originalImage = inputRaster.getDataBuffer();
        double[] newByteArray = new double[inputRaster.getWidth() * inputRaster.getHeight()];
        for (int i = 0; i < originalImage.getSize(); i++) {
            newByteArray[i] = originalImage.getElemDouble(i);
        }
        return newByteArray;
    }

    private List<Compressor> initList() {
        List<Compressor> compressors = new ArrayList<>();
        compressors.add(new FpcCompressor());
        for (NSD nsd : NSD.values()) {
            compressors.add(new BitGroomingCompressor(nsd));
        }
        for (int i = 0; i < 53; i++) {
            compressors.add(new BitShavingCompressor(i));
        }
        return compressors;
    }
}
