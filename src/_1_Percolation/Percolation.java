package _1_Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This class creates a system that
 * presents a n-by-n Grid and then
 * checks this System on percolate
 */
public class Percolation {

    private WeightedQuickUnionUF mWeightedQuickUnionUF;
    private int mSizeOfLineInGrid;

    private boolean mGridElementOpened[][];
    private int mNumberOfOpenSites = 0;
    private int mTopIndex;
    private int mBottomIndex;

    /**
     * create n-by-n grid, with all sites blocked
     *
     * @param n size of grid
     */
    public Percolation(int n) {
        int bottomElement = 1;
        int topElement = 1;
        int sizeOfElements = n * n + bottomElement + topElement;

        mTopIndex = 0;
        mBottomIndex = sizeOfElements - 1;

        mSizeOfLineInGrid = n;
        mWeightedQuickUnionUF = new WeightedQuickUnionUF(sizeOfElements);
        mGridElementOpened = new boolean[n][n];
    }

    /**
     * Unittest-like function
     */
    public static void main(String[] args) {

        Percolation p = new Percolation(1);

        System.out.println(p.percolates() == false);

        p.open(1, 1);

        System.out.println(p.percolates() == true);

        try {
            p.open(1, 2);
        } catch (Exception e) {
            System.out.println(true);
        }

        p = new Percolation(4);

        p.open(1, 1);
        p.open(1, 1);
        p.open(1, 1);

        System.out.println(p.isOpen(1, 1) == true);
        System.out.println(p.isFull(1, 1) == true);

        p.open(2, 1);
        p.open(2, 2);

        System.out.println(p.percolates() == false);
        System.out.println(p.numberOfOpenSites() == 3);

        p.open(3, 1);

        p.open(4, 1);

        System.out.println(p.percolates() == true);


        p = new Percolation(4);

        p.open(1, 1);
        p.open(1, 1);
        p.open(1, 1);

        System.out.println(p.isOpen(1, 3) == false);
        System.out.println(p.isFull(1, 2) == false);

        p.open(2, 1);
        p.open(2, 2);
        System.out.println(p.isFull(2, 2) == true);
        p.open(2, 2);
        p.open(2, 3);

        System.out.println(p.percolates() == false);
        System.out.println(p.numberOfOpenSites() == 4);

        p.open(3, 1);
        p.open(3, 3);

        System.out.println(p.percolates() == false);

        p.open(3, 4);

        System.out.println(p.percolates() == false);

        p.open(4, 4);

        System.out.println(p.percolates() == true);
    }

    /**
     * open site (row, col) if it is not open already
     *
     * @param row row number
     * @param col column number
     */
    public void open(int row, int col) {
        boolean alreadyOpened = false;
        try {
            alreadyOpened = mGridElementOpened[row - 1][col - 1];
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }

        if (!alreadyOpened) {
            mGridElementOpened[row - 1][col - 1] = true;
            mNumberOfOpenSites++;
            connectNeighbors(row, col);
        }

    }

    /**
     * is site (row, col) open?
     *
     * @param row row number
     * @param col column number
     * @return is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        try {
            return mGridElementOpened[row - 1][col - 1];
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }

    /**
     * is site (row, col) full?
     *
     * @param row row number
     * @param col column number
     * @return is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if ((row > mSizeOfLineInGrid) || (col > mSizeOfLineInGrid) || (row < 1)
                || (col < 1)) {
            throw new IndexOutOfBoundsException();
        }
        int linearIndex = matrixIndexToLinearIndex(row, col);

        return mWeightedQuickUnionUF.connected(mTopIndex, linearIndex);

    }

    /**
     * Calculates number of open sites
     *
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return mNumberOfOpenSites;
    }

    /**
     * does the system percolate?
     *
     * @return does the system percolate?
     */
    public boolean percolates() {

        boolean conneted = mWeightedQuickUnionUF.connected(mTopIndex,
                mBottomIndex);

        return conneted;
    }

    private void connectNeighbors(int row, int col) {
        int linearIndex = matrixIndexToLinearIndex(row, col);

        if (inFirstLine(linearIndex)) {
            mWeightedQuickUnionUF.union(linearIndex, mTopIndex);
        } else {
            int indexOfUpperElement = linearIndex - mSizeOfLineInGrid;

            if (isOpen(indexOfUpperElement)) {
                mWeightedQuickUnionUF.union(linearIndex, indexOfUpperElement);
            }
        }

        if (inLastLine(linearIndex)) {
            mWeightedQuickUnionUF.union(linearIndex, mBottomIndex);
        } else {
            int indexOfDownElement = linearIndex + mSizeOfLineInGrid;

            if (isOpen(indexOfDownElement)) {
                mWeightedQuickUnionUF.union(linearIndex, indexOfDownElement);
            }
        }

        int leftElement = linearIndex - 1;
        if (linearIndexToColumnNumber(leftElement) != mSizeOfLineInGrid) {
            if (isOpen(leftElement)) {
                mWeightedQuickUnionUF.union(linearIndex, leftElement);
            }

        }

        int rightElement = linearIndex + 1;
        if (linearIndexToColumnNumber(rightElement) != 1) {
            if (isOpen(rightElement)) {
                mWeightedQuickUnionUF.union(linearIndex, rightElement);
            }
        }
    }

    private boolean isOpen(int linearIndex) {
        int rowNumber = linearIndexToRowNumber(linearIndex);
        int colNumber = linearIndexToColumnNumber(linearIndex);

        return isOpen(rowNumber, colNumber);
    }

    private int linearIndexToRowNumber(int linearIndex) {
        return (int) Math.ceil(((double) linearIndex) / mSizeOfLineInGrid);
    }

    private int linearIndexToColumnNumber(int linearIndex) {
        int rowNumber = linearIndexToRowNumber(linearIndex);
        return linearIndex - (rowNumber - 1) * mSizeOfLineInGrid;
    }

    private int matrixIndexToLinearIndex(int row, int col) {
        return (row - 1) * mSizeOfLineInGrid + col;
    }

    private boolean inFirstLine(int linearIndex) {
        return linearIndex <= mSizeOfLineInGrid;
    }

    private boolean inLastLine(int linearIndex) {
        return linearIndex > mSizeOfLineInGrid * (mSizeOfLineInGrid - 1);
    }
}