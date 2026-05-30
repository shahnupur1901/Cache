package org.example;

import java.util.HashMap;
import java.util.*;

public class Cache {
    private HashMap<Integer, Node> map;
    private DoublyLinkedList list;
    private EvictionPolicyStrategy evictionPolicyStrategy;
    int capacity;
    int currentCapacity;

    public Cache(int capacity) {
        this.map = new HashMap<>();
        this.list = new DoublyLinkedList();
        this.capacity = capacity;
    }

    public int get(int key) throws Exception {
        /* Core logic
        1. Lookup in hash table and if doesn't exist, throw NotFoundEx
        2. remove that node from LL
        3. Add that LL to the front.
         */
        Node node = map.getOrDefault(key, null);
        if (node == null) throw new Exception("Key Not Found");
        if (!this.list.removeNode(node)) throw new Exception("Inconsistent cache");
        this.list.addToFront(node);
        return node.getValue();
    }

    public void set(int key, int value) throws Exception {
        /* Core logic
        1. If key already exists - update that node and bring it front
        2. Else, create a new node and add in map and LL.
        3. If capacity == currentCapacity, then evict one with your policy and then add
         */
        if (capacity == currentCapacity) {
            evictionPolicyStrategy.delete();
        }
        Node node;
        if (map.containsKey(key)) {
            node = map.get(key);
            node.setValue(value);
        }
        else {
            node = new Node (key, value);
            map.put(key, node);
            currentCapacity++;
        }
        this.list.removeNode(node);
        this.list.addToFront(node);
    }
}
