package queue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

abstract public class AbstractQueue implements Queue {

    private int size = 0;
    private final Map<Object, Integer> entries = new HashMap<>();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        while (size > 0) {
            dequeue();
        }
    }

    public int count(Object element) {
        assert element != null;
        if (entries.get(element) == null) {
            return 0;
        }
        return entries.get(element);
    }

    public void enqueue(Object element) {
        assert element != null;

        if (entries.containsKey(element)) {
            entries.put(element, entries.get(element) + 1);
        } else {
            entries.put(element, 1);
        }

        enqueueImpl(element, size);
        size++;
    }

    abstract void enqueueImpl(Object element, int size);


    public Object dequeue() {
        assert size >= 1;
        size--;

        final Object result = element();
        dequeueImpl();
        entries.put(result, entries.get(result) - 1);

        return result;
    }

    protected abstract void dequeueImpl();


    public Object element() {
        assert size >= 1;
        return elementImpl();
    }

    abstract Object elementImpl();

    public int countIf(Predicate<Object> pred) {
        assert pred != null;
        int count = 0;
        for (int i = 0; i < size; i++) {
            final Object element = dequeue();
            enqueue(element);
            if (pred.test(element)) {
                count++;
            }
        }

        return count;
    }

}