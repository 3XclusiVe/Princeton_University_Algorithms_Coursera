package Puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.SuffixArray;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 02.07.17.
 */
public class Solver {

    private int mMoves;
    private List<Board> mSolutionPath = new LinkedList<>();

    /**
     * find a solution to the initial
     * board (using the A* algorithm)
     * @param initial Board
     */
    public Solver(Board initial) {

        MinPQ<SearchNode> minPQ = new MinPQ<>();
        SearchNode initNode = new SearchNode(initial, 0, null);

        Board goalBoard = createGoalBoard(initial.dimension());

        minPQ.insert(initNode);
        SearchNode minSearcNode = null;
        Board currentBoard = null;
        int moves = 0;

        while (!goalBoard.equals(currentBoard)) {
            minSearcNode = minPQ.delMin();
            moves++;
            for (Board neighbor : minSearcNode.getCurrentBoard().neighbors()) {
                minPQ.insert(new SearchNode(neighbor, moves, minSearcNode));
            }
            currentBoard = minSearcNode.getCurrentBoard();
        }

        mMoves = moves;
        SearchNode currentNode = minSearcNode;
        mSolutionPath.add(currentNode.getCurrentBoard());
        while (currentNode.getPrev() != null) {
            currentNode = currentNode.getPrev();
            mSolutionPath.add(currentNode.getCurrentBoard());
        }

    }

    private Board createGoalBoard(int size) {

        int[][] goalBoardArray = new int [size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                goalBoardArray[i][j] = i * size + j + 1;
            }
        }
        goalBoardArray[size -1][size - 1] = 0;
        return new Board(goalBoardArray);
    }

    private class SearchNode implements Comparable<SearchNode> {

        private Board currentBoard;
        private int NumberOfMoves;
        private SearchNode prev;

        public SearchNode(Board currentBoard, int numberOfMoves, SearchNode
                prev) {
            this.currentBoard = currentBoard;
            this.NumberOfMoves = numberOfMoves;
            this.prev = prev;
        }

        public Board getCurrentBoard() {
            return currentBoard;
        }

        public int getNumberOfMoves() {
            return NumberOfMoves;
        }

        public SearchNode getPrev() {
            return prev;
        }

        @Override
        public int compareTo(SearchNode other) {
            int thisPriority = this.currentBoard.hamming() + this
                    .currentBoard.manhattan();
            int otherPriority = other.currentBoard.hamming() + other
                    .currentBoard.manhattan();

            if(thisPriority > otherPriority) {
                return 1;
            }
            if(thisPriority < otherPriority) {
                return -1;
            }

            return 0;
        }
    }

    /**
     * @return is the initial board solvable?
     */
    public boolean isSolvable() {
        return true;
    }

    /**
     * @return min number of moves to solve
     * initial board; -1 if unsolvable
     */
    public int moves() {
        return mMoves;
    }

    /**
     * @return sequence of boards in a shortest solution;
     * null if unsolvable
     */
    public Iterable<Board> solution() {
        return mSolutionPath;
    }
    public static void main(String[] args) {

        int BoardArray[][] = new int[3][3];
        BoardArray[0][0] = 8;
        BoardArray[0][1] = 1;
        BoardArray[0][2] = 3;

        BoardArray[1][0] = 4;
        BoardArray[1][1] = 2;
        BoardArray[1][2] = 5;

        BoardArray[2][0] = 7;
        BoardArray[2][1] = 0;
        BoardArray[2][2] = 6;

        Board board = new Board(BoardArray);

        Solver solver = new Solver(board);

        for(Board solutionBoard : solver.solution()) {
            System.out.println(solutionBoard);
        }

    }
}
