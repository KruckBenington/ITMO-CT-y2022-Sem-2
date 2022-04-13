package queue;

public class ArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();


        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue1, "hello_" + i);
        }

        while (!ArrayQueueADT.isEmpty(queue1)) {
            System.out.println(ArrayQueueADT.dequeue(queue1) + " who?");
        }

        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue2, "knock_knock " + i + " -who?");
        }

        System.out.println(ArrayQueueADT.size(queue2));
        System.out.println(ArrayQueueADT.element(queue2));
        ArrayQueueADT.clear(queue2);

        System.out.println(ArrayQueueADT.isEmpty(queue2));


        for (int i = 0; i < 13; i++) {
            ArrayQueueADT.enqueue(queue2, i);
        }

        System.out.println(ArrayQueueADT.count(queue2, 1));

        System.out.println(ArrayQueueADT.element(queue2));
    }


}
