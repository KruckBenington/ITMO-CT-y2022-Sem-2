import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.SocketHandler;

public class A {


    public static class SegmentTree {
        static public long[] array;

        public static void setSize(int size){
            array = new long[size];
        }

        public static void set(int pos, long value, int lx, int rx, int i) {

            if (rx - lx <= 1) {
                array[i] = value;
                return;
            }

            int m = (lx + rx) / 2;

            if (m >= pos) {
                set(pos, value, lx, m, 2 * i + 1);
            } else {
                set(pos, value, m, rx, 2 * i + 2);
            }

            array[i] = array[2 * i + 1] + array[2 * i + 2];

        }

        public static long sum(int i, int lx, int rx, int left, int right) {

            if (left <= lx && right >= rx) {
                return array[i];
            }

            if (lx >= right || rx <= left) {
                return 0;
            }


            int m = (lx + rx) / 2;

            return sum(2 * i + 1, lx, m, left, right) + sum(2 * i + 2, m, rx, left, right);

        }

        public static void printTree() {
            int k = 1;
            int index = 0;
            int spaces = (array.length / 2)  - 1;
            while (k < array.length) {
                for (int i = 0; i < spaces; i++) {
                    System.out.print(' ');
                }
                spaces -= k;
                for (int i = 0; i < k; i++) {
                    System.out.print(array[index++]);
                    System.out.print(' ');
                }
                System.out.println();
                k *= 2;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n, m, stSize;

        n = in.nextInt();
        m = in.nextInt();

        if (n == 1) {
            stSize = 1;
        } else {
            stSize = (int) Math.pow(2,  (int)(Math.log(n - 1) / Math.log(2)) + 1);
        }


        SegmentTree.setSize(stSize * 2);

        for (int i = 0; i < n; i++) {
            SegmentTree.set(i, (long)in.nextInt(), -1, stSize - 1, 0);
        }
        //SegmentTree.printTree();

        for (int i = 0; i < m; i++) {
            if (in.nextInt() == 1) {
                SegmentTree.set(in.nextInt(), (long)in.nextInt(), -1, stSize - 1, 0);
                //SegmentTree.printTree();
            } else {
                //SegmentTree.printTree();
                System.out.println(SegmentTree.sum(0, 0, stSize, in.nextInt(), in.nextInt()));
            }
        }

    }
}
