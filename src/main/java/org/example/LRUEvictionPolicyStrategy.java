package org.example;

public class LRUEvictionPolicyStrategy extends EvictionPolicyStrategy {
    private DoublyLinkedList list = new DoublyLinkedList();

    public LRUEvictionPolicyStrategy() {
        this.list = new DoublyLinkedList();
    }

    @Override
    public Node evict() {
        return this.list.removeLast();
    }

    @Override
    public void onAccess(Node node)  {
        this.list.removeNode(node);
        this.list.addToFront(node);
    }

    @Override
    public void add(Node node) {
        this.list.addToFront(node);
    }
}
