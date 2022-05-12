package algorithms.k2raster;

import algorithms.utils.BitMatrix;
import algorithms.utils.TypeUtils;

public class K2Node {
    private boolean min;
    private boolean max;
    private K2Node parent;
    private K2Node[] children;
    private BitMatrix matrix;

    public K2Node(boolean min, boolean max) {
        this.min = min;
        this.max = max;
        children = new K2Node[4];
    }

    public K2Node(boolean min, boolean max, K2Node parent, BitMatrix matrix) {
        this.min = min;
        this.max = max;
        this.parent = parent;
        this.matrix = matrix;
    }

    public K2Node(K2Node parent, BitMatrix matrix) {
        this.parent = parent;
        int size = matrix.getWidth();

        max = min = matrix.get(0, 0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix.get(i, j)) {
                    max = true;
                } else {
                    min = false;
                }

                if (max != min) {
                    break;
                }
            }
        }

        if (max != min && size > 2) {
            makeChildren(matrix, size);
        } else {
            this.matrix = matrix;
        }
    }

    private void makeChildren(BitMatrix matrix, int size) {
        children = new K2Node[4];
        int i;
        int j;
        BitMatrix childMatrix = new BitMatrix(size / 2, size / 2);
        for (i = 0; i < size / 2; i++) {
            for (j = 0; j < size / 2; j++) {
                if (matrix.get(i, j)) {
                    childMatrix.set(i, j);
                }
            }
        }
        children[0] = new K2Node(this, childMatrix);

        childMatrix = new BitMatrix(size / 2, size / 2);
        for (i = 0; i < size / 2; i++) {
            for (j = size / 2; j < size; j++) {
                if (matrix.get(i, j)) {
                    childMatrix.set(i, j - size / 2);
                }
            }
        }
        children[1] = new K2Node(this, childMatrix);

        childMatrix = new BitMatrix(size / 2, size / 2);
        for (i = size / 2; i < size; i++) {
            for (j = 0; j < size / 2; j++) {
                if (matrix.get(i, j)) {
                    childMatrix.set(i - size / 2, j);
                }
            }
        }
        children[2] = new K2Node(this, childMatrix);

        childMatrix = new BitMatrix(size / 2, size / 2);
        for (i = size / 2; i < size; i++) {
            for (j = size / 2; j < size; j++) {
                if (matrix.get(i, j)) {
                    childMatrix.set(i - size / 2, j - size / 2);
                }
            }
        }
        children[3] = new K2Node(this, childMatrix);
    }

    public byte[] toBytes() {
        byte[] sizeOfMatrixInByteArray;
        byte[] bytes;
        int i;
        if (matrix != null) {
            byte[] matrixByteArray = TypeUtils.intArrayToByteArray(matrix.getData());
            sizeOfMatrixInByteArray = TypeUtils.intToByteArray(matrix.getData().length * 4);

            byte[] sizeMatrixSide = TypeUtils.intToByteArray(matrix.getWidth());

            bytes = new byte[matrixByteArray.length + 2
                    + sizeOfMatrixInByteArray.length + sizeMatrixSide.length];

            for (i = 0; i < sizeOfMatrixInByteArray.length; i++) {
                bytes[i] = sizeOfMatrixInByteArray[i];
            }

            for (i = 0; i < sizeMatrixSide.length; i++) {
                bytes[i + sizeOfMatrixInByteArray.length] = sizeMatrixSide[i];
            }

            for (i = 0; i < matrixByteArray.length; i++) {
                bytes[i + sizeOfMatrixInByteArray.length + sizeMatrixSide.length] = matrixByteArray[i];
            }
        } else {
            sizeOfMatrixInByteArray = TypeUtils.intToByteArray(0);
            bytes = new byte[2 + sizeOfMatrixInByteArray.length];

            for (i = 0; i < sizeOfMatrixInByteArray.length; i++) {
                bytes[i] = sizeOfMatrixInByteArray[i];
            }
        }

        bytes[bytes.length - 2] = (byte) (min ? 1 : 0);
        bytes[bytes.length - 1] = (byte) (max ? 1 : 0);

        return bytes;
    }

    public int getByteSize() {
        if (matrix != null) {
            return matrix.getData().length * 4 + 10;
        } else {
            return 6;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        K2Node k2Node = (K2Node) obj;
        return this.hashCode() == k2Node.hashCode();
    }

    @Override
    public int hashCode() {
        int result = 31 * (min ? 1 : 0) + 32 * (max ? 1 : 0) + (matrix == null ? 0 : matrix.hashCode());
        if (children != null) {
            for (int i = 0; i < 4; i++) {
                result += children[i].hashCode();
            }
        }
        return result;
    }

    public BitMatrix getMatrix() {
        return matrix;
    }

    public K2Node[] getChildren() {
        return children;
    }
}
