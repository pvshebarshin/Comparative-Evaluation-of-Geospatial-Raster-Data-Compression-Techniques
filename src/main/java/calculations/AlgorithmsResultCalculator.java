package calculations;

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
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

public final class AlgorithmsResultCalculator {

    private static final Logger LOG = LogManager.getLogger(AlgorithmsResultCalculator.class);

    private static final int DEPTH_OF_READING_DATA_IN_DIRECTORY = 2;

    public void makeCalculations(String directory) throws IOException, DataFormatException {
        List<Compressor> compressors = initList();
        File file = createResultFile();
        try (PrintWriter printWriter = new PrintWriter(Files.newOutputStream(file.toPath()), true)) {
            if (file.length() == 0) {
                printWriter.append("Name,Ratio,Time,Size,Parameters,Type\n");
            }

            List<String> fileNameMass = listFilesForFolder(directory);

            for (String fileName : fileNameMass) {
                for (Compressor compressor : compressors) {
                    try {
                        LOG.log(Level.DEBUG, () -> compressor.toString() + " " + compressor.getParameters());
                        LOG.debug(() -> "Begin of Measuring");
                        makeMeasuring(printWriter, fileName, compressor);
                        LOG.debug(() -> "End of Measuring");
                    } catch (Exception e) {
                        LOG.error(e::getMessage);
                    }
                }
            }
        }
    }

    private void makeMeasuring(PrintWriter printWriter, String fileName, Compressor compressor)
            throws IOException, DataFormatException {
        double[] data;
        byte[] compressedData;
        long time;
        data = parseRasterFile(fileName);

        time = System.nanoTime();
        compressedData = compressor.compress(data);
        time = System.nanoTime() - time;
        printWriter.append(Measuring.newBuilder()
                .setName(compressor.toString())
                .setRatio(DeflaterUtils.getRatio())
                .setTime(time)
                .setSize(data.length * 8L)
                .setParameters(compressor.getParameters())
                .setType("C")
                .build()
                .toString())
                .append(String.valueOf('\n'));

        time = System.nanoTime();
        compressor.decompress(compressedData);
        time = System.nanoTime() - time;
        printWriter.append(Measuring.newBuilder()
                .setName(compressor.toString())
                .setRatio(DeflaterUtils.getRatio())
                .setTime(time)
                .setSize(data.length * 8L)
                .setParameters(compressor.getParameters())
                .setType("D")
                .build()
                .toString())
                .append(String.valueOf('\n'));
    }

    private List<String> listFilesForFolder(final String folder) {
        try (Stream<Path> stream = Files.walk(Paths.get(folder), DEPTH_OF_READING_DATA_IN_DIRECTORY)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CalculationException(e.getMessage(), e);
        }
    }

    private File createResultFile() {
        File file = new File("src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "calculations.csv");

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
//        for (NSD nsd : NSD.values()) {
//            compressors.add(new BitGroomingCompressor(nsd));
//        }
//        for (int i = 0; i < 53; i++) {
//            compressors.add(new BitShavingCompressor(i));
//        }
//        for (int i = 0; i < 11; i++) {
//            compressors.add(new DigitRoutingCompressor(i));
//        }
        return compressors;
    }
}
