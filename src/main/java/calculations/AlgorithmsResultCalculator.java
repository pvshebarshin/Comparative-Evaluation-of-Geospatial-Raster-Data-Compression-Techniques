package calculations;

import algorithms.Compressor;
import algorithms.bitgrooming.NSD;
import compressors.BitGroomingCompressor;
import compressors.BitShavingCompressor;
import compressors.FpcCompressor;
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

    public static double ratio;

    public void makeCalculations() throws IOException, DataFormatException {
        List<Compressor> compressors = initList();
        File file = createResultFile();
        PrintWriter out = new PrintWriter(file);
        out.println("Name,Ratio,Time,Size,Parameters,Type");

        List<Measuring> measurings = new ArrayList<>();
        List<String> fileNameMass = listFilesForFolder(new File("resources/data"));
        double[] data;
        byte[] compressedData;
        long time;
        for (String fileName : fileNameMass) {
            for (Compressor compressor : compressors) {
                data = parseRasterFile(fileName);

                time = System.nanoTime();
                compressedData = compressor.compress(data);
                time = System.nanoTime() - time;
                out.println(Measuring.newBuilder()
                        .setName(compressor.toString())
                        .setRatio(ratio)
                        .setTime(time)
                        .setSize(data.length * 8)
                        .setParameters(compressor.getParameters())
                        .setType("C")
                        .build()
                        .toString());

                time = System.nanoTime();
                compressor.decompress(compressedData);
                time = System.nanoTime() - time;
                out.println(Measuring.newBuilder()
                        .setName(compressor.toString())
                        .setRatio(ratio)
                        .setTime(time)
                        .setSize(data.length * 8)
                        .setParameters(compressor.getParameters())
                        .setType("D")
                        .build()
                        .toString());
            }
        }
        out.close();
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

    private File createResultFile() throws IOException {
        File file = new File("calculations" + File.separator + "calculations.csv");
        if (!file.exists() && file.createNewFile()) {
            throw new CalculationException("Unable to create result file: " + file.getAbsolutePath());
        }
        return file;
    }

    private static double[] parseRasterFile(String fileName) throws IOException {
        GeoTiffReader reader = new GeoTiffReader(new File(fileName));
        GridCoverage2D coverage = reader.read(null);

        if (coverage == null) {
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
