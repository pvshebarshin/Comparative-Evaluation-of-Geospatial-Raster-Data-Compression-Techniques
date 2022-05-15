package calculations;

public class Measuring {

    private String name;
    private double ratio;
    private long time;
    private long size;
    private String parameters;
    private String type;

    public Measuring() {
        // It is empty because objects created by builder
    }

    public static Builder newBuilder() {
        return new Measuring().new Builder();
    }

    @Override
    public String toString() {
        return name + ","
                + ratio + ","
                + time + ","
                + size + ","
                + parameters + ","
                + type;
    }

    public class Builder {

        private Builder() {
        }

        public Builder setName(String name) {
            Measuring.this.name = name;
            return this;
        }

        public Builder setTime(long time) {
            Measuring.this.time = time;
            return this;
        }

        public Builder setSize(long size) {
            Measuring.this.size = size;
            return this;
        }

        public Builder setParameters(String parameters) {
            Measuring.this.parameters = parameters;
            return this;
        }

        public Builder setType(String type) {
            Measuring.this.type = type;
            return this;
        }

        public Builder setRatio(double ratio) {
            Measuring.this.ratio = ratio;
            return this;
        }

        public Measuring build() {
            return Measuring.this;
        }
    }
}
