package org.example;

public class DoublyLinkedList {
    private Node head, tail;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public boolean addToFront(Node t) {
        if (head == null) {
            head = t;
            tail = t;
            return true;
        }
        t.next = head;
        head.prev = t;
        head = t;
        return true;
    }

    public Node removeLast() {
        // null
        if (head == null || tail == null) return null;
        // only 1 node
        if (head == tail) {
            Node deleted = tail;
            head = null;
            tail = null;
            return deleted;
        }
        // more than 2 nodes.
        Node deleted = tail;
        tail.prev.next = null;
        tail = tail.prev;
        deleted.next = null;
        deleted.prev = null;
        return deleted;
    }

    public boolean removeNode(Node node) {
        if (node == null) return false;
        // if list is empty
        if (head == null && tail == null) return false;
        // list has 1 node and that is this one
        if (head == tail && head == node) {
            head = null;
            tail = null;
            node.next = null;
            node.prev = null;
            return true;
        }
        // list has 1 node and that is not this
        if (head == tail && head != node) {
            return false;
        }
        // we have to be careful if head is node
        if (head == node) {
            head = head.next;
            head.prev = null;
            node.next = null;
            node.prev = null;
            return true;
        }
        // if tail is node use last helper
        if (tail == node) {
            removeLast();
            return true;
        }

        // now node is neither head or tail.
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
            return true;
        }

        return false;
    }

}
