package calculations;

import algorithms.bitgrooming.NSD;
import compressors.*;
import compressors.interfaces.DoubleCompressor;
import compressors.interfaces.ICompressor;
import compressors.interfaces.IntCompressor;
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

    /**
     * Make calculations of algorithms compressors on entered data
     *
     * @param doubleStoreDirectory Directory for double data
     * @param uintStoreDirectory   Directory for uint data
     * @param resultFilePath       Path for result file
     */
    public void makeCalculations(String doubleStoreDirectory, String uintStoreDirectory, String resultFilePath) {
        List<ICompressor> doubleCompressors = initList();
        Path path = getResultPath(resultFilePath);
        List<String> fileNameMassDouble = listFilesForFolder(doubleStoreDirectory);
        List<String> fileNameMassUint = listFilesForFolder(uintStoreDirectory);

        for (ICompressor compressor : doubleCompressors) {
            if (compressor instanceof KRasterCompressor) {
                makeMeasurementsFromFileList(path, fileNameMassUint, compressor);
            } else {
                makeMeasurementsFromFileList(path, fileNameMassDouble, compressor);
            }
        }
    }

    /**
     * Initialization of compressors list
     *
     * @return Compressors list
     */
    private List<ICompressor> initList() {
        List<ICompressor> compressors = new ArrayList<>();

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

        compressors.add(new K2RasterCompressor());

        return compressors;
    }

    /**
     * Makes measurements of a certain compressor on an array of all the images that were in the entered directory
     *
     * @param path         Path to file with measuring results
     * @param fileNameMass Array of input data file names
     * @param compressor   Compressor with algorithm implementation
     */
    private void makeMeasurementsFromFileList(Path path, List<String> fileNameMass, ICompressor compressor) {
        for (String fileName : fileNameMass) {
            try {
                LOG.debug(() -> compressor.toString() + " " + compressor.getParameters());
                LOG.debug(() -> "Begin of Measuring");
                makeMeasuring(path, fileName, compressor);
                LOG.debug(() -> "End of Measuring");
            } catch (Exception e) {
                LOG.error(e::getLocalizedMessage);
            }
        }
    }

    /**
     * Make single measuring
     *
     * @param path       Path to file with measuring results
     * @param fileName   Name of input file
     * @param compressor Compressor with algorithm implementation
     * @throws IOException         Exception while read or write file
     * @throws DataFormatException Exception wile deflate bata
     */
    private void makeMeasuring(Path path, String fileName, ICompressor compressor)
            throws IOException, DataFormatException {
        if (compressor instanceof KRasterCompressor) {
            int[] data = parseRasterFileInt(fileName);
            byte[] compressedData;
            long time;

            time = System.nanoTime();
            compressedData = ((IntCompressor) compressor).compress(data);
            time = System.nanoTime() - time;
            writeMeasuringToFile(path, compressor, data, time, "C");

            time = System.nanoTime();
            ((IntCompressor) compressor).decompress(compressedData);
            time = System.nanoTime() - time;
            writeMeasuringToFile(path, compressor, data, time, "D");
        } else {
            double[] data = parseRasterFileDouble(fileName);
            byte[] compressedData;
            long time;

            time = System.nanoTime();
            compressedData = ((DoubleCompressor) compressor).compress(data);
            time = System.nanoTime() - time;
            writeMeasuringToFile(path, compressor, data, time, "C");

            time = System.nanoTime();
            ((DoubleCompressor) compressor).decompress(compressedData);
            time = System.nanoTime() - time;
            writeMeasuringToFile(path, compressor, data, time, "D");
        }
    }

    /**
     * Get raster from file name by GeoTiff
     *
     * @param fileName Name of file
     * @return Raster
     * @throws IOException Exception while rading wile
     */
    private static Raster getRasterByGeoTiff(String fileName) throws IOException {
        GeoTiffReader reader = new GeoTiffReader(new File(fileName));
        GridCoverage2D coverage = reader.read(null);

        if (coverage == null) {
            LOG.error(() -> "GridCoverage2D is null");
            throw new CalculationException("Incorrect file name:" + fileName);
        }

        RenderedImage image = coverage.getRenderedImage();
        return image.getData();
    }

    /**
     * Get list of paths with data from input directories
     *
     * @param storeDirectory Input store directory
     * @return List of paths with data
     */
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

    /**
     * Get path for result of measuring and create begin data if it not exists
     *
     * @param resultFilePath Name of path of measuring result
     * @return Path of measuring result
     */
    private Path getResultPath(String resultFilePath) {
        File file = new File(resultFilePath);

        try {
            if (file.createNewFile()) {
                LOG.info(() -> "The new result file has been created");
                Files.write(
                        file.toPath(),
                        "Name,Ratio,Time,Size,Parameters,Type\n".getBytes(),
                        StandardOpenOption.APPEND
                );
            } else {
                LOG.info(() -> "The result file has already been created");
            }
        } catch (IOException e) {
            throw new CalculationException("Unable to create result file: " + file.getAbsolutePath(), e);
        }

        return file.toPath();
    }

    /**
     * Parse raster file of Float64 data
     *
     * @param fileName Name of raster file
     * @return Array of double data
     * @throws IOException Exception while reading file
     */
    private static double[] parseRasterFileDouble(String fileName) throws IOException {
        Raster inputRaster = getRasterByGeoTiff(fileName);

        DataBuffer originalImage = inputRaster.getDataBuffer();
        double[] arrayOfRasterData = new double[inputRaster.getWidth() * inputRaster.getHeight()];
        for (int i = 0; i < originalImage.getSize(); i++) {
            arrayOfRasterData[i] = originalImage.getElemDouble(i);
        }
        return arrayOfRasterData;
    }

    /**
     * Parse raster file of uint16 data
     *
     * @param fileName Name of raster file
     * @return Array of int data
     * @throws IOException Exception while reading file
     */
    private static int[] parseRasterFileInt(String fileName) throws IOException {
        Raster inputRaster = getRasterByGeoTiff(fileName);

        DataBuffer originalImage = inputRaster.getDataBuffer();
        int[] arrayOfRasterData = new int[inputRaster.getWidth() * inputRaster.getHeight()];
        for (int i = 0; i < arrayOfRasterData.length; i++) {
            arrayOfRasterData[i] = originalImage.getElem(i);
        }
        return arrayOfRasterData;
    }

    /**
     * Write measuring for int data to file
     *
     * @param path            Path to file
     * @param compressor      Compressor
     * @param data            Int data
     * @param time            Time of measuring
     * @param typeOfOperation Type of operation
     * @throws IOException Exception while writing result of measuring
     */
    private void writeMeasuringToFile(Path path,
                                      ICompressor compressor,
                                      int[] data,
                                      long time,
                                      String typeOfOperation)
            throws IOException {
        Files.write(
                path,
                (
                        Measuring.newBuilder()
                                .setName(compressor.toString())
                                .setRatio(DeflaterUtils.getRatio())
                                .setTime(time)
                                .setSize(data.length * 8L)
                                .setParameters(compressor.getParameters())
                                .setType(typeOfOperation)
                                .build()
                                .toString() + '\n'
                ).getBytes(),
                StandardOpenOption.APPEND
        );
    }

    /**
     * Write measuring for double data to file
     *
     * @param path            Path to file
     * @param compressor      Compressor
     * @param data            Double data
     * @param time            Time of measuring
     * @param typeOfOperation Type of operation
     * @throws IOException Exception while writing result of measuring
     */
    private void writeMeasuringToFile(Path path,
                                      ICompressor compressor,
                                      double[] data,
                                      long time,
                                      String typeOfOperation)
            throws IOException {
        Files.write(
                path,
                (
                        Measuring.newBuilder()
                                .setName(compressor.toString())
                                .setRatio(DeflaterUtils.getRatio())
                                .setTime(time)
                                .setSize(data.length * 8L)
                                .setParameters(compressor.getParameters())
                                .setType(typeOfOperation)
                                .build()
                                .toString() + '\n'
                ).getBytes(),
                StandardOpenOption.APPEND
        );
    }
}
