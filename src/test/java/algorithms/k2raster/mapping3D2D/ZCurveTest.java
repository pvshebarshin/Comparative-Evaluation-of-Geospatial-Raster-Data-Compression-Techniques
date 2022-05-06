package algorithms.k2raster.mapping3D2D;

import algorithms.utils.mapping3D2D.ZCurve;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ZCurveTest {

    protected ZCurve zCurve = new ZCurve();

    @Test
    public void notThrowEncodeTest() {
        Assertions.assertDoesNotThrow(() -> {
            for (int i = 0; i < 1024; i++) {
                int x = (int) (Math.random() * 16777216);
                int y = (int) (Math.random() * 16777216);
                zCurve.encode(x, y);
            }
        });
    }

    @Test
    public void encodeTest1() {
        Assertions.assertEquals(0, zCurve.encode(0, 0));
        Assertions.assertEquals(2, zCurve.encode(0, 1));
        Assertions.assertEquals(1, zCurve.encode(1, 0));
        Assertions.assertEquals(95, zCurve.encode(15, 3));
        Assertions.assertEquals(175, zCurve.encode(3, 15));
        Assertions.assertEquals(85, zCurve.encode(15, 0));
        Assertions.assertEquals(170, zCurve.encode(0, 15));
        Assertions.assertEquals(165, zCurve.encode(3, 12));
        Assertions.assertEquals(2152, zCurve.encode(8, 38));
        Assertions.assertEquals(15176, zCurve.encode(88, 114));
        Assertions.assertEquals(6473, zCurve.encode(89, 34));
        Assertions.assertEquals(12998, zCurve.encode(74, 89));
        Assertions.assertEquals(2623, zCurve.encode(7, 55));
        Assertions.assertEquals(3067, zCurve.encode(29, 63));
        Assertions.assertEquals(5340, zCurve.encode(110, 10));
        Assertions.assertEquals(11860, zCurve.encode(46, 112));
        Assertions.assertEquals(2966, zCurve.encode(22, 57));
        Assertions.assertEquals(1778, zCurve.encode(44, 29));
        Assertions.assertEquals(14472, zCurve.encode(64, 106));
    }

    @Test
    public void encodeTest2() {
        Assertions.assertEquals(4000, zCurve.encode(48, 60));
        Assertions.assertEquals(15732, zCurve.encode(126, 100));
        Assertions.assertEquals(6948, zCurve.encode(82, 52));
        Assertions.assertEquals(11435, zCurve.encode(33, 111));
        Assertions.assertEquals(3028, zCurve.encode(30, 56));
        Assertions.assertEquals(3504, zCurve.encode(52, 44));
        Assertions.assertEquals(10009, zCurve.encode(53, 82));
        Assertions.assertEquals(10325, zCurve.encode(15, 96));
        Assertions.assertEquals(5040, zCurve.encode(84, 28));
        Assertions.assertEquals(3977, zCurve.encode(49, 58));
        Assertions.assertEquals(5544, zCurve.encode(112, 14));
        Assertions.assertEquals(27177448, zCurve.encode(5720, 3022));
        Assertions.assertEquals(631981749, zCurve.encode(12711, 20252));
        Assertions.assertEquals(513589087, zCurve.encode(26239, 15091));
        Assertions.assertEquals(226270114, zCurve.encode(15952, 9917));
        Assertions.assertEquals(980865790, zCurve.encode(20174, 30111));
        Assertions.assertEquals(302577852, zCurve.encode(16582, 4846));
        Assertions.assertEquals(11963322, zCurve.encode(1556, 3519));
    }

    @Test
    public void encodeTest3() {
        Assertions.assertEquals(76333871, zCurve.encode(8851, 2711));
        Assertions.assertEquals(913775838, zCurve.encode(28526, 21803));
        Assertions.assertEquals(148082287, zCurve.encode(3371, 10679));
        Assertions.assertEquals(632190678, zCurve.encode(13038, 20313));
        Assertions.assertEquals(962231521, zCurve.encode(23785, 25452));
        Assertions.assertEquals(454776292, zCurve.encode(22010, 13068));
        Assertions.assertEquals(345125974, zCurve.encode(25710, 2369));
        Assertions.assertEquals(256517481, zCurve.encode(14393, 13126));
        Assertions.assertEquals(465367871, zCurve.encode(22231, 16087));
        Assertions.assertEquals(285974738, zCurve.encode(20748, 969));
        Assertions.assertEquals(811863312, zCurve.encode(18996, 17440));
        Assertions.assertEquals(30426780, zCurve.encode(7334, 2074));
        Assertions.assertEquals(1762101, zCurve.encode(1175, 980));
        Assertions.assertEquals(467026154, zCurve.encode(24200, 14607));
        Assertions.assertEquals(694605942, zCurve.encode(6862, 26021));
        Assertions.assertEquals(933610681, zCurve.encode(29605, 23694));
        Assertions.assertEquals(104371913, zCurve.encode(9321, 5786));
        Assertions.assertEquals(554568978, zCurve.encode(4660, 17185));
        Assertions.assertEquals(141329791, zCurve.encode(2623, 9863));
    }

    @Test
    public void notThrowDecodeTest() {
        Assertions.assertDoesNotThrow(() -> {
            for (int i = 0; i < 1024; i++) {
                int c = (int) (Math.random() * 141329791);
                zCurve.decode(c);
            }
        });
    }

    @Test
    public void decodeTest1() {
        Assertions.assertArrayEquals(new int[]{2623, 9863}, zCurve.decode(141329791));
        Assertions.assertArrayEquals(new int[]{66, 16480845}, zCurve.decode(187510395777190L));
        Assertions.assertArrayEquals(new int[]{13, 3912493}, zCurve.decode(11590137678067L));
        Assertions.assertArrayEquals(new int[]{56, 3465049}, zCurve.decode(11135284291522L));
        Assertions.assertArrayEquals(new int[]{25, 12556034}, zCurve.decode(152464932864329L));
        Assertions.assertArrayEquals(new int[]{41, 15499121}, zCurve.decode(185405864029763L));
        Assertions.assertArrayEquals(new int[]{81, 3372671}, zCurve.decode(11038773230507L));
        Assertions.assertArrayEquals(new int[]{84, 7229009}, zCurve.decode(44702567510802L));
        Assertions.assertArrayEquals(new int[]{107, 10776107}, zCurve.decode(149671702437071L));
        Assertions.assertArrayEquals(new int[]{30, 11770156}, zCurve.decode(151777743866356L));
        Assertions.assertArrayEquals(new int[]{2964607, 111}, zCurve.decode(4746028006911L));
        Assertions.assertArrayEquals(new int[]{9316506, 5}, zCurve.decode(70729592750438L));
        Assertions.assertArrayEquals(new int[]{13056202, 48}, zCurve.decode(88051212638788L));
        Assertions.assertArrayEquals(new int[]{16450255, 58}, zCurve.decode(93754841389789L));
        Assertions.assertArrayEquals(new int[]{9677308, 62}, zCurve.decode(71490875777016L));
        Assertions.assertArrayEquals(new int[]{9560250, 28}, zCurve.decode(71473960077284L));
        Assertions.assertArrayEquals(new int[]{10834378, 78}, zCurve.decode(74840090439916L));
        Assertions.assertArrayEquals(new int[]{11029506, 11}, zCurve.decode(75041942274190L));
        Assertions.assertArrayEquals(new int[]{3524130, 49}, zCurve.decode(5571916074502L));
        Assertions.assertArrayEquals(new int[]{9494756, 78}, zCurve.decode(71469665121464L));
    }

    @Test
    public void decodeTest2() {
        Assertions.assertArrayEquals(new int[]{14853686, 7}, zCurve.decode(92377298765118L));
        Assertions.assertArrayEquals(new int[]{15308712, 113}, zCurve.decode(92639241530946L));
        Assertions.assertArrayEquals(new int[]{2738756, 79}, zCurve.decode(4678566031546L));
        Assertions.assertArrayEquals(new int[]{12882385, 32}, zCurve.decode(88030740306177L));
        Assertions.assertArrayEquals(new int[]{30038, 11}, zCurve.decode(353440158L));
        Assertions.assertArrayEquals(new int[]{6750500, 61}, zCurve.decode(22080426938034L));
        Assertions.assertArrayEquals(new int[]{15981812, 23}, zCurve.decode(93481327417146L));
        Assertions.assertArrayEquals(new int[]{1578817, 79}, zCurve.decode(1374407700651L));
        Assertions.assertArrayEquals(new int[]{48742, 106}, zCurve.decode(1163148444L));
        Assertions.assertArrayEquals(new int[]{14470076, 97}, zCurve.decode(89405385960786L));
        Assertions.assertArrayEquals(new int[]{10981191, 87}, zCurve.decode(74858064327231L));
        Assertions.assertArrayEquals(new int[]{5830444, 101}, zCurve.decode(18968003030130L));
        Assertions.assertArrayEquals(new int[]{8819208, 110}, zCurve.decode(70455734315240L));
        Assertions.assertArrayEquals(new int[]{3529191, 58}, zCurve.decode(5571935821469L));
        Assertions.assertArrayEquals(new int[]{11118716, 58}, zCurve.decode(75047108616152L));
        Assertions.assertArrayEquals(new int[]{15457602, 5}, zCurve.decode(92656693743654L));
        Assertions.assertArrayEquals(new int[]{2924808, 0}, zCurve.decode(4742784811072L));
        Assertions.assertArrayEquals(new int[]{9155980, 30}, zCurve.decode(70666255680248L));
        Assertions.assertArrayEquals(new int[]{10055619, 60}, zCurve.decode(71747769817765L));
        Assertions.assertArrayEquals(new int[]{1456684, 34}, zCurve.decode(1185499319384L));
        Assertions.assertArrayEquals(new int[]{3564671, 0}, zCurve.decode(5583794083157L));
        Assertions.assertArrayEquals(new int[]{1831415, 85}, zCurve.decode(1397290530615L));
        Assertions.assertArrayEquals(new int[]{2380129, 45}, zCurve.decode(4467051273379L));
        Assertions.assertArrayEquals(new int[]{5128011, 14}, zCurve.decode(17953052758253L));
    }
}
