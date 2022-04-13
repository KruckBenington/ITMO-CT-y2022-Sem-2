package queue;

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(i);
        }


        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.dequeue() + " who?");
        }

        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue("knock_knock " + i + " -who?");
        }

        System.out.println(ArrayQueueModule.size());
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.clear();

        System.out.println(ArrayQueueModule.isEmpty());


        for (int i = 0; i < 13; i++) {
            ArrayQueueModule.enqueue(i);
            ArrayQueueModule.dequeue();
        }

        System.out.println(ArrayQueueModule.count(1));

        System.out.println(ArrayQueueModule.element());

    }


}
