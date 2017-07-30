package Puzzle;

import edu.princeton.cs.algs4.In;

import static edu.princeton.cs.algs4.StdOut.println;

/**
 * Created by user on 04.07.17.
 */
public class Client {
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        println(initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            println("No solution possible");
        else {
            println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                println(board);
        }
    }
}
