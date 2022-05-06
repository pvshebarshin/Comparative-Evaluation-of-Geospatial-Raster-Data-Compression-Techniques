package calculations;

import algorithms.bitgrooming.NSD;
import compressors.*;
import compressors.utils.DeflaterUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

public final class AlgorithmsResultCalculator {

    private static final Logger LOG = LogManager.getLogger(AlgorithmsResultCalculator.class);

    private static final int DEPTH_OF_READING_DATA_IN_DIRECTORY = 2;

    public void makeCalculations(String storeDirectory, String resultFilePath) throws IOException, DataFormatException {
        List<Compressor> compressors = initList();
        Path path = getResultPath(resultFilePath);
        List<String> fileNameMass = listFilesForFolder(storeDirectory);

        for (String fileName : fileNameMass) {
            for (Compressor compressor : compressors) {
                try {
                    LOG.log(Level.DEBUG, () -> compressor.toString() + " " + compressor.getParameters());
                    LOG.debug(() -> "Begin of Measuring");
                    makeMeasuring(path, fileName, compressor);
                    LOG.debug(() -> "End of Measuring");
                } catch (Exception e) {
                    LOG.error(e::getLocalizedMessage);
                }
            }
        }
    }

    private void makeMeasuring(Path path, String fileName, Compressor compressor)
            throws IOException, DataFormatException {
        double[] data = parseRasterFile(fileName);
        byte[] compressedData;
        long time;

        time = System.nanoTime();
        compressedData = compressor.compress(data);
        time = System.nanoTime() - time;
        Files.write(
                path,
                (
                        Measuring.newBuilder()
                                .setName(compressor.toString())
                                .setRatio(DeflaterUtils.getRatio())
                                .setTime(time)
                                .setSize(data.length * 8L)
                                .setParameters(compressor.getParameters())
                                .setType("C")
                                .build()
                                .toString() + '\n'
                ).getBytes(),
                StandardOpenOption.APPEND
        );

        time = System.nanoTime();
        compressor.decompress(compressedData);
        time = System.nanoTime() - time;
        Files.write(
                path,
                (
                        Measuring.newBuilder()
                                .setName(compressor.toString())
                                .setRatio(DeflaterUtils.getRatio())
                                .setTime(time)
                                .setSize(data.length * 8L)
                                .setParameters(compressor.getParameters())
                                .setType("D")
                                .build()
                                .toString() + '\n'
                ).getBytes(),
                StandardOpenOption.APPEND
        );
    }

    private List<String> listFilesForFolder(String storeDirectory) {
        try (Stream<Path> stream = Files.walk(Paths.get(storeDirectory), DEPTH_OF_READING_DATA_IN_DIRECTORY)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CalculationException(e.getMessage(), e);
        }
    }

    private Path getResultPath(String resultFilePath) {
        File file = new File(resultFilePath);

        try {
            if (file.createNewFile()) {
                LOG.info(() -> "The new result file has been created");
                Files.write(
                        file.toPath(),
                        "Name,Ratio,Time,Size,Parameters,Type\n".getBytes(),
                        StandardOpenOption.APPEND);
            } else {
                LOG.info(() -> "The result file has already been created");
            }
        } catch (IOException e) {
            throw new CalculationException("Unable to create result file: " + file.getAbsolutePath(), e);
        }

        return file.toPath();
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
        compressors.add(new FPCCompressor());

        compressors.add(new SZCompressor(0.0));
        compressors.add(new SZCompressor(0.01));
        compressors.add(new SZCompressor(0.05));
        compressors.add(new SZCompressor(0.1));
        compressors.add(new SZCompressor(0.2));
        compressors.add(new SZCompressor(0.3));

        for (NSD nsd : NSD.values()) {
            compressors.add(new BitGroomingCompressor(nsd));
        }
        for (int i = 0; i < 53; i++) {
            compressors.add(new BitShavingCompressor(i));
        }
        for (int i = 0; i < 53; i++) {
            compressors.add(new DigitRoutingCompressor(i));
        }
//        compressors.add(new K2RasterCompressor());
        return compressors;
    }
}
