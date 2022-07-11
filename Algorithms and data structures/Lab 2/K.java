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

    public static class AVLTree {

        public Node peak = null;


        private static class Node {
            public long key, height;
            public Node left, right;
            public long count;


            public Node(long key) {
                this.key = key;
                this.left = null;
                this.count = 1;

                this.right = null;
                this.height = 1;
            }
        }

        private void update(Node x) {
            x.count = 1;
            if (x.left != null){
                x.count += x.left.count;
            }

            if (x.right != null) {
                x.count += x.right.count;
            }
        }

        private long getHeight(Node x) {
            return x == null ? 0 : x.height;
        }

        private long getCount(Node x) {
            return x == null ? 0 : x.count;
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



        public Node delete(long key, Node x) {
            if (x == null) {
                return null;
            }

            if (key > x.key) {
                x.right = delete(key, x.right);
            } else if (key < x.key) {
                x.left = delete(key, x.left);
            } else if (x.left == null) {
                return x.right;
            } else if (x.right == null) {
                return x.left;
            } else {
                Node left = x.left;
                Node right = x.right;

                Node dop = right;
                while (dop.left != null) {
                    dop = dop.left;
                }

                x = new Node(dop.key);
                x.left = left;
                x.right = delete(x.key, right);
            }

            return balanceNode(x);
        }

        public long kMax(long n, Node x) {
            return searchKMax(peak.count - n + 1, x);
        }

        private long searchKMax(long n, Node x) {
            if (getCount(x.left) >= n) {
                return searchKMax(n, x.left);
            } else if (getCount(x.left)  + 1 == n) {
                return x.key;
            } else {
                return searchKMax(n - (getCount(x.left) + 1), x.right);
            }
        }
    }


    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        AVLTree at = new AVLTree();

        int n = in.nextInt();

        for (int i = 0; i < n; i++) {
            int mode = in.nextInt();

            if (mode == 1) {
                at.peak = at.add(in.nextInt() ,at.peak) ;
            } else if (mode == -1) {
                at.peak = at.delete(in.nextInt(), at.peak);
            } else if (mode == 0) {
                System.out.println(at.kMax(in.nextInt(), at.peak));
            }
        }

    }
}
