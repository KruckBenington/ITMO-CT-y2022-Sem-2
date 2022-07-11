import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

public class C {
    public static class AVLTree {

        public Node peak = null;
        private long prevRes = Long.MIN_VALUE;
        private long nextRes = Long.MAX_VALUE;

        private static class Node {
            public long key, height;
            public Node left, right;

            public Node(long key) {
                this.key = key;
                this.left = null;
                this.right = null;
                this.height = 1;
            }
        }

        private long getHeight(Node x) {
            return x == null ? 0 : x.height;
        }

        private Node leftRotation(Node x) {
            Node y = x.right;
            x.right = y.left;
            y.left = x;
            heightCorrect(x);
            heightCorrect(y);
            return y;
        }

        private Node rightRotation(Node x) {
            Node y = x.left;
            x.left = y.right;
            y.right = x;
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

        public Node balanceNode(Node x) {

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

            return x;
        }

        public Node insert(long key, Node x) {
            if (x == null) {
                return new Node(key);
            }

            if (key > x.key) {
                x.right = insert(key, x.right);
            } else if (key < x.key) {
                x.left = insert(key, x.left);
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

        public boolean exists(long key, Node x) {
            if (x == null) {
                return false;
            }

            if (key > x.key) {
                return exists(key, x.right);
            } else if (key < x.key) {
                return exists(key, x.left);
            } else {
                return true;
            }
        }

        public void next(long key, Node x) {
            nextS(key, x);
            if (nextRes == Long.MAX_VALUE){
                System.out.println("none");
            } else {
                System.out.println(nextRes);
            }
            nextRes = Long.MAX_VALUE;
        }

        public void nextS(long key, Node x) {
            if (x == null) {
               return;
            }

            if (key >= x.key) {
                nextS(key, x.right);
            } else {
                nextRes = Math.min(x.key, nextRes);
                nextS(key, x.left);
            }
        }

        public void prev(long key, Node x) {
            prevS(key, x);
            if (prevRes == Long.MIN_VALUE){
                System.out.println("none");
            } else {
                System.out.println(prevRes);
            }
            prevRes = Long.MIN_VALUE;
        }

        public void prevS(long key, Node x) {
            if (x == null) {
                return;
            }

            if (key > x.key) {
                prevRes = Math.max(x.key, prevRes);
                prevS(key, x.right);
            } else {
                prevS(key, x.left);
            }
        }

        public void checkHeights(Node x) {
            if (x == null) {
//                System.out.println("ok");
                return;
            }
            if (Math.abs(heightDiff(x)) >= 2){
                System.out.println("not correct");
            }

            checkHeights(x.left);
            checkHeights(x.right);
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        AVLTree at = new AVLTree();


        while (in.hasNext()) {
            String mode = in.next();
            long key = in.nextLong();

            if (Objects.equals(mode, "insert")) {
                at.peak = at.insert(key, at.peak);
//                at.checkHeights(at.peak);
//                System.out.println(at.peak.key);
            } else if (Objects.equals(mode, "delete")) {
                at.peak = at.delete(key, at.peak);
//                at.checkHeights(at.peak);
            } else if (Objects.equals(mode, "exists")) {
                System.out.println(at.exists(key, at.peak));
            } else if (Objects.equals(mode, "prev")) {
                at.prev(key, at.peak);
            } else if (Objects.equals(mode, "next")) {
                at.next(key, at.peak);
            }

        }
    }
}
