package algorithms.k2raster;

import algorithms.utils.BitMatrix;
import algorithms.utils.TypeUtils;

public class K2Tree {

    private final K2Node root;
    private final int zMassSize;

    private static int decodeIndex;
    private static int encodeIndex;

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
        return getBitMatrixFromChildren(node.getChildren());
    }

    private BitMatrix getBitMatrixFromChildren(K2Node[] children) {
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
        encodeIndex = 0;
        int byteCount = getSizeInBytesOfTree(k2Tree.getRoot());
        byte[] sizeOfZMassSize = TypeUtils.intToByteArray(k2Tree.getZMassSize());
        byte[] byteArray = new byte[byteCount + sizeOfZMassSize.length];
        for (byte _byte : sizeOfZMassSize) {
            byteArray[encodeIndex++] = _byte;
        }
        serialize(k2Tree.getRoot(), byteArray);

        return byteArray;
    }

    private static int getSizeInBytesOfTree(K2Node k2Node) {
        int byteSize = k2Node.getByteSize();
        K2Node[] children = k2Node.getChildren();
        if (children != null) {
            for (int i = 0; i < 4; i++) {
                byteSize += getSizeInBytesOfTree(children[i]);
            }
        }

        return byteSize;
    }

    private static void serialize(K2Node node, byte[] byteArray) {
        byte[] nodeBytes = node.toBytes();
        for (byte nodeByte : nodeBytes) {
            byteArray[encodeIndex++] = nodeByte;
        }

        K2Node[] children = node.getChildren();
        if (children != null) {
            for (int i = 0; i < 4; i++) {
                serialize(children[i], byteArray);
            }
        }
    }

    public static K2Tree deserialize(byte[] bytes) {
        decodeIndex = 0;
        byte[] sizeOfZCurveInBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            sizeOfZCurveInBytes[i] = bytes[decodeIndex++];
        }
        int sizeOfZCurve = TypeUtils.byteArrayToInt(sizeOfZCurveInBytes);

        byte[] byteSizeOfMatrixSize = new byte[4];
        for (int i = 0; i < 4; i++) {
            byteSizeOfMatrixSize[i] = bytes[decodeIndex++];
        }
        int sizeOfMatrix = TypeUtils.byteArrayToInt(byteSizeOfMatrixSize);

        if (sizeOfMatrix != 0) {
            byte[] sizeOfMatrixSideInBytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                sizeOfMatrixSideInBytes[i] = bytes[decodeIndex++];
            }
            int sizeOfMatrixSide = TypeUtils.byteArrayToInt(sizeOfMatrixSideInBytes);

            byte[] matrixInBytes = new byte[sizeOfMatrix];
            for (int i = 0; i < sizeOfMatrix; i++) {
                matrixInBytes[i] = bytes[decodeIndex++];
            }

            int[] matrix = TypeUtils.byteArrayToIntArray(matrixInBytes);
            boolean min = bytes[decodeIndex++] == 1;
            boolean max = bytes[decodeIndex++] == 1;
            return new K2Tree(
                    new K2Node(
                            min,
                            max,
                            null,
                            new BitMatrix(sizeOfMatrixSide, sizeOfMatrixSide, matrix)
                    ),
                    sizeOfZCurve);
        }

        boolean min = bytes[decodeIndex++] == 1;
        boolean max = bytes[decodeIndex++] == 1;
        K2Tree k2Tree = new K2Tree(new K2Node(min, max), sizeOfZCurve);

        K2Node[] children = k2Tree.getRoot().getChildren();
        for (int i = 0; i < 4; i++) {
            children[i] = deserializeRec(bytes);
        }

        return k2Tree;
    }

    private static K2Node deserializeRec(byte[] bytes) {
        byte[] byteSizeOfMatrixSize = new byte[4];
        for (int i = 0; i < 4; i++) {
            byteSizeOfMatrixSize[i] = bytes[decodeIndex++];
        }
        int sizeOfMatrix = TypeUtils.byteArrayToInt(byteSizeOfMatrixSize);

        if (sizeOfMatrix != 0) {
            byte[] sizeOfMatrixSideInBytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                sizeOfMatrixSideInBytes[i] = bytes[decodeIndex++];
            }
            int sizeOfMatrixSide = TypeUtils.byteArrayToInt(sizeOfMatrixSideInBytes);

            byte[] matrixInBytes = new byte[sizeOfMatrix];
            for (int i = 0; i < sizeOfMatrix; i++) {
                matrixInBytes[i] = bytes[decodeIndex++];
            }

            int[] matrix = TypeUtils.byteArrayToIntArray(matrixInBytes);
            boolean min = bytes[decodeIndex++] == 1;
            boolean max = bytes[decodeIndex++] == 1;
            return new K2Node(
                    min,
                    max,
                    null,
                    new BitMatrix(sizeOfMatrixSide, sizeOfMatrixSide, matrix
                    )
            );
        }

        boolean min = bytes[decodeIndex++] == 1;
        boolean max = bytes[decodeIndex++] == 1;

        K2Node node = new K2Node(min, max);

        K2Node[] children = node.getChildren();
        for (int i = 0; i < 4; i++) {
            children[i] = deserializeRec(bytes);
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
