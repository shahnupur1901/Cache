package org.example;

// all ordering
public interface EvictionPolicyStrategy {
    public Node evict();
    public void onAccess(Node node);
    public void add(Node node);
}
