package com.project.AlgoritmsAndStructs;

public class SinglyLinkedList {
    Node first;
    int size;
    public <T extends  Object> void insert(T obj) {
        Node node = new Node(obj, null);
        if (first == null) {
            first  = node;
            return;
        }
        Node current = first;
        while (current.next != null) {
            current = current.next;
        }
        current.next = node;
        this.size++;
    }

    public int size() {
        return size;
    }

    public Node getFirst() {
        return first;
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        list.insert("1");
        list.insert("2");
        list.insert("3");
        list.insert("4");
        list.insert("5");

        Node first = list.getFirst();
        Node current = first;
        Node next = current.next;
        Node prev = null;
        while (next != null) {
            next = current.next;
            current.next = prev;
            prev = current;
        }
        System.out.println();
    }

    class Node<T extends  Object> {
        T data;
        Node next;

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
