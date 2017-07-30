package Puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 02.07.17.
 */
public class Solver {

    private List<Board> mSolutionPath = null;
    private Move mLastMove;
    private Board mGoalBoard;
    private boolean mSolvable = true;

    /**
     * find a solution to the initial
     * board (using the A* algorithm)
     *
     * @param initial Board
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }

        MinPQ<Move> possibleMoves = new MinPQ<>();
        Move startMove = new Move(initial);

        MinPQ<Move> possibleMovesTwin = new MinPQ<>();
        Move startMoveTwin = new Move(initial.twin());

        mGoalBoard = createGoalBoard(initial.dimension());

        possibleMoves.insert(startMove);
        possibleMovesTwin.insert(startMoveTwin);
        Board currentBoard = null;

        while (!isGoal(currentBoard)) {
            mLastMove = possibleMoves.delMin();

            for (Board nextMoveBoard : mLastMove.getCurrentBoard().neighbors()) {
                if (notRepeatedMove(nextMoveBoard)) {
                    possibleMoves.insert(new Move(nextMoveBoard, mLastMove));
                }
            }
            currentBoard = mLastMove.getCurrentBoard();

            Move twinedLastMove = possibleMovesTwin.delMin();
            for (Board nextMoveBoard : twinedLastMove.getCurrentBoard().neighbors()) {
                if (notRepeatedMove(nextMoveBoard)) {
                    possibleMovesTwin.insert(new Move(nextMoveBoard, twinedLastMove));
                }
            }
            if (isGoal(twinedLastMove.getCurrentBoard())) {
                unsolvable();
                break;
            }
        }

        if (isSolvable()) {
            mSolutionPath = new ArrayList<>(mLastMove.NumberOfMoves);
            Move currentNode = mLastMove;
            mSolutionPath.add(currentNode.getCurrentBoard());

            while (currentNode.getPrev() != null) {
                currentNode = currentNode.getPrev();
                mSolutionPath.add(currentNode.getCurrentBoard());
            }
            Collections.reverse(mSolutionPath);
        }

    }

    public static void main(String[] args) {

        int BoardArray[][] = new int[3][3];
        BoardArray[0][0] = 3;
        BoardArray[0][1] = 1;
        BoardArray[0][2] = 7;

        BoardArray[1][0] = 6;
        BoardArray[1][1] = 5;
        BoardArray[1][2] = 8;

        BoardArray[2][0] = 4;
        BoardArray[2][1] = 2;
        BoardArray[2][2] = 0;

        Board board = new Board(BoardArray);

        //System.out.println(board);
        //System.out.println(board.twin());
        Solver solver = new Solver(board);
        System.out.println(solver.isSolvable());
        System.out.println(solver.moves());
        //System.out.println(solver.solution());

        for (Board solutionBoard : solver.solution()) {
            System.out.println(solutionBoard);
        }

    }

    private void unsolvable() {
        mSolvable = false;
    }

    private boolean isGoal(Board board) {
        return mGoalBoard.equals(board);
    }

    private boolean notRepeatedMove(Board current) {
        if (mLastMove == null) {
            return true;
        }
        if (mLastMove.prev == null) {
            return true;
        }
        if (!mLastMove.prev.currentBoard.equals(current)) {
            return true;
        }
        return false;
    }

    private Board createGoalBoard(int size) {

        int[][] goalBoardArray = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goalBoardArray[i][j] = i * size + j + 1;
            }
        }
        goalBoardArray[size - 1][size - 1] = 0;
        return new Board(goalBoardArray);
    }

    /**
     * @return is the initial board solvable?
     */
    public boolean isSolvable() {
        return mSolvable;
    }

    /**
     * @return min number of moves to solve
     * initial board; -1 if unsolvable
     */
    public int moves() {
        if (isSolvable()) {
            return mLastMove.getNumberOfMoves();
        } else {
            return -1;
        }
    }

    /**
     * @return sequence of boards in a shortest solution;
     * null if unsolvable
     */
    public Iterable<Board> solution() {
        return mSolutionPath;
    }

    private class Move implements Comparable<Move> {

        private Board currentBoard;
        private int NumberOfMoves;
        private Move prev;

        public Move(Board currentBoard, Move
                prevMove) {
            this.currentBoard = currentBoard;
            this.NumberOfMoves = prevMove.getNumberOfMoves() + 1;
            this.prev = prevMove;
        }

        public Move(Board currentBoard) {
            this.currentBoard = currentBoard;
            this.NumberOfMoves = 0;
            this.prev = null;
        }

        public Board getCurrentBoard() {
            return currentBoard;
        }

        public int getNumberOfMoves() {
            return NumberOfMoves;
        }

        public Move getPrev() {
            return prev;
        }

        @Override
        public int compareTo(Move other) {
            int thisPriority = this.getCurrentBoard().manhattan() + this
                    .getNumberOfMoves();
            int otherPriority = other.getCurrentBoard().manhattan() + other
                    .getNumberOfMoves();

            return thisPriority - otherPriority;
        }
    }
}
