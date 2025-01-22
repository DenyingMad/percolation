import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final int n;

    private boolean[][] grid;
    private int openSitesCount = 0;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N " + n + " must be greater than 0");
        }

        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
            uf.union(n * n + 1, n * n + 1 - i);
        }
        grid = new boolean[n][n];
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;

        grid[row - 1][col - 1] = true;
        openSitesCount++;

        if (row > 1 && isOpen(row - 1, col)) { // union with upper above
            uf.union(getIndex(row - 1, col), getIndex(row, col));
        }
        if (row < n && isOpen(row + 1, col)) { // union with row below
            uf.union(getIndex(row + 1, col), getIndex(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) { // union with left cell
            uf.union(getIndex(row, col - 1), getIndex(row, col));
        }
        if (col < n && isOpen(row, col + 1)) { // union with right cell
            uf.union(getIndex(row, col + 1), getIndex(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1] && uf.find(0) == uf.find(getIndex(row, col));
    }

    public int numberOfOpenSites() {
        return this.openSitesCount;
    }

    public boolean percolates() {
        return uf.find(0) == uf.find(n * n + 1);
    }

    private int getIndex(int row, int col) {
        return col + n * (row - 1);
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        int size = 20;
        Percolation percolation = new Percolation(size);


        do {
            percolation.open(StdRandom.uniformInt(1, size + 1), StdRandom.uniformInt(1, size + 1));
        } while (!percolation.percolates());

        System.out.println("Elapsed time = " + stopwatch.elapsedTime() + " open sites = " + percolation.numberOfOpenSites());
    }
}
