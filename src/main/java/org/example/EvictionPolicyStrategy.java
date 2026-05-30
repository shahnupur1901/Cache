package org.example;

// all ordering
public abstract class EvictionPolicyStrategy {
    public abstract Node evict();
    public abstract void onAccess(Node node);
    public abstract void add(Node node);
}
