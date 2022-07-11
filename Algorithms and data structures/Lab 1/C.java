import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class C {


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


    public static class Node {
        long prefix, suffix, sum, segment;


        public Node initLeaf(long value) {
            this.prefix = value;
            this.suffix = value;
            this.sum = value;
            this.segment = value;
            return this;
        }
    }

    public static class SegmentTree {
        static public Node[] array;

        public static void setSize(int size) {
            array = new Node[size];
        }


        private static Node operation(Node left, Node right) {
            Node answer = new Node();
            answer.prefix = Math.max(left.prefix, left.sum + right.prefix);
            answer.suffix = Math.max(left.suffix + right.sum, right.suffix);
            answer.sum = left.sum + right.sum;
            answer.segment = Math.max(Math.max(left.segment, left.suffix + right.prefix), right.segment);
            return answer;
        }


        public static void build(long[] els, int lx, int rx, int i) {

            if (rx - lx <= 1) {
                array[i] = new Node().initLeaf(els[lx]);
                return;
            }

            int m = (lx + rx) / 2;

            build(els, lx, m, i * 2 + 1);
            build(els, m, rx, i * 2 + 2);

            array[i] = operation(array[i * 2 + 1], array[i * 2 + 2]);

        }

        public static void set(int pos, long value, int lx, int rx, int i) {

            if (rx - lx <= 1) {
                array[i] = new Node().initLeaf(value);
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

        public static void printAns() {
            System.out.println(array[0].segment > 0 ? array[0].segment : 0);
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

        long[] array = new long[stSize];

        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }

        SegmentTree.build(array, 0, stSize, 0);

        SegmentTree.printAns();
        for (int i = 0; i < m; i++) {
            SegmentTree.set(in.nextInt(), in.nextInt(), -1, stSize - 1, 0);
            SegmentTree.printAns();
        }

    }
}
