import java.util.Scanner;

public class B {

    public static class Node {
        int min;
        int countOfMin;
        final static Node NULL_NODE = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);


        public Node(int min, int countOfMin) {
            this.min = min;
            this.countOfMin = countOfMin;
        }

        public Node(){

        }

        private static Node nodeCopy(Node node) {
            return new Node(node.min, node.countOfMin);
        }

        public static Node minOfNode(Node n1, Node n2) {
            Node curNode = new Node();


            if (n1.min == n2.min) {
                curNode.countOfMin = n1.countOfMin + n2.countOfMin;
                curNode.min = n1.min;
            } else {
                if (n1.min < n2.min){
                    curNode = nodeCopy(n1);
                } else {
                    curNode = nodeCopy(n2);
                }
            }
            return curNode;
        }
    }

    public static class MinSegmentTree {
        static public Node[] array;

        public static void setSize(int size) {
            array = new Node[size];
            for (int i = 0; i < size; i++) {
                array[i] = Node.NULL_NODE;
            }
        }


        public static void set(int pos, int value, int lx, int rx, int i) {

            if (rx - lx <= 1) {
                array[i] = new Node(value, 1);
                return;
            }

            int m = (lx + rx) / 2;

            if (m >= pos) {
                set(pos, value, lx, m, 2 * i + 1);
            } else {
                set(pos, value, m, rx, 2 * i + 2);
            }

            array[i] = Node.minOfNode(array[2 * i + 1], array[2 * i + 2]);
        }

        public static Node infMin(int i, int lx, int rx, int left, int right) {

            if (left <= lx && right >= rx) {
                return array[i];
            }

            if (lx >= right || rx <= left) {
                return Node.NULL_NODE;
            }


            int m = (lx + rx) / 2;

            return Node.minOfNode(infMin(2 * i + 1, lx, m, left, right), infMin(2 * i + 2, m, rx, left, right));

        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int stSize;
        int n = in.nextInt();
        int m = in.nextInt();

        if (n == 1) {
            stSize = 1;
        } else {
            stSize = (int) Math.pow(2, (int) (Math.log(n - 1) / Math.log(2)) + 1);
        }

        MinSegmentTree.setSize(stSize * 2);


        for (int i = 0; i < n; i++) {
            MinSegmentTree.set(i, in.nextInt(), -1, stSize - 1, 0);
        }
        //SegmentTree.printTree();

        for (int i = 0; i < m; i++) {
            if (in.nextInt() == 1) {
                MinSegmentTree.set(in.nextInt(), in.nextInt(), -1, stSize - 1, 0);
                //SegmentTree.printTree();
            } else {
                //SegmentTree.printTree();
                Node res = MinSegmentTree.infMin(0, 0, stSize, in.nextInt(), in.nextInt());
                System.out.println(res.min + " " + res.countOfMin);
            }
        }

    }
}
