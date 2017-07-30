package Deques_and_Randomized_Queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static edu.princeton.cs.algs4.StdOut.println;


/**
 * A randomized queue is similar to a stack or queue,
 * except that the item removed is chosen uniformly
 * at random from items in the data structure.
 *
 * @param <Item> generic parameter
 */
public class RandomizedQueue<Item> implements Iterable<Item> {


    private Item[] mQueueElement;
    private int mSize;


    /**
     * construct an empty randomized queue
     * performance requirements: constant amortized time
     */
    public RandomizedQueue() {
        mQueueElement = (Item[]) new Object[1];
    }

    /**
     * Unit-test like function:
     * bad style but acceptable in this case
     *
     * @param args
     */
    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        try {
            queue.enqueue(null);
        } catch (NullPointerException e) {
            testCase(true);
        }

        try {
            queue.dequeue();
        } catch (NoSuchElementException e) {
            testCase(true);
        }

        try {
            queue.sample();
        } catch (NoSuchElementException e) {
            testCase(true);
        }


        try {
            queue.iterator().remove();
        } catch (UnsupportedOperationException e) {
            testCase(true);
        }

        try {
            queue.iterator().next();
        } catch (NoSuchElementException e) {
            testCase(true);
        }

        testCase(queue.size() == 0);
        testCase(queue.size() == 0);
        testCase(queue.isEmpty());
        testCase(queue.isEmpty());
        queue.enqueue(39);
        testCase(queue.dequeue() == 39);


        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 10; i++) {
            println(queue.dequeue());
        }


    }

    private static void testCase(boolean testCase) {
        if (testCase == true) {
            println("PASSED");
        } else {
            println("FAILED");
        }
    }

    /**
     * @return is the queue empty?
     * performance requirements: constant amortized time
     */
    public boolean isEmpty() {
        return mSize == 0;
    }

    /**
     * @return the number of items on the queue
     * performance requirements: constant amortized time
     */
    public int size() {
        return mSize;
    }

    /**
     * add the item
     *
     * @param item item to add
     *             performance requirements: constant amortized time
     */
    public void enqueue(Item item) {

        checkNullPointerParameter(item);

        if (mQueueElement.length == mSize) {
            resize(2 * mSize);
        }

        mSize++;
        int end = mSize - 1;
        mQueueElement[end] = item;
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];

        for (int i = 0; i < mSize; i++) {
            copy[i] = mQueueElement[i];
        }
        mQueueElement = copy;
    }

    private void checkNullPointerParameter(Item newItem) {
        if (newItem == null) {
            throw new NullPointerException("cant add null pointer " +
                    "element");
        }
    }

    /**
     * remove and return a random item
     *
     * @return random item
     * performance requirements: constant amortized time
     */
    public Item dequeue() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        int queueElementToDeleteIndex = StdRandom.uniform(mSize);
        Item lastElement = mQueueElement[mSize - 1];
        Item elementToDelete = mQueueElement[queueElementToDeleteIndex];

        mQueueElement[queueElementToDeleteIndex] = lastElement;
        mQueueElement[mSize - 1] = null;

        if ((mSize > 0) && (mSize == mQueueElement.length / 4)) {
            resize(mQueueElement.length / 2);
        }

        mSize--;

        return elementToDelete;
    }

    /**
     * return (but do not remove) a random item
     *
     * @return random item
     * performance requirements: constant amortized time
     */
    public Item sample() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        return mQueueElement[StdRandom.uniform(mSize)];
    }

    /**
     * return an independent iterator over items in random order
     *
     * @return iterator
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int mCurrentIndex;
        private int mNextCalls;

        public RandomizedQueueIterator() {
            mCurrentIndex = StdRandom.uniform(mSize + 1) - 1;
            mNextCalls = 0;
        }

        @Override
        public boolean hasNext() {
            return mNextCalls != mSize;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            mNextCalls++;
            Item currentElement = mQueueElement[mCurrentIndex % mSize];
            mCurrentIndex++;
            return currentElement;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super Item> action) {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}
