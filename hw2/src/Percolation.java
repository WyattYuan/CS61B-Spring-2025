import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufForIsFull;
    boolean[][] openMatrix;  // track every site's open state
    private final int N;
    private int numberOfOpenSites;
    private final int source;
    private final int target;

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }


    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufForIsFull = new WeightedQuickUnionUF(N * N + 1);

        openMatrix = new boolean[N][N];
        this.N = N;
        numberOfOpenSites = 0;
        source = N * N; // represent the water source
        target = source + 1; // represent the water target

    }

    public void open(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        // data track
        openMatrix[row][col] = true;
        numberOfOpenSites += 1;
        // if top site, connect to source
        int currentID = getID(row, col);
        if (row == 0) {
            uf.union(currentID, source);
            ufForIsFull.union(currentID, source);
        }
        if (row == N - 1) {
            uf.union(currentID, target);
        }

        // connect to 4 neighbors: when the neighbor is open, connect to it.
        Direction[] directions = {Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};
        for (Direction dir : directions) {
            if (isOpen(row, col, dir)) {
                uf.union(currentID, getID(row, col, dir));
                ufForIsFull.union(currentID, getID(row, col, dir));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return openMatrix[row][col];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && ufForIsFull.connected(source, getID(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return uf.connected(source, target);
    }

    private boolean isOpen(int row, int col, Direction n) {
        switch (n) {
            case LEFT -> {
                if (col <= 0) return false;
                return openMatrix[row][col - 1];
            }
            case RIGHT -> {
                if (col >= N - 1) return false;
                return openMatrix[row][col + 1];
            }
            case UP -> {
                if (row <= 0) return false;
                return openMatrix[row - 1][col];
            }
            case DOWN -> {
                if (row >= N - 1) return false;
                return openMatrix[row + 1][col];
            }
            case null, default -> {
                return false;
            }
        }
    }

    private int getID(int row, int col) {
        return row * N + col;
    }

    private int getID(int row, int col, Direction n) {
        switch (n) {
            case LEFT -> {
                if (col <= 0) return -1;
                return row * N + col - 1;
            }
            case RIGHT -> {
                if (col >= N - 1) return -1;
                return row * N + col + 1;
            }
            case UP -> {
                if (row <= 0) return -1;
                return (row - 1) * N + col;
            }
            case DOWN -> {
                if (row >= N - 1) return -1;
                return (row + 1) * N + col;
            }
            case null, default -> {
                return -1;
            }
        }
    }


}
