package search;

public class BinarySearchMissing {


    //Pred: x in Z;
    private static int binSearchIterative(int x, int[] array) {
        //Pred: true;
        int l = -1;
        //Post: l = -1;

        //Pred: (array.length in Integer) && (array.length >= 0);
        int r = array.length;
        //Post: r = array.length;

        int middle;


        //Pred(I): array[r'] >= x && array[l'] < x;
        while (r - l > 1) {

            //Pred: (l' + r') in Integer && (r' - l' > 1);
            middle = (l + r) / 2;
            //Post: middle' = (l' + r') / 2;

            //Pred: array.length > middle' && middle' = (l' + r') / 2 && (r' - l' > 1);
            if (array[middle] >= x) {
                //Pred: array.length > middle' && array[middle'] >= x && (r' - l' > 1);
                r = middle;
                //Post: r' = middle';
            } else {
                //Pred: array.length > middle' && array[middle'] < x && (r' - l' > 1);
                l = middle;
                //Post: l' = middle';
            }
            //Post:  (r' != l') && (r' == middle || l' == middle) -> (array[r'] >= x && array[l'] < x);
        }
        //Post: (array[r'] >= x && array[l'] < x) && (r' - l' <= 1);
        //But (r' != l'), it means that r' - l' == 1;


        //Pred: true;
        if (array.length > r && array[r] == x) {
            //Pred: array.length > r && array[r] == x && r is Integer;
            return r;
            //Post: Returned r;
        } else {
            //Pred: array.length <= r && array[r] != x && r is Integer;
            return (-r - 1); // == ~r
            //Post: Returned (-r - 1);
        }
        //Post:  array[R] == x || (-(nearest pos to insert x) - 1);
    }
    //Post: array[R] == x || R == (-(nearest pos to insert x) - 1);


    //Pred: x in Z && (l < r);
    private static int binSearchRecursive(int x, int[] array, int l, int r) {
        //Pred: true;
        if (r - l <= 1) {
            //Pred: true;
            if (array.length > r && array[r] == x) {
                //Pred: array.length > r && array[r] == x && r is Integer;
                return r;
                //Post: Returned r;
            } else {
                //Pred: array.length <= r && array[r] != x && r is Integer;
                return (-r - 1);
                //Post: Returned (-r - 1);
            }
            //Post:  array[R] == x || (-(nearest pos to insert x) - 1);
        }
        //array[R] == x || R == (-(nearest pos to insert x) - 1);


        //Pred: Integer.MIN_VALUE <= (l + r) <= Integer.MAX_VALUE;
        int middle = (l + r) / 2;
        //Post: middle = (l + r) / 2;

        //Pred: middle = (l + r) / 2 && array.length > middle;
        if (array[middle] >= x) {
            //Pred: array[middle] >= x && array.length > middle;
            return binSearchRecursive(x, array, l, middle);
            //Post: R == binSearchRecursive(x, array, l, middle);
        } else {
            //Pred: array[middle] < x && array.length > middle;
            return binSearchRecursive(x, array, middle, r);
            //Post: R == binSearchRecursive(x, array, middle, r);
        }
        //Post: (l < R <= middle || middle < R <= r) && (array[R] >= x);

    }
    //array[R] == x || R == (-(nearest pos to insert x) - 1);


    //Pred: i < j (i, j in [0, +inf]) && args[i] >= args[j] && args.length > 0;
    public static void main(String[] args) {
        //Pred: args[0] can be parsed to Integer && args.length > 0;
        int x = Integer.parseInt(args[0]);
        //Post: x = Integer.parseInt(args[0]);

        //Pred: args.length > 0;
        int[] array = new int[args.length - 1];
        //Post: array = new int[args.length - 1];

        //Pred: true;
        int i = 0;
        //Post: i = 0;

        //i' is current value of i;

        //Pred(I): true;
        while (i < array.length) {
            //Pred: i' < array.length && args.length < i' + 1 && args[i' + 1] can be parsed to Integer;
            array[i] = Integer.parseInt(args[i + 1]);
            //Post: array[i'] = Integer.parseInt(args[i' + 1]) && i' < array.length;

            //Pred: array[i'] = Integer.parseInt(args[i' + 1]) && i' < array.length;
            i++;
            //Post: array[i' - 1] = Integer.parseInt(args[i' + 1]) && i' - 1 < array.length;
        }
        //Post: true && i >= array.length;

        //Post: binSearchIterative value can be parsed to String;
        System.out.println(binSearchIterative(x, array));
        //Pred: Printed R to console;

        //Pred: binSearchRecursive value can be parsed to String;
        //System.out.println(binSearchRecursive(x, array, -1, array.length));
        //Post: Printed R to console;
    }
    //Post: array[R] == x || R == (-(nearest pos to insert x) - 1);
}
