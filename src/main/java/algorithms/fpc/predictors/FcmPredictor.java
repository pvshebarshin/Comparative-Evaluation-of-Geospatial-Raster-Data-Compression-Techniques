package algorithms.fpc.predictors;

import java.util.Arrays;

public class FcmPredictor {
    private final long[] table;
    private int fcmHash;

    public FcmPredictor(int logOfTableSize) {
        table = new long[1 << logOfTableSize];
        Arrays.fill(table, 0);
        fcmHash = 0;
    }

    public long getPrediction() {
        return table[fcmHash];
    }

    public void update(long trueValue) {
        table[fcmHash] = trueValue;
        fcmHash = (int) (((fcmHash << 6) ^ (trueValue >> 48)) & (table.length - 1));
    }
}
