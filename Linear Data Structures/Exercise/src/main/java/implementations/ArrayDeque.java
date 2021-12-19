package implementations;

import interfaces.Deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<E> implements Deque<E> {
    private static final Integer DEFAULT_CAPACITY = 7;

    private int head;
    private int tail;
    private int size;

    private Object[] elements;

    public ArrayDeque() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.head = this.elements.length / 2;
        this.tail = this.head;
    }

    @Override
    public void add(E element) {
        this.addLast(element);
    }

    private void grow() {
        Object[] newElements = new Object[this.elements.length * 2 + 1];

        int middle = newElements.length / 2;

        int begin = middle - this.size / 2;

        int index = this.head;

       /* for (int i = middle; i < middle + this.size; i++) {
            newElements[i] = this.elements[this.head++];
        }*/
        for (int i = begin; index <= this.tail; i++) {
            newElements[i] = this.elements[index++];
        }

        this.head = begin;
        this.tail = this.head + this.size - 1;
        this.elements = newElements;


/*       for (int i = this.elements.length / 2 + this.head - 1;
//             i < middle + (this.tail - this.elements.length / 2);
//             i++) {
//
*/
    }

    @Override
    public void offer(E element) {
        this.add(element);
    }

    @Override
    public void addFirst(E element) {
        if (this.size == 0) {
            this.elements[this.head] = element;
        } else {
            if (this.head == 0) {
                this.grow();
            }

            this.elements[--this.head] = element;
        }

        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.elements[this.tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.grow();
            }

            this.elements[++this.tail] = element;
        }

        this.size++;
    }

    @Override
    public void push(E element) {
        this.addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        int realIndex = this.head + index;
        ensureIndex(realIndex);

        if (realIndex - this.head < this.tail - realIndex) {
            shiftLeft(realIndex - 1, element);
        } else {
            shiftRight(realIndex, element);
        }
    }

    private void shiftRight(int index, E element) {
        E lastElement = this.getAt(this.tail);
        for (int i = this.tail; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
        addLast(lastElement);
    }

    private void shiftLeft(int index, E element) {
        E firstElement = this.getAt(this.head);
        for (int i = this.head; i <= index; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[index] = element;
        addFirst(firstElement);
    }

    @Override
    public void set(int index, E element) {
        int realIndex = this.head + index;
        ensureIndex(realIndex);

        this.elements[realIndex] = element;
    }

    @Override
    public E peek() {
        if (this.size != 0) {
            return getAt(this.head);
        }
        return null;
    }

    private E getAt(int index) {
        return (E) this.elements[index];
    }

    @Override
    public E poll() {
        return this.removeFirst();
    }

    @Override
    public E pop() {
        return this.removeFirst();
    }

    @Override
    public E get(int index) {
        int realIndex = this.head + index;
        ensureIndex(realIndex);
        return this.getAt(realIndex);
    }

    @Override
    public E get(Object object) {
        if (isEmpty()){
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                return getAt(i);
            }
        }
        return null;
    }

    @Override
    public E remove(int index) {
        int realIndex = this.head + index;
        ensureIndex(realIndex);
        return this.getAt(realIndex);
    }

    private void ensureIndex(int realIndex) {
        if (realIndex < this.head || realIndex > this.tail) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E remove(Object object) {
        if (isEmpty()){
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                E element = this.getAt(i);
                this.elements[i] = null;
                for (int j = i; j < this.tail; j++) {
                    this.elements[j] = this.elements[j + 1];
                }
                this.removeLast();
                return element;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (!isEmpty()) {
            E element = this.getAt(this.head);
            this.elements[this.head] = null;
            this.head++;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public E removeLast() {
        if (!isEmpty()) {
            E element = this.getAt(this.tail);
            this.elements[this.tail] = null;
            this.tail--;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] elements = new Object[this.size];
        int index = 0;
        for (int i = this.head; i <= this.tail; i++) {
            elements[index++] = this.elements[i];
        }
        this.elements = elements;

    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = head;

            @Override
            public boolean hasNext() {
                return index <= tail;
            }

            @Override
            public E next() {
                return getAt(index++);
            }
        };
    }
}
