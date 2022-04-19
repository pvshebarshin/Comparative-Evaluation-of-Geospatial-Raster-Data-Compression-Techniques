package calculations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class LaunchCalculations {

    private static final Logger LOG = LogManager.getLogger(LaunchCalculations.class);

    public static void main(String[] args) throws DataFormatException, IOException {
        AlgorithmsResultCalculator calculator = new AlgorithmsResultCalculator();
        try {
            calculator.makeCalculations("src\\main\\resources\\data");
        } catch (IOException | DataFormatException | CalculationException e) {
            LOG.error(e::getCause);
        }
        LOG.info(() -> "Measurements completed");
    }
}
