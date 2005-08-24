package com.thoughtworks.jbehave.extensions.harness;

public interface QueuedMiniMap {
    
    public void put(Object key, Object value);
    public Object get(Object key, long timeout);
    public void remove(Object key);
}