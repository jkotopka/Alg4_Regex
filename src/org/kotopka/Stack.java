package org.kotopka;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;

/**
 * {@code Stack} - Basic array-based Stack
 * @param <T> Java generic type
 */
public class Stack<T> {

    private T[] stack;
    private int count;

    public Stack() {
        this(1);
    }

    @SuppressWarnings("unchecked")
    public Stack(int initialCapacity) {
        this.stack = (T[]) new Object[initialCapacity];
    }

    private void ensureCapacity() {
        if (count == stack.length) {
            stack = Arrays.copyOf(stack, stack.length * 2);
        }
    }

    public void push(T item) {
        Objects.requireNonNull(item, "Cannot push null item");
        ensureCapacity();
        stack[count++] = item;
    }

    public T pop() {
        if (count == 0) throw new EmptyStackException();
        T item = stack[--count];
        stack[count] = null;
        return item;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

}
