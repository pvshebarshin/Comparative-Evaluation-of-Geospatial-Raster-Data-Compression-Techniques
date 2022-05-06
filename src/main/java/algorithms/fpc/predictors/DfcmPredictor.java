package algorithms.fpc.predictors;

import java.util.Arrays;

public class DfcmPredictor {

    private final long[] table;
    private int dfcm_hash;
    private long lastValue;

    public DfcmPredictor(int logOfTableSize) {
        table = new long[1 << logOfTableSize];
        Arrays.fill(table, 0);
        dfcm_hash = 0;
        lastValue = 0;
    }

    public long getPrediction() {
        return table[dfcm_hash] + lastValue;
    }

    public void update(long trueValue) {
        table[dfcm_hash] = trueValue - lastValue;
        dfcm_hash = (int) (((dfcm_hash << 2) ^ ((trueValue - lastValue) >> 40)) & (table.length - 1));
        lastValue = trueValue;
    }
}
