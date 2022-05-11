package algorithms.k2raster;

import algorithms.utils.BitMatrix;
import algorithms.utils.TypeUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class K2Tree {

    private final K2Node root;
    private final int zMassSize;

    public K2Tree(BitMatrix matrix, int zMassSize) {
        root = new K2Node(null, matrix);
        this.zMassSize = zMassSize;
    }

    private K2Tree(K2Node root, int zMassSize) {
        this.root = root;
        this.zMassSize = zMassSize;
    }

    public K2Node getRoot() {
        return root;
    }

    public BitMatrix toMatrix() {
        if (root.getMatrix() != null) {
            return root.getMatrix();
        }
        return toMatrix(root);
    }

    private BitMatrix toMatrix(K2Node node) {
        if (node.getMatrix() != null) {
            return node.getMatrix();
        }

        K2Node[] children = node.getChildren();
        BitMatrix[] matrices = new BitMatrix[4];
        for (int i = 0; i < 4; i++) {
            matrices[i] = toMatrix(children[i]);
        }

        int size = 2 * matrices[0].getWidth();
        BitMatrix result = new BitMatrix(size, size);

        for (int i = 0; i < size / 2; i++) {
            for (int j = 0; j < size / 2; j++) {
                if (matrices[0].get(i, j)) {
                    result.set(i, j);
                }
            }
        }

        for (int i = 0; i < size / 2; i++) {
            for (int j = size / 2; j < size; j++) {
                if (matrices[1].get(i, j - size / 2)) {
                    result.set(i, j);
                }
            }
        }

        for (int i = size / 2; i < size; i++) {
            for (int j = 0; j < size / 2; j++) {
                if (matrices[2].get(i - size / 2, j)) {
                    result.set(i, j);
                }
            }
        }

        for (int i = size / 2; i < size; i++) {
            for (int j = size / 2; j < size; j++) {
                if (matrices[3].get(i - size / 2, j - size / 2)) {
                    result.set(i, j);
                }
            }
        }

        return result;
    }

    public static byte[] serialize(K2Tree k2Tree) {
        List<Byte> byteArray = new ArrayList<>();
        byte[] sizeOfZMassSize = TypeUtils.intToByteArray(k2Tree.getZMassSize());
        for (byte _byte : sizeOfZMassSize) {
            byteArray.add(_byte);
        }
        serialize(k2Tree.getRoot(), byteArray);

        return ArrayUtils.toPrimitive(byteArray.toArray(new Byte[0]));
    }

    private static void serialize(K2Node node, List<Byte> byteArray) {
        byte[] nodeBytes = node.toBytes();
        for (byte nodeByte : nodeBytes) {
            byteArray.add(nodeByte);
        }

        K2Node[] children = node.getChildren();
        if (children != null) {
            for (int i = 0; i < 4; i++) {
                serialize(children[i], byteArray);
            }
        }
    }

    public static K2Tree deserialize(byte[] byteArray) {
        Deque<Byte> bytes = new ArrayDeque<>();
        for (byte value : byteArray) {
            bytes.add(value);
        }

        byte[] sizeOfZCurveInBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            sizeOfZCurveInBytes[i] = bytes.pollFirst();
        }
        int sizeOfZCurve = TypeUtils.byteArrayToInt(sizeOfZCurveInBytes);

        byte[] byteSizeOfMatrixSize = new byte[4];
        for (int i = 0; i < 4; i++) {
            byteSizeOfMatrixSize[i] = bytes.pollFirst();
        }
        int sizeOfMatrix = TypeUtils.byteArrayToInt(byteSizeOfMatrixSize);

        if (sizeOfMatrix != 0) {
            byte[] sizeOfMatrixSideInBytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                sizeOfMatrixSideInBytes[i] = bytes.pollFirst();
            }
            int sizeOfMatrixSide = TypeUtils.byteArrayToInt(sizeOfMatrixSideInBytes);

            byte[] matrixInBytes = new byte[sizeOfMatrix];
            for (int i = 0; i < sizeOfMatrix; i++) {
                matrixInBytes[i] = bytes.pollFirst();
            }

            int[] matrix = TypeUtils.byteArrayToIntArray(matrixInBytes);
            boolean min = bytes.pollFirst() == 1;
            boolean max = bytes.pollFirst() == 1;
            return new K2Tree(
                    new K2Node(
                            min,
                            max,
                            null,
                            new BitMatrix(sizeOfMatrixSide, sizeOfMatrixSide, matrix)
                    ),
                    sizeOfZCurve);
        }

        boolean min = bytes.pollFirst() == 1;
        boolean max = bytes.pollFirst() == 1;
        K2Tree k2Tree = new K2Tree(new K2Node(min, max), sizeOfZCurve);

        K2Node[] children = k2Tree.getRoot().getChildren();
        for (int i = 0; i < 4; i++) {
            children[i] = deserialize(bytes);
        }

        return k2Tree;
    }

    private static K2Node deserialize(Deque<Byte> bytes) {
        byte[] byteSizeOfMatrixSize = new byte[4];
        for (int i = 0; i < 4; i++) {
            byteSizeOfMatrixSize[i] = bytes.pollFirst();
        }
        int sizeOfMatrix = TypeUtils.byteArrayToInt(byteSizeOfMatrixSize);

        if (sizeOfMatrix != 0) {
            byte[] sizeOfMatrixSideInBytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                sizeOfMatrixSideInBytes[i] = bytes.pollFirst();
            }
            int sizeOfMatrixSide = TypeUtils.byteArrayToInt(sizeOfMatrixSideInBytes);

            byte[] matrixInBytes = new byte[sizeOfMatrix];
            for (int i = 0; i < sizeOfMatrix; i++) {
                matrixInBytes[i] = bytes.pollFirst();
            }

            int[] matrix = TypeUtils.byteArrayToIntArray(matrixInBytes);
            boolean min = bytes.pollFirst() == 1;
            boolean max = bytes.pollFirst() == 1;
            return new K2Node(
                    min,
                    max,
                    null,
                    new BitMatrix(sizeOfMatrixSide, sizeOfMatrixSide, matrix
                    )
            );
        }

        boolean min = bytes.pollFirst() == 1;
        boolean max = bytes.pollFirst() == 1;

        K2Node node = new K2Node(min, max);

        K2Node[] children = node.getChildren();
        for (int i = 0; i < 4; i++) {
            children[i] = deserialize(bytes);
        }

        return node;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        K2Tree k2Tree = (K2Tree) obj;
        return hashCode() == k2Tree.hashCode();
    }

    @Override
    public int hashCode() {
        return root.hashCode();
    }

    public int getZMassSize() {
        return zMassSize;
    }
}
