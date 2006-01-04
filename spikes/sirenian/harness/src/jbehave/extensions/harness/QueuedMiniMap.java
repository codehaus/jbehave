package jbehave.extensions.harness;

import jbehave.extensions.harness.time.TimeoutException;

public interface QueuedMiniMap {
    
    public void put(Object key, Object value);
    public Object get(Object key, long timeout) throws TimeoutException;
    public void remove(Object key);
}