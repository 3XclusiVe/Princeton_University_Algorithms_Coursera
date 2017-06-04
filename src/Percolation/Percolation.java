package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import static java.lang.Math.ceil;

public class Percolation {

    private WeightedQuickUnionUF mWeightedQuickUnionUF;
    private int mSizeOfLineInGrid;

    private boolean mGridElementOpened[][];
    private int mNumberOfOpenSites = 0;

    /**
     * create n-by-n grid, with all sites blocked
     * @param n size of grid
     */
    public Percolation(int n)     {
        int bottomElement = 1;
        int topElement = 1;
        int sizeOfElements = n * n + bottomElement + topElement;

        mSizeOfLineInGrid = n;
        mWeightedQuickUnionUF = new WeightedQuickUnionUF(sizeOfElements);
        mGridElementOpened = new boolean[n][n];
    }


    /**
     * open site (row, col) if it is not open already
     * @param row row number
     * @param col column number
     */
    public void open(int row, int col) {
        boolean alreadyOpened = false;
        try {
            alreadyOpened = mGridElementOpened[row - 1][col - 1];
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if(!alreadyOpened) {
            mGridElementOpened[row - 1][col - 1] = true;
            mNumberOfOpenSites++;
            connectNeighbors(row, col);
        }

    }

    /**
     * is site (row, col) open?
     * @param row row number
     * @param col column number
     * @return is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        try {
            return mGridElementOpened[row - 1][col - 1];
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    /**
     * is site (row, col) full?
     * @param row row number
     * @param col column number
     * @return is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        try {
            return !mGridElementOpened[row - 1][col - 1];
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Calculates number of open sites
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return mNumberOfOpenSites;
    }

    /**
     * does the system percolate?
     * @return does the system percolate?
     */
    public boolean percolates() {

        boolean  conneted = mWeightedQuickUnionUF.connected(getTopIndex(), getBottomIndex());

        return conneted;
    }

    private void connectNeighbors(int row, int col) {
        int linearIndex = matrixIndexToLinearIndex(row, col);

        if (inFirstLine(linearIndex)) {
            mWeightedQuickUnionUF.union(linearIndex, getTopIndex());
        } else {
            int IndexOfUpperElement = linearIndex - mSizeOfLineInGrid;

            if(isOpen(IndexOfUpperElement)) {
                mWeightedQuickUnionUF.union(linearIndex, IndexOfUpperElement);
            }
        }

        if(inLastLine(linearIndex)) {
            mWeightedQuickUnionUF.union(linearIndex, getBottomIndex());
        } else {
            int IndexOfDownElement = linearIndex + mSizeOfLineInGrid;

            if(isOpen(IndexOfDownElement)) {
                mWeightedQuickUnionUF.union(linearIndex, IndexOfDownElement);
            }
        }

        int LeftElement = linearIndex - 1;
        if( linearIndexToColumnNumber(LeftElement) != mSizeOfLineInGrid) {
            mWeightedQuickUnionUF.union(linearIndex, LeftElement);
        }

        int RightElement = linearIndex + 1;
        if(linearIndexToColumnNumber(RightElement) != 1) {
            mWeightedQuickUnionUF.union(linearIndex, RightElement);
        }
    }

    private boolean isOpen(int linearIndex) {
        int rowNumber = linearIndexToRowNumber(linearIndex);
        int colNumber = linearIndexToColumnNumber (linearIndex);

        return isOpen(rowNumber, colNumber);
    }

    private int linearIndexToRowNumber(int linearIndex) {
        return (int) ceil(((double) linearIndex) / mSizeOfLineInGrid);
    }

    private int linearIndexToColumnNumber (int linearIndex) {
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




    private int getBottomIndex() {
        return mWeightedQuickUnionUF.count() - 1;
    }

    private int getTopIndex() {
        return 0;
    }

    private void ConnectBottomAndLastLineOfGrid() {
        int Botom = getBottomIndex();

        int indexOfLastElementInLastLine = Botom - 1;
        int indexOfFirstElementInLastLine = indexOfLastElementInLastLine - mSizeOfLineInGrid + 1;

        for(int i = indexOfFirstElementInLastLine; i <= indexOfLastElementInLastLine; i++) {
            mWeightedQuickUnionUF.union(Botom, i);
        }
    }

    private void ConnectTopAndFirstLineOfGrid() {
        int Top = getTopIndex();

        int indexOfFirstElementInFirstLine = Top + 1;
        int indexOfLastElementInFirstLine = indexOfFirstElementInFirstLine + mSizeOfLineInGrid - 1;

        for(int i = indexOfFirstElementInFirstLine; i <= indexOfLastElementInFirstLine; i++) {
            mWeightedQuickUnionUF.union(Top, i);
        }

    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);

        p.open(1,3);
        p.open(1,1);
        //p.open(1,2);
        p.open(2,2);
        p.open(2,2);
        //p.open(2,1);
        p.open(3,2);



        System.out.println(p.percolates());
    }
}