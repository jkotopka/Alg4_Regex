package org.kotopka;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * {@code Bag} - Iterable set that allows duplicate items. Once inserted, items cannot be removed from the {@code Bag} instance.
 * @param <T> Java generic type
 */
public class Bag<T> implements Iterable<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private int size;
    private int modCount;

    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (head != null) {
            newNode.next = head;
        }

        head = newNode;
        size++;
        modCount++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterator<T> iterator() {
        return new BagIterator(head);
    }

    private class BagIterator implements Iterator<T> {

        Node<T> current;
        int innerModCount;

        public BagIterator(Node<T> first) {
            this.current = first;
            this.innerModCount = modCount;
        }

        private void validateModCount() {
            if (innerModCount != modCount) throw new ConcurrentModificationException("Bag modified during iteration");
        }

        @Override
        public boolean hasNext() {
            validateModCount();

            return current != null;
        }

        @Override
        public T next() {
            validateModCount();

            T data = current.data;
            current = current.next;

            return data;
        }
    }
}
