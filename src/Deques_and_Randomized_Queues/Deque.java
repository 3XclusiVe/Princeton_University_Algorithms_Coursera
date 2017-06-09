package Deques_and_Randomized_Queues;


import java.util.Iterator;
import java.util.function.Consumer;

import static edu.princeton.cs.algs4.StdOut.println;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
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
     * @param newItem item to add to the front
     * performance requirements: Constant worst-case time
     */
    public void addFirst(Item newItem) {

        checkNullPointerParameter(newItem);

        if(isEmpty()) {
            mHead = new Node();
            mHead.item = newItem;
            mHead.next = null;
            mTale = mHead;

        } else {

            Node lastHead = mHead;

            mHead = new Node();
            mHead.item = newItem;
            mHead.next = lastHead;

        }

        mSize++;
    }

    /**
     * add the item to the end
     * @param newItem item to add to the end
     * performance requirements: Constant worst-case time
     */
    public void addLast(Item newItem) {

        checkNullPointerParameter(newItem);

        if(isEmpty()) {
            mHead = new Node();
            mHead.item = newItem;
            mHead.next = null;
            mTale = mHead;

        } else {

            Node lastTale = mTale;

            mTale = new Node();
            lastTale.next = mTale;
            mTale.item = newItem;
        }

        mSize++;

    }

    /**
     * remove and return the item from the front
     * @return item from the front
     * performance requirements: Constant worst-case time
     */
    public Item removeFirst() {

        if(isEmpty()) {
            throw new java.util.NoSuchElementException("list is empty");
        }

        Node lastHead = mHead;

        mHead = new Node();
        if(lastHead.next != null) {
            mHead.item = lastHead.next.item;
            mHead.next = lastHead.next.next;
        }

        mSize--;

        return lastHead.item;

    }

    /**
     * remove and return the item from the end
     * @return item from the end
     * performance requirements: Constant worst-case time
     */
    public Item removeLast() {

        if(isEmpty()) {
            throw new java.util.NoSuchElementException("list is empty");
        }

        Node lastTale = mTale;

        mTale = new Node();
        if(lastTale.next != null) {
            mTale.item = lastTale.next.item;
            mTale.next = lastTale.next.next;
        }
        mSize--;

        return lastTale.item;

    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }



    private void checkNullPointerParameter(Item newItem) {
        if(newItem == null) {
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

            if(_current == null) {
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

    public static void main(String[] args) {

        Deque<String> deque = new Deque<String>();

        println(deque.isEmpty());
        println(deque.size());

        deque.addFirst("ABC");
        deque.addLast("XYZ");

        println(deque.removeLast());
        println(deque.removeLast());
        println();

        deque.addFirst("QQQ");
        deque.addFirst("WWW");
        deque.addFirst("zxc");
        deque.addFirst("oioi");
        deque.addLast("QQQ");
        deque.addLast("123wefwfe111");

        for (String element : deque) {
            println(element);
        }
    }
}
