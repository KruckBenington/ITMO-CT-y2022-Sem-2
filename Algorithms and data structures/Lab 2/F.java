import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class F {

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

    public static class AVLTree {

        public Node peak = null;


        private static class Node {
            public long key, height;
            public Node left, right;
            public long lV, rV, sum;

            public Node(long key) {
                this.key = key;
                this.lV = key;
                this.rV = key;
                this.sum = key;
                this.left = null;
                this.right = null;
                this.height = 1;
            }
        }

        private void update(Node x) {
            x.sum = x.key;
            if (x.left != null) {
                x.lV = x.left.lV;
                x.sum += x.left.sum;
            } else {
                x.lV = x.key;
            }

            if (x.right != null) {
                x.rV = x.right.rV;
                x.sum += x.right.sum;
            } else {
                x.rV = x.key;
            }
        }

        private long getHeight(Node x) {
            return x == null ? 0 : x.height;
        }

        private Node leftRotation(Node x) {
            Node y = x.right;
            x.right = y.left;
            y.left = x;
            update(x);
            update(y);
            heightCorrect(x);
            heightCorrect(y);
            return y;
        }

        private Node rightRotation(Node x) {
            Node y = x.left;
            x.left = y.right;
            y.right = x;
            update(x);
            update(y);
            heightCorrect(x);
            heightCorrect(y);
            return y;
        }

        private Node bigLeftRotation(Node x) {
            x.right = rightRotation(x.right);
            x = leftRotation(x);
            return x;
        }

        private Node bigRightRotation(Node x) {
            x.left = leftRotation(x.left);
            x = rightRotation(x);
            return x;
        }

        private void heightCorrect(Node x) {
            x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        }


        private long heightDiff(Node x) {
            return getHeight(x.left) - getHeight(x.right);
        }

        private Node balanceNode(Node x) {

            heightCorrect(x);

            if (heightDiff(x) == -2) {
                if (heightDiff(x.right) > 0) {
                    return bigLeftRotation(x);
                } else {
                    return leftRotation(x);
                }
            }

            if (heightDiff(x) == 2) {
                if (heightDiff(x.left) < 0) {
                    return bigRightRotation(x);
                } else {
                    return rightRotation(x);
                }
            }

            update(x);
            return x;
        }

        public Node add(long key, Node x) {
            if (x == null) {
                return new Node(key);
            }

            if (key > x.key) {
                x.right = add(key, x.right);
            } else if (key < x.key) {
                x.left = add(key, x.left);
            }


            return balanceNode(x);
        }

        public long sum(long l, long r, Node x) {
            if (x == null || l > x.rV || r < x.lV) {
                return 0;
            }

            if (l <= x.lV && r >= x.rV) {
                return x.sum;
            }

            long add = (l <= x.key && r>= x.key) ? x.key : 0;

            return add + sum(l, r, x.left) + sum(l, r, x.right);
        }
    }


    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        AVLTree at = new AVLTree();

        int n = in.nextInt();
        long y = 0;

        for (int i = 0; i < n; i++) {
            String symbol = in.next();

            if (symbol.equals("+")) {
                at.peak = at.add((in.nextInt() + y) % (long)Math.pow(10, 9), at.peak) ;
                y = 0;
            } else if (symbol.equals("?")) {
                y = at.sum(in.nextInt(), in.nextInt(), at.peak);
                System.out.println(y);
            }
        }

    }
}
