package queue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayQueueADT {

    private Object[] elements = new Object[2];
    private int size = 0;
    private int len = elements.length;
    private int head, tail = 0;
    private Map<Object, Integer> entries = new HashMap<>();


    public static ArrayQueueADT create() {
        final ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[2];
        queue.len = queue.elements.length;
        return queue;
    }


    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        ensureCapacity(queue, ++queue.size);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.len;

        if (queue.entries.containsKey(element)) {
            queue.entries.put(element, queue.entries.get(element) + 1);
        } else {
            queue.entries.put(element, 1);
        }

    }

    private static void ensureCapacity(ArrayQueueADT queue, int size) {
        if (queue.len < size) {
            final Object[] array = new Object[size * 2];
            System.arraycopy(queue.elements, queue.head, array, 0, queue.len - queue.head);
            System.arraycopy(queue.elements, 0, array, queue.len - queue.head, queue.head);
            queue.elements = Arrays.copyOf(array, size * 2);
            queue.len = queue.elements.length;
            queue.head = 0;
            queue.tail = size - 1;
        }
    }

    public static Object element(ArrayQueueADT queue) {
        assert queue.size >= 1;
        return queue.elements[queue.head];
    }

    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size >= 1;
        queue.size--;
        Object result = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.len;

        queue.entries.put(result, queue.entries.get(result) - 1);

        return result;
    }

    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    public static void clear(ArrayQueueADT queue) {
        while (queue.size > 0) {
            dequeue(queue);
        }
    }

    public static int count(ArrayQueueADT queue, Object element) {
        assert element != null;
        if (queue.entries.get(element) == null) {
            return 0;
        }
        return queue.entries.get(element);
    }

}
