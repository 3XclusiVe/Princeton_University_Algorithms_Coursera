package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by user on 04.06.17.
 */

public class PercolationStats {

    private double mPercolationThreshold[];
    private int mOpenedSites = 0;
    private int mExperementsNumber;

    /**
     * perform trials independent experiments on an n-by-n grid
     *
     * @param sizeOfGrid size of n-by-n grid
     * @param trials     number of experiments
     */
    public PercolationStats(int sizeOfGrid, int trials) {
        if ((trials <= 0) || (sizeOfGrid <= 0)) {
            throw new IllegalArgumentException();
        }

        mPercolationThreshold = new double[trials];
        mExperementsNumber = trials;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(sizeOfGrid);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, sizeOfGrid + 1);
                int col = StdRandom.uniform(1, sizeOfGrid + 1);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    mOpenedSites++;
                }
            }

            mPercolationThreshold[i] = (double) mOpenedSites / (sizeOfGrid * sizeOfGrid);

            mOpenedSites = 0;

        }
    }

    /**
     * calculates mean of percolation threshold
     *
     * @return mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(mPercolationThreshold);
    }

    // sample mean of percolation threshold

    /**
     * calculates standard deviation of percolation threshold
     *
     * @return standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(mPercolationThreshold);
    }

    /**
     * caclculates low  endpoint of 95% confidence interval
     *
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(mExperementsNumber));
    }

    /**
     * calculates high endpoint of 95% confidence interval
     *
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(mExperementsNumber));
    }

    public static void main(String[] args) {

        int sizeOfGrid = Integer.parseInt(args[0]);
        int numberOfExperements = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(sizeOfGrid, numberOfExperements);


        String confidence = percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi();
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}