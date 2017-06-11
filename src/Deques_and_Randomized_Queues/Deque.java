package Deques_and_Randomized_Queues;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static edu.princeton.cs.algs4.StdOut.println;

/**
 * A double-ended queue or deque
 * is a generalization of a stack and a queue that
 * supports adding and removing items from either
 * the front or the back of the data structure
 *
 * @param <Item> generic parameter
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private int mSize;

    private Node mHead;
    private Node mTale;

    /**
     * construct an empty deque
     */
    public Deque() {
        mSize = 0;
    }

    /**
     * @return is the deque empty?
     * performance requirements: Constant worst-case time
     */
    public boolean isEmpty() {

        return mSize == 0;
    }

    /**
     * @return the number of items on the deque
     * performance requirements: Constant worst-case time
     */
    public int size() {

        return mSize;
    }

    /**
     * add the item to the front
     *
     * @param newItem item to add to the front
     *                performance requirements: Constant worst-case time
     */
    public void addFirst(Item newItem) {

        checkNullPointerParameter(newItem);

        if (isEmpty()) {
            mHead = new Node();
            mHead.item = newItem;
            mHead.next = null;
            mHead.previous = null;
            mTale = mHead;

        } else {

            Node lastHead = mHead;

            mHead = new Node();
            mHead.item = newItem;
            mHead.next = lastHead;
            lastHead.previous = mHead;

        }

        mSize++;
    }

    /**
     * add the item to the end
     *
     * @param newItem item to add to the end
     *                performance requirements: Constant worst-case time
     */
    public void addLast(Item newItem) {

        checkNullPointerParameter(newItem);

        if (isEmpty()) {
            mHead = new Node();
            mHead.item = newItem;
            mHead.next = null;
            mTale = mHead;

        } else {

            Node lastTale = mTale;

            mTale = new Node();
            lastTale.next = mTale;
            mTale.item = newItem;
            mTale.previous = lastTale;
        }

        mSize++;

    }

    /**
     * remove and return the item from the front
     *
     * @return item from the front
     * performance requirements: Constant worst-case time
     */
    public Item removeFirst() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        Node lastHead = mHead;

        if (lastHead.next != null) {
            mHead = lastHead.next;
            mHead.previous = null;
        }

        mSize--;

        return lastHead.item;

    }

    /**
     * remove and return the item from the end
     *
     * @return item from the end
     * performance requirements: Constant worst-case time
     */
    public Item removeLast() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        Node lastTale = mTale;

        if (lastTale.previous != null) {
            mTale = lastTale.previous;
            mTale.next = null;
        }
        mSize--;

        return lastTale.item;

    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    private void checkNullPointerParameter(Item newItem) {
        if (newItem == null) {
            throw new NullPointerException("cant add null pointer " +
                    "element");
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node _current = mHead;

        @Override
        public boolean hasNext() {
            return _current != null;
        }

        @Override
        public Item next() {

            if (_current == null) {
                throw new java.util.NoSuchElementException("no elements in " +
                        "collection");
            }

            Item item = _current.item;
            _current = _current.next;

            return item;
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

        Deque<String> deque = new Deque<String>();

        try {
            deque.addFirst(null);
        } catch (NullPointerException e) {
            testCase(true);
        }

        try {
            deque.addLast(null);
        } catch (NullPointerException e) {
            testCase(true);
        }

        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            testCase(true);
        }

        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            testCase(true);
        }

        try {
            deque.iterator().remove();
        } catch (UnsupportedOperationException e) {
            testCase(true);
        }

        testCase(deque.isEmpty() == true);
        testCase(deque.size() == 0);

        deque.addFirst("A");
        deque.addFirst("B");
        deque.addFirst("C");
        deque.addLast("X");
        deque.addLast("Y");
        deque.addLast("Z");

        String allElements = new String();
        for (String element : deque) {
            allElements += element;
        }

        testCase(allElements.equals("CBAXYZ"));
        testCase(deque.size() == 6);
        testCase(deque.isEmpty() == false);

        deque = new Deque<String>();
        deque.addFirst("A");
        deque.addFirst("B");
        deque.addFirst("C");

        testCase(deque.removeLast().equals("A"));
        testCase(deque.removeFirst().equals("C"));
        testCase(deque.isEmpty() == false);
        testCase(deque.size() == 1);

        allElements = new String();
        for (String element : deque) {
            allElements += element;
        }
        testCase(allElements.equals("B"));

        testCase(deque.removeLast().equals("B"));
        testCase(deque.isEmpty());
        testCase(deque.size() == 0);

        deque.addFirst("AAv");
        deque.addFirst("122");
        deque.addLast("A213");
        deque.addLast("11fg>");

        allElements = new String();
        for (String element : deque) {
            allElements += element;
        }

        testCase(allElements.equals("122AAvA21311fg>"));


        Deque<Integer> deque1 = new Deque<Integer>();

        int n = 1000000;
        for (int i = 0; i < n; i++) {
            deque1.addFirst(i);
        }
        for (int i = 0; i < n; i++) {
            testCase(deque1.removeLast() == i);
        }

    }

    private static void testCase(boolean testCase) {
        if (testCase == true) {
            println("PASSED");
        } else {
            println("FAILED");
        }
    }
}
