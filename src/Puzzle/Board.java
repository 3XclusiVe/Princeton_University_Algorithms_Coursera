package Puzzle;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {

    private int[] mBlock;
    private int mBoardSize;
    private int mEmptyBlockIndex;

    private class Neighbor {
        private boolean UP = true;
        private boolean RIGHT = true;
        private boolean DOWN = true;
        private boolean LEFT = true;
    }

    private Neighbor mNeighbor = new Neighbor();

    /**
     * construct a board from an n-by-n
     * array of blocks
     *
     * @param blocks (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {

        int fieldSize = blocks.length * blocks[0].length;
        mBoardSize = blocks.length;
        mBlock = new int[fieldSize];

        int index = 0;
        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks[row].length; column++) {
                if (blocks[row][column] == 0) {
                    mEmptyBlockIndex = index;
                    if (row == 0) {
                        mNeighbor.UP = false;
                    }
                    if (row == mBoardSize - 1) {
                        mNeighbor.DOWN = false;
                    }
                    if (column == 0) {
                        mNeighbor.LEFT = false;
                    }
                    if (column == mBoardSize - 1) {
                        mNeighbor.RIGHT = false;
                    }
                }
                mBlock[index] = blocks[row][column];
                index++;
            }
        }

    }

    /**
     * @return board dimension n
     */
    public int dimension() {
        return mBoardSize;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {
        int correctNumber = 1;
        int hammingFunction = 0;

        for (int i = 0; i < mBlock.length - 1; i++) {
            if (mBlock[i] != correctNumber) {
                hammingFunction++;
            }
            correctNumber++;
        }

        return hammingFunction;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return sum distances between blocks and goal
     */
    public int manhattan() {

        int manhattanFunction = 0;
        int empty = 0;

        for (int i = 0; i < mBlock.length; i++) {

            if (mBlock[i] != empty) {

                int currentPosition = i;
                int numberInCurrentPosition = mBlock[i];
                int correctPosition =
                        numberInCurrentPosition - 1;

                int distance = Math.abs(linearIndexToColumnNumber(currentPosition)
                        - linearIndexToColumnNumber(correctPosition)) +
                        Math.abs(linearIndexToRowNumber(currentPosition) -
                                linearIndexToRowNumber(correctPosition));

                manhattanFunction += distance;

            } else {
                int currentPosition = i;
                int correctPosition = mBlock.length - 1;

                int distance = Math.abs(linearIndexToColumnNumber(currentPosition)
                        - linearIndexToColumnNumber(correctPosition)) +
                        Math.abs(linearIndexToRowNumber(currentPosition) -
                                linearIndexToRowNumber(correctPosition));

                manhattanFunction += distance;
            }

        }

        return manhattanFunction;
    }

    private int linearIndexToRowNumber(int linearIndex) {
        return (int) Math.ceil(((double) linearIndex) / mBoardSize);
    }

    private int linearIndexToColumnNumber(int linearIndex) {
        int rowNumber = linearIndexToRowNumber(linearIndex);
        return linearIndex - (rowNumber - 1) * mBoardSize;
    }

    /**
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        int empty = 0;
        if (mBlock[mBlock.length - 1] != empty) {
            return false;
        }
        for (int i = 0; i < mBlock.length - 1; i++) {
            int coorectNumber = i + 1;
            if (mBlock[i] != coorectNumber) {
                return false;
            }
        }
        return true;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return newBoard
     */
    public Board twin() {
        int positionToSwipe1 = StdRandom.uniform(mBlock.length);
        while (mBlock[positionToSwipe1] == 0) {
            positionToSwipe1 = StdRandom.uniform(mBlock.length);
        }

        int positionToSwipe2 = StdRandom.uniform(mBlock.length);
        while ((mBlock[positionToSwipe2] == 0) || positionToSwipe2 ==
                positionToSwipe1) {
            positionToSwipe2 = StdRandom.uniform(mBlock.length);
        }

        int[] twinedBoard = mBlock.clone();
        int number = twinedBoard[positionToSwipe1];
        twinedBoard[positionToSwipe1] = mBlock[positionToSwipe2];
        twinedBoard[positionToSwipe2] = number;

        int[][] newBoard = getBoard(twinedBoard);

        return new Board(newBoard);

    }

    private int[][] getBoard(int[] board) {
        int[][] newBoard = new int[mBoardSize][mBoardSize];
        for (int i = 0; i < mBoardSize; i++) {
            for (int j = 0; j < mBoardSize; j++) {
                newBoard[i][j] = board[i * mBoardSize + j];
            }
        }
        return newBoard;
    }


    /**
     * does this board equal to other
     *
     * @param other board
     * @return
     */
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other instanceof Board) {
            Board that = (Board) other;
            if (this.mBoardSize != that.mBoardSize) {
                return false;
            }
            for (int i = 0; i < mBoardSize; i++) {
                if (this.mBlock[i] != that.mBlock[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {

        ArrayList<Board> neighbors = new ArrayList<>(4);

        if (mNeighbor.UP) {
            int neighborUpIndex = mEmptyBlockIndex - mEmptyBlockIndex;
            int[] neighborUPBoard = swap(mBlock, mEmptyBlockIndex,
                    neighborUpIndex);
            int[][] upBoard = getBoard(neighborUPBoard);
            neighbors.add(new Board(upBoard));
        }

        if (mNeighbor.RIGHT) {
            int neighborRightIndex = mEmptyBlockIndex + 1;
            int[] neighborRightBoard = swap(mBlock, mEmptyBlockIndex,
                    neighborRightIndex);
            int[][] rightBoard = getBoard(neighborRightBoard);
            neighbors.add(new Board(rightBoard));
        }

        if (mNeighbor.LEFT) {
            int neighborLeftIndex = mEmptyBlockIndex - 1;
            int[] neighborLeftBoard = swap(mBlock, mEmptyBlockIndex,
                    neighborLeftIndex);
            int[][] leftBoard = getBoard(neighborLeftBoard);
            neighbors.add(new Board(leftBoard));
        }

        if (mNeighbor.DOWN) {
            int neighborDownIndex = mEmptyBlockIndex + mBoardSize;
            int[] neighborDownBoard = swap(mBlock, mEmptyBlockIndex,
                    neighborDownIndex);
            int[][] downBoard = getBoard(neighborDownBoard);
            neighbors.add(new Board(downBoard));
        }

        return neighbors;
    }

    private int[] swap(int[] board, int swapFrom, int swapTo) {
        int[] swaped = board.clone();
        swaped[swapFrom] = board[swapTo];
        swaped[swapTo] = board[swapFrom];

        return swaped;
    }

    /**
     * string representation of this board (in the output format specified below)
     *
     * @return string
     */
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(mBoardSize + "\n");
        for (int i = 0; i < mBoardSize; i++) {
            for (int j = 0; j < mBoardSize; j++) {
                s.append(String.format("%2d ", mBlock[i * mBoardSize + j]));
            }
            s.append("\n");
        }
        return s.toString();

    }

    /**
     * Unit-test like
     *
     * @param args
     */
    public static void main(String[] args) {
        int BoardArray[][] = new int[3][3];
        BoardArray[0][0] = 0;
        BoardArray[0][1] = 1;
        BoardArray[0][2] = 3;

        BoardArray[1][0] = 4;
        BoardArray[1][1] = 2;
        BoardArray[1][2] = 5;

        BoardArray[2][0] = 7;
        BoardArray[2][1] = 8;
        BoardArray[2][2] = 6;

        Board board = new Board(BoardArray);
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        System.out.println(board.twin());

        for(Board neighborBoard : board.neighbors()) {
            System.out.println(neighborBoard);
            System.out.println(neighborBoard.hamming());
            System.out.println(neighborBoard.manhattan());
            System.out.println(neighborBoard.isGoal());
        }

    }

}
