import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Scanner;

class PercolationStats {

    private double[] trialsThreshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        trialsThreshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            trialsThreshold[i] = execute(n);
        }
    }

    private double execute(int n) {
        Percolation percolation = new Percolation(n);
        do {
            percolation.open(StdRandom.uniformInt(1, n + 1), StdRandom.uniformInt(1, n + 1));
        } while (!percolation.percolates());

        return (double) percolation.numberOfOpenSites() / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.trialsThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.trialsThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / trialsThreshold.length;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / trialsThreshold.length;
    }

    // test client (see below)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PercolationStats stats = new PercolationStats(sc.nextInt(), sc.nextInt());

        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}