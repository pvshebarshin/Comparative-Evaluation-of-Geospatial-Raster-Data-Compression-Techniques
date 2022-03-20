package calculations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class LaunchCalculations {

    private static final Logger LOG = LogManager.getLogger(LaunchCalculations.class);

    public static void main(String[] args) {
        AlgorithmsResultCalculator calculator = new AlgorithmsResultCalculator();
        try {
            calculator.makeCalculations("resources/data");
        } catch (IOException | DataFormatException e) {
            LOG.error(e.getMessage());
        }
    }
}
