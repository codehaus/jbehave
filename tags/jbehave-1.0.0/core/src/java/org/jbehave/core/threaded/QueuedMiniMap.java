package org.jbehave.core.threaded;


public interface QueuedMiniMap {
    
    public void put(Object key, Object value);
    public Object get(Object key, long timeout) throws TimeoutException;
    public void remove(Object key);
}