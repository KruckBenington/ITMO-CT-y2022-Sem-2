package queue;


    /*
    Model: a[1]..a[n]
    Invariant: for i=1..n: a[i] != null;


    Let immutable(1, n): for i = 1...n: a'[i] == a[i];


    Pred: element != null
    Post: n' = n + 1  && immutable(1, n) && a[n'] == element ;
        enqueue(element)


    Pred: n >= 1;
    Post: R == a[1] && immutable(1, n) && n' == n;
        element()

    Pred: n >= 1;
    Post: R == a[1] && for i 1..n': a'[i] = a[i + 1] && n' == n - 1
        dequeue()

    Pred: true
    Post: R == n && n' == n && immutable(1, n);
        size()

    Pred: true
    Post: R == (n == 0) && n' == n && immutable(1, n);
        isEmpty()

    Pred: true
    Post: n' == 0
        clear()

    Pred: element != null
    Post: R == count of element in array && immutable(1, n) && n' == n;
        count(element)
    */


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayQueueModule {
    private static Object[] elements = new Object[2];
    private static int size = 0;
    private static int len = elements.length;
    private static int head, tail = 0;
    private static Map<Object, Integer> entries = new HashMap<>();

    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity(++size);
        elements[tail] = element;
        tail = (tail + 1) % len;

        if (entries.containsKey(element)) {
            entries.put(element, entries.get(element) + 1);
        } else {
            entries.put(element, 1);
        }

    }


    private static void ensureCapacity(int size) {
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


    public static Object element() {
        assert !isEmpty();
        return elements[head];
    }

    public static Object dequeue() {
        assert !isEmpty();
        size--;
        final Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % len;

        entries.put(result, entries.get(result) - 1);

        return result;
    }

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {
        return size == 0;
    }

    public static void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    public static int count(Object element) {
        assert element != null;
        if (entries.get(element) == null) {
            return 0;
        }
        return entries.get(element);
    }
}


