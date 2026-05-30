package org.example;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Cache {
    private HashMap<Integer, Node> map;
    private EvictionPolicyStrategy evictionPolicyStrategy;
    private int capacity;
    private final ReentrantLock lock = new ReentrantLock();

    public Cache(int capacity, EvictionPolicyStrategy evictionPolicyStrategy) {
        this.map = new HashMap<>();
        this.capacity = capacity;
        this.evictionPolicyStrategy = evictionPolicyStrategy;
    }

    public int get(int key) throws Exception {
        /* Core logic
        1. Lookup in hash table and if doesn't exist, throw NotFoundEx
        ORDERING IS THE RESPONSIBILITY OF EVICTION POLICY
        2. remove that node from LL
        3. Add that LL to the front.
         */
        lock.lock();
        try {
            Node node = map.getOrDefault(key, null);
            if (node == null) throw new Exception("Key Not Found");
            evictionPolicyStrategy.onAccess(node);
            return node.getValue();
        }
        finally {
                lock.unlock();
        }
    }

    public void set(int key, int value) throws Exception {
        /* Core logic
        1. If key exists, update the node and return.
        2. Else we have to create a new node.
            But First evict if no place.
        3. Create new node and add to map and ordering.
         */
        lock.lock();
        try {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.setValue(value);
                this.evictionPolicyStrategy.onAccess(node);
                return;
            }
            if (capacity == map.size()) {
                Node evictedNode = evictionPolicyStrategy.evict();
                map.remove(evictedNode.getKey());
            }
            Node node = new Node(key, value);
            map.put(key, node);
            this.evictionPolicyStrategy.add(node);
        }
        finally {
            lock.unlock();
        }
    }
}
