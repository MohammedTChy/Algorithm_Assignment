/**
 * @author Karl Rameld kara9436
 * @author Mohammed Tahmid Chowdhury moch8386
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAQueue<E> implements ALDAQueue<E> {

    private static class Node<E> {
        private E data;
        private Node<E> next;

        Node(E data) {
            this.data = data;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private int capacity;
    private Node<E> first;

    public MyALDAQueue(int defaultCapacity) {
        if (defaultCapacity < 1)
            throw new IllegalArgumentException("Value cant be less than 1");
        this.capacity = defaultCapacity;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            private Node<E> node = first;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public E next() {
                if (node == null) {
                    throw new NoSuchElementException("Next is null");
                }
                Node<E> temp = node;
                node = node.next;
                return temp.data;
            }
        };
        return iterator;
    }

    @Override
    public void add(E data) {
        if (data == null)
            throw new NullPointerException("Cant add null");
        if (isFull()) {
            throw new IllegalStateException("List is full");
        }
        if (first == null) {
            first = new Node<E>(data);
        } else {
            Node<E> add = new Node<E>(data);
            Node<E> next = first;
            for (Node<E> temp = first; temp != null; temp = temp.next) {
                next = temp;
            }
            next.setNext(add);
        }
    }

    @Override
    public void addAll(Collection<? extends E> c) {
        if (c == null)
            throw new NullPointerException("Cant add null");
        for (E e : c) {
            add(e);
        }
    }

    @Override
    public E remove() {
        if (isEmpty())
            throw new NoSuchElementException("Cant remove on empty");
        Node<E> e = first;
        first = e.next;
        e.next = null;
        return e.data;
    }

    @Override
    public E peek() {
        if (first != null)
            return first.data;
        return null;
    }

    @Override
    public void clear() {
        if (isEmpty())
            return;
        while (first.next != null) {
            remove();
        }
        first = null;
    }

    @Override
    public int size() {
        int i = 0;
        for (Node<E> temp = first; temp != null; temp = temp.next) {
            i++;
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean isFull() {
        return size() == capacity;
    }

    @Override
    public int totalCapacity() {
        return capacity;
    }

    @Override
    public int currentCapacity() {
        return capacity - size();
    }

    @Override
    public int discriminate(E e) {
        int c = 0;
        if (e == null)
            throw new NullPointerException("Null is not valid parameter");
        for (Node<E> temp = first; temp != null; temp = temp.next) {
            if (temp.data.equals(e)) {
                c++;
            }
        }
        removeNode(e);
        for (int i = 0; i < c; i++) { // NOTE: idea stolen from handledning cuz its way better :) @@Rebecka
            add(e);
        }
        return c;
    }

    @Override
    public String toString() {
        String s = "";
        for (Node<E> temp = first; temp != null; temp = temp.next) {
            s = s + temp.data.toString();
            if (temp.next != null)
                s = s + ", ";
        }
        return "[" + s + "]";
    }

    private void removeNode(E e) {
        while (first != null && first.data.equals(e)) {
            remove();
        }
        Node<E> prev = first; // NOTE: also stole this idea from handledning :) @Rebecka
        for (Node<E> temp = first; temp != null; temp = temp.next) {
            if (temp.data.equals(e)) {
                prev.next = temp.next;
            } else {
                prev = temp;
            }
        }
    }
}