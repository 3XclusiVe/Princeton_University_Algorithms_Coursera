package Deques_and_Randomized_Queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    /**
     * reads in a sequence of strings from standard input
     * and prints exactly k of them, uniformly at random.
     *
     * @param args number of items (k)
     */
    public static void main(String[] args) {

        int totalNumberOfSequence = Integer.parseInt(args[0]);
        int numberOfSequence = 0;

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        while (numberOfSequence < totalNumberOfSequence) {
            StdOut.println(randomizedQueue.dequeue());
            numberOfSequence++;
        }

    }


}
