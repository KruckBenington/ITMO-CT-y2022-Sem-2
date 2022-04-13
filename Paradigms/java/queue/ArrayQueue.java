package queue;

import java.util.Arrays;

public class ArrayQueue extends AbstractQueue {

    private Object[] elements;
    private int len;
    private int head, tail = 0;

    public ArrayQueue() {
        elements = new Object[10];
        len = elements.length;
    }

    public void enqueueImpl(Object element, int size) {
        ensureCapacity(++size);
        elements[tail] = element;
        tail = (tail + 1) % len;
    }

    private void ensureCapacity(int size) {
        if (len < size) {
            final Object[] array = new Object[size * 2];
            System.arraycopy(elements, head, array, 0, len - head);
            System.arraycopy(elements, 0, array, len - head, head);
            elements = Arrays.copyOf(array, size * 2);
            len = elements.length;
            head = 0;
            tail = size - 1;
        }
    }

    public Object elementImpl() {
        return elements[head];
    }

    public void dequeueImpl() {
        elements[head] = null;
        head = (head + 1) % len;
    }

}
