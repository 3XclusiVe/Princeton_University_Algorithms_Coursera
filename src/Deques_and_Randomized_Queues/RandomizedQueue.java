package Deques_and_Randomized_Queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.jar.Pack200;

import static edu.princeton.cs.algs4.StdOut.println;


/**
 * Created by user on 11.06.17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {


    private Item[] mQueueElement;
    private int mSize;


    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        mQueueElement = (Item[]) new Object[1];
    }

    /**
     * @return is the queue empty?
     */
    public boolean isEmpty() {
        return mSize == 0;
    }

    /**
     * @return the number of items on the queue
     */
    public int size() {
        return mSize;
    }

    /**
     * add the item
     *
     * @param item item to add
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
     */
    public Item dequeue() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        int queueElementToDeleteIndex = StdRandom.uniform(mSize);
        Item lastElement = mQueueElement[mSize - 1];
        Item elementToDelete = mQueueElement[queueElementToDeleteIndex];

        mQueueElement[queueElementToDeleteIndex] = lastElement;
        mQueueElement[mSize] = null;

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

        private int mCurrentIndex = StdRandom.uniform(mSize);
        private int mNextCalls = 0;

        @Override
        public boolean hasNext() {
            return mNextCalls != mSize;
        }

        @Override
        public Item next() {
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


    /**
     * Unit-test like function:
     * bad style but acceptable in this case
     *
     * @param args
     */
    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 10; i++) {
            println(queue.dequeue());
        }

    }
}
