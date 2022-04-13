package queue;

import java.util.function.Predicate;

public interface Queue {


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
    Post: R == a[1] && immutable(2, n) && n' == n && a[1] == null;
        dequeue()

    Pred: true
    Post: R == n && n' == n && immutable(1, n);
        size()

    Pred: true
    Post: R == (n == 0) && n' == n && immutable(1, n);
        isEmpty()

    Pred: true
    Post: n' == 0 && for i = 1..n: a[i] == null;
        clear()

    Pred: element != null
    Post: R == count of element in array && immutable(1, n) && n' == n;
        count(element)

    Pred: predicate != null;
    Post: R == count of element satisfy Predicate && immutable(1, n) && n' == n;
        countIf(predicate)
    */


    void clear();

    boolean isEmpty();

    int size();

    void enqueue(Object element);

    int count(Object element);

    Object dequeue();

    Object element();

    int countIf(Predicate<Object> pred);
}
