package queue;

public class LinkedQueue extends AbstractQueue {

    private static class Node {
        private final Object element;
        private Node next = null;

        public Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }

    }

    private Node top;

    public void enqueueImpl(Object element, int size) {
        if (size == 0) {
            top = new Node(element, null);
            top.next = top;
        } else {
            final Node tNode = new Node(element, top.next);
            top.next = tNode;
            top = tNode;
        }

    }

    public Object elementImpl() {
        return top.next.element;
    }

    public void dequeueImpl() {
        top.next = top.next.next;
    }
}
