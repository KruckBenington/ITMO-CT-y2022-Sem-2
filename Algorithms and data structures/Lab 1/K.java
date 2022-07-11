import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class K {
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


    public static class ST {
        static int[][] array;

        public static void setSize(int size) {
            array = new int[size][(int) (Math.log(size) / Math.log(2)) + 1];
        }

        public static void init(int[] els) {
            for (int j = 0; j < array[0].length; j++) {
                for (int i = 0; i < array.length; i++) {
                    if (j == 0) {
                        array[i][j] = els[i];
                    } else if (i + (int) Math.pow(2, j) <= array.length) {
                        array[i][j] = Math.min(array[i][j - 1], array[i + (int) Math.pow(2, j - 1)][j - 1]);
                    } else {
                        array[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }


        public static int request(int l, int r) {
            int len = r - l + 1;
            int log = (int) (Math.log(len) / Math.log(2));

            return Math.min(array[l][log], array[r - (int)Math.pow(2, log) + 1][log]);

        }

    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();
        /*int a = in.nextInt();*/
        /*int u = in.nextInt();
        int v = in.nextInt();*/

        int[] array = {1, 6, 2, 0, 3, 1};

        /*for (int i = 0; i < n; i++) {
            array[i] = a;
            a = (23 * a + 21563) % 16714589;
        }*/

        ST.setSize(n);
        ST.init(array);

        /*int r = ST.request(Math.min(u, v) - 1, Math.max(u, v) - 1);

        for (int i = 1; i < m; i++) {
            u = ((17 * u + 751 + r + 2 * i) % n) + 1;
            v = ((13 * v + 593 + r + 5 * i) % n) + 1;
            r = ST.request(Math.min(u, v) - 1, Math.max(u, v) - 1);
        }

        System.out.println(u + " " + v + " " + r);*/

    }
}
