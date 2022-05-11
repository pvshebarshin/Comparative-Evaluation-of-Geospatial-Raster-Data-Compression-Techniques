package calculations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class LaunchCalculations {

    private static final Logger LOG = LogManager.getLogger(LaunchCalculations.class);

    public static void main(String[] args) throws DataFormatException, IOException {
        args = checkArguments(args);
        LOG.info("The directory where the double data for calculations are stored: {}", args[0]);
        LOG.info("The directory where the double data for calculations are stored: {}", args[1]);
        LOG.info("Path for file with calculations is: {}", args[2]);

        AlgorithmsResultCalculator calculator = new AlgorithmsResultCalculator();
        try {
            calculator.makeCalculations(args[0], args[1], args[2]);
        } catch (CalculationException e) {
            LOG.error(e::getCause);
        }

        LOG.info(() -> "Measurements completed");
    }

    private static String[] checkArguments(String[] args) {
        if (args.length < 3) {
            args = new String[3];
            String rootWay = "src" + File.separator + "main" + File.separator + "resources";
            args[0] = rootWay + File.separator + "data" + File.separator + "double";
            args[1] = rootWay + File.separator + "data" + File.separator + "uint";
            args[2] = rootWay + File.separator + "calculations.csv";
        }
        return args;
    }
}
