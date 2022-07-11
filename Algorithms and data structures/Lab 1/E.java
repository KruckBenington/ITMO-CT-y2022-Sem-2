import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class E {


    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream str) {
            br = new BufferedReader(new InputStreamReader(str));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static class SegmentTree {
        static public int[] array;

        public static void setSize(int size) {
            array = new int[size];
        }


        private static int operation(int el1, int el2) {
            return Math.max(el1, el2);
        }


        public static void build(int[] els, int lx, int rx, int i) {

            if (rx - lx <= 1) {
                array[i] = els[lx];
                return;
            }

            int m = (lx + rx) / 2;

            build(els, lx, m, i * 2 + 1);
            build(els, m, rx, i * 2 + 2);

            array[i] = operation(array[i * 2 + 1], array[i * 2 + 2]);

        }

        public static void set(int pos, int value, int lx, int rx, int i) {

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

            array[i] = operation(array[2 * i + 1], array[2 * i + 2]);

        }

        public static int searchEl(int x, int left, int lx, int rx, int i) {

            if (array[i] < x) {
                return -1;
            }


            if (rx - lx == 1) {
                return lx;
            }


            int m = (lx + rx) / 2;

            int res = -1;

            if (left < m) {
                res = searchEl(x, left, lx, m, i * 2  + 1);
            }

            if (res == -1) {
                res = searchEl(x, left, m, rx, i * 2 + 2);
            }

            return res;
        }


    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);

        int n, m, stSize;

        n = in.nextInt();
        m = in.nextInt();

        if (n == 1) {
            stSize = 1;
        } else {
            stSize = (int) Math.pow(2, (int) (Math.log(n - 1) / Math.log(2)) + 1);
        }


        SegmentTree.setSize(stSize * 2);

        int[] array = new int[stSize];

        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }

        SegmentTree.build(array, 0, stSize, 0);


        for (int i = 0; i < m; i++) {
            if (in.nextInt() == 1) {
                SegmentTree.set(in.nextInt(), in.nextInt(), -1, stSize - 1, 0);
            } else {
                System.out.println(SegmentTree.searchEl(in.nextInt(), in.nextInt(), 0, stSize, 0));
            }
        }

    }
}
