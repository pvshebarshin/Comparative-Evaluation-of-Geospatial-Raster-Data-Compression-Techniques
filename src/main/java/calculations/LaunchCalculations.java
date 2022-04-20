package calculations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class LaunchCalculations {

    private static final Logger LOG = LogManager.getLogger(LaunchCalculations.class);

    public static void main(String[] args) throws DataFormatException, IOException {
        LOG.info(() -> "The directory where the data for calculations are stored: " + args[0]);
        LOG.info(() -> "Path for file with calculations is: " + args[1]);

        AlgorithmsResultCalculator calculator = new AlgorithmsResultCalculator();
        try {
            calculator.makeCalculations(args[0], args[1]);
        } catch (IOException | DataFormatException | CalculationException e) {
            LOG.error(e::getCause);
        }

        LOG.info(() -> "Measurements completed");
    }
}
