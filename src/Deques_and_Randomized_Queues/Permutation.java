package Deques_and_Randomized_Queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by user on 11.06.17.
 */
public class Permutation {

    public static void main(String[] args) {

        int totalNumberOfSequence = Integer.parseInt(args[0]);
        int numberOfSequence = 0;

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (numberOfSequence < totalNumberOfSequence) {
            randomizedQueue.enqueue(StdIn.readString());
            numberOfSequence++;
        }

        while (numberOfSequence > 0) {
            StdOut.println(randomizedQueue.dequeue());
            numberOfSequence--;
        }

    }


}
