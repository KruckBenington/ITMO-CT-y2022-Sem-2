import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;
import java.util.StringTokenizer;

public class I {

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

    public static class ImpTreap {

        public Node peak = null;

        private class Node {
            Random rand = new Random();
            public long priority;
            public Node left, right;
            public long count;
            public long value;

            public Node(long value) {
                this.priority = rand.nextInt();
                this.value = value;
                this.left = null;
                this.right = null;
                this.count = 1;
            }
        }

        private class Pair {
            public Node first, second;

            public Pair(Node first, Node second) {
                this.first = first;
                this.second = second;
            }

            public Node getFirst() {
                return first;
            }

            public Node getSecond() {
                return second;
            }
        }


        private void updateCount(Node x) {
            x.count = getCount(x.left) + getCount(x.right) + 1;
        }

        public long getCount(Node x) {
            return x == null ? 0 : x.count;
        }


        private Pair split(Node x, long key) {
            if (x == null) {
                return new Pair(null, null);
            }

            Pair dop = null;

            if (getCount(x.left) < key) {
                dop = split(x.right, key - (getCount(x.left) + 1));
                x.right = dop.getFirst();
                updateCount(x);
                return new Pair(x, dop.getSecond());
            } else {
                dop = split(x.left, key);
                x.left = dop.getSecond();
                updateCount(x);
                return new Pair(dop.getFirst(), x);
            }
        }

        private Node merge(Node leftNode, Node rightNode) {
            if (leftNode == null && rightNode == null) {
                return null;
            } else if (leftNode == null) {
                return rightNode;
            } else if (rightNode == null) {
                return leftNode;
            } else if (leftNode.priority >= rightNode.priority) {
                leftNode.right = merge(leftNode.right, rightNode);
                updateCount(leftNode);
                return leftNode;
            } else {
                rightNode.left = merge(leftNode, rightNode.left);
                updateCount(rightNode);
                return rightNode;
            }
        }


        public Node add(long key, long value, Node x) {
            Pair dop = split(x, key);
            return merge(merge(dop.getFirst(), new Node(value)), dop.getSecond());
        }

        public Node moveToBeginning(int leftBr, int rightBr, Node x) {
            Pair dop = split(x, leftBr - 1);
            Pair dop2 = split(dop.getSecond(), (rightBr - leftBr + 1));
            return merge(dop2.getFirst(), merge(dop.getFirst(), dop2.getSecond()));
        }


        public void BST(Node x) {
            if (x != null) {
                BST(x.left);
                System.out.print(x.value + " ");
                BST(x.right);
            }
        }

    }

    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        ImpTreap impT = new ImpTreap();

        int n = in.nextInt();
        int m = in.nextInt();

        for (int i = 0; i < n; i++) {
            impT.peak = impT.add(i, i + 1, impT.peak);
        }

        for (int i = 0; i < m; i++) {
            impT.peak = impT.moveToBeginning(in.nextInt(), in.nextInt(), impT.peak);
        }

        impT.BST(impT.peak);

    }
}
