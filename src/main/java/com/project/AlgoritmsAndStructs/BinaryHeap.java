package com.project.AlgoritmsAndStructs;


public class BinaryHeap {
    private Node rootNode;

    public <V extends  Object> boolean insert(int key, V data) {
        //System.out.println(data + " hashCode =  " + data.hashCode());
        Node node = new Node(key, data);
        if (rootNode == null) {
            rootNode = node;
        } else {
            Node current = rootNode;
            while (true) {
                if (key<current.getKey()) {
                    if (current.leftChild == null) {
                        current.leftChild = node;
                        return true;
                    } else {
                        current = current.leftChild;
                    }
                } else {
                    if (current.rightChild == null) {
                        current.rightChild = node;
                        return true;
                    } else {
                        current = current.rightChild;
                    }
                }
            }
        }
        return false;
    }

    public void get() {

    }

    public boolean remove() {
        return false;
    }

    /*
    Нужен не максимальный ключ, а средний, а так же выстраивать дерево так, чтобы и слева и справа ветви были одинакового размера и полностью заполнялись
     */
    public int balance() {
        Node current = this.rootNode;
        boolean maxKeyFound = false;
        int maxKey = 0;
        while (!maxKeyFound) {
            if (current.rightChild != null) {
                current = current.rightChild;
            } else {
                maxKey = current.getKey();
                maxKeyFound = true;
            }
        }
        return maxKey;
    }

    class Node<V extends  Object> {
        private int key;
        private V data;
        Node leftChild;
        Node rightChild;

        public Node(int key, V data) {
            this.key = key;
            this.data = data;
        }

        public int getKey() {
            return key;
        }

        public V getData() {
            return data;
        }
    }

    public static void main(String[] args) {
        BinaryHeap tree = new BinaryHeap();
        tree.insert(10, "A");
        tree.insert(5, "B");
        tree.insert(1, "C");
        tree.insert(4, "D");
        tree.insert(35, "E");
        tree.insert(20, "F");
        tree.insert(17, "G");
        tree.insert(31, "H");
        tree.insert(99, "I");
        tree.print(tree.rootNode);
//        Node node = tree.rootNode;
//        Node left;
//        boolean leftVisited = true;
//        Node right;
//        while ((left = node.leftChild) != null && (right = node.rightChild) != null) {
//            System.out.println(left.getData());
//            System.out.println(right.getData());
//            if (leftVisited) {
//                node = right;
//                leftVisited = false;
//            } else {
//                node = left;
//                leftVisited = true;
//            }
//        }
//        int maxKey = tree.balance();
        //System.out.println(tree);
    }

    public void print(Node root) {
        if (root == null) {
            //System.out.println();
            return;
        }
        print(root.leftChild);
        System.out.println(root.getKey());
        print(root.rightChild);
    }
}
