package algorithms.bitgrooming;

public enum NSD {
    NSD1(1),
    NSD2(2),
    NSD3(3),
    NSD4(4),
    NSD5(5),
    NSD6(6),
    NSD7(7),
    NSD8(8),
    NSD9(9),
    NSD10(10),
    NSD11(11),
    NSD12(12),
    NSD13(13),
    NSD14(14),
    NSD15(15);

    private final int value;

    NSD(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
