package search;

public class BinarySearch {

    //Pred: x in Z && (l < r);
    private static int binSearchRecursive(int x, int[] array, int l, int r) {

        //Pred: true;
        if (r - l <= 1) {
            //Pred: (r - l <= 1);
            return r;
            //Post: R == r;
        } else {
            // (r - l > 1);
        }
        //Post: array[R] <= x && R = min(r);


        //Pred: Integer.MIN_VALUE <= (l + r) <= Integer.MAX_VALUE;
        int middle = (l + r) / 2;
        //Post: middle = (l + r) / 2;

        //Pred: middle = (l + r) / 2 && array.length > middle;
        if (array[middle] <= x) {
            //Pred: array[middle] <= x && array.length > middle;
            return binSearchRecursive(x, array, l, middle);
            //Post: R == binSearchRecursive(x, array, l, middle);
        } else {
            //Pred: array[middle] > x && array.length > middle;
            return binSearchRecursive(x, array, middle, r);
            //Post: R == binSearchRecursive(x, array, middle, r);
        }
        //Post: (l < R <= middle || middle < R <= r) && (array[R] <= x && R = min(r););
    }
    //Post: (min(R) && array[R] <= x);


    //Pred: x in Z;

    private static int binSearchIterative(int x, int[] array) {
        //Pred: true;
        int l = -1;
        //Post: l = -1;

        //Pred: (array.length in Integer) && (array.length >= 0);
        int r = array.length;
        //Post: r = array.length;

        int middle;

        //Pred(I): array[r'] <= x && array[l'] > x;
        while (r - l > 1) {

            //Pred: (l' + r') in Integer && (r' - l' > 1);
            middle = (l + r) / 2;
            //Post: middle' = (l' + r') / 2;

            //Pred: array.length > middle' && middle' = (l' + r') / 2 && (r' - l' > 1);
            if (array[middle] <= x) {
                //Pred: array.length > middle' && array[middle'] <= x && (r' - l' > 1);
                r = middle;
                //Post: r' = middle';
            } else {
                //Pred: array.length > middle' && array[middle'] > x && (r' - l' > 1);
                l = middle;
                //Post: l' = middle';
            }
            //Post:  (r' != l') && (r' == middle || l' == middle) -> (array[r'] <= x && array[l'] > x) (I know that is wrong!);
        }
        //Post: (array[r'] <= x && array[l'] > x) && (r' - l' <= 1);
        //But (r' != l'), it means that r' - l' == 1;

        //Pred: r' is Integer (it's always true);
        return r;
        //Post: R == r';

    }
    //Post: (R == r') && ((0 <= R < array.length) && (min(R) && array[R] <= x) || (array.length == 0) && (R == 0));


    //Pred: i < j -> args[i] >= args[j];
    public static void main(String[] args) {
        // Pred: args[0] can be parsed to Integer;
        int x = Integer.parseInt(args[0]);
        // Post: x = Integer.parseInt(args[0]);

        //Pred: args.length >= 1;
        int[] array = new int[args.length - 1];
        //Post: array = new int[args.length - 1];


        //Pred: true;
        int i = 0;
        //Post: i = 0;


//        (i' is current value of variable i);

        //Pred(I): true;
        while (i < array.length) {
            //Pred: i' < array.length && args.length < i' + 1 && args[i' + 1] can be parsed to Integer;
            array[i] = Integer.parseInt(args[i + 1]);
            //Post: array[i'] = Integer.parseInt(args[i' + 1]) && i' < array.length;

            //Pred: array[i'] = Integer.parseInt(args[i' + 1]) && i' < array.length;
            i++;
            //Post: i' - 1 < array.length;
        }
        //Post: true && i >= array.length;

        //Pred: Value of binSearchRecursive(x, array, -1, array.length) can be parsed to String
        System.out.println(binSearchRecursive(x, array, -1, array.length));
        //Post: Printed result of binSearchRecursive(x, array, -1, array.length) in String format in console;

//        System.out.println(binSearchIterative(x, array));
    }
    //Post: (R = min(index)) && array[R] <= x);
}