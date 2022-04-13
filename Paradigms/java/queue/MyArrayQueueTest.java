package queue;

public class MyArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();


        for (int i = 0; i < 5; i++) {
            queue1.enqueue("hello_" + i);
        }


        while (!queue1.isEmpty()) {
            System.out.println(queue1.dequeue() + " who?");
        }

        for (int i = 0; i < 10; i++) {
            queue2.enqueue("knock_knock " + i + " -who?");
        }

        System.out.println(queue2.size());
        System.out.println(queue2.element());
        queue2.clear();

        System.out.println(queue2.isEmpty());


        for (int i = 0; i < 13; i++) {
            queue2.enqueue(1);
        }

        System.out.println(queue2.count(1));

        System.out.println(queue2.element());

    }


}
