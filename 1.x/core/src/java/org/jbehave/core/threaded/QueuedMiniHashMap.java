package org.jbehave.core.threaded;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * An asynchronous holder implementing some map-like features. If an object is 
 * retrieved using a key and no object is present, this set will wait for a 
 * matching object.
 */
public class QueuedMiniHashMap implements QueuedMiniMap {
    public static final long DEFAULT_TIMEOUT = 30000;
    
    private Map map = Collections.synchronizedMap(new HashMap());
    private Object waitingPlace = new Object();
    private TimeouterFactory timeouterFactory;
    private long timeout;
    
    /**
     * Constructs a map with a default timeout of 30 seconds.
     */
    public QueuedMiniHashMap() {
    	this(new ClockedTimeouterFactory());
    }
    
    /**
     * @param timeoutInMillis overrides the map's timeout value.
     */
    public QueuedMiniHashMap(long timeoutInMillis) {
        this(new ClockedTimeouterFactory(), timeoutInMillis);
    }
    
    public QueuedMiniHashMap(TimeouterFactory timeouterFactory) {
        this(timeouterFactory, DEFAULT_TIMEOUT);
    }
    
    /**
     * @param timeoutInMillis overrides the map's timeout value.
     */
    public QueuedMiniHashMap(TimeouterFactory timeouterFactory, long timeoutInMillis) {
        this.timeouterFactory = timeouterFactory;
        timeout = timeoutInMillis;
    }

    public void put(Object key, Object value) {
        map.put(key, value);
		synchronized(waitingPlace) {
			waitingPlace.notifyAll();
		}
    }
    
    public Object get(Object key) throws TimeoutException {
        return get(key, timeout);
    }
    
    public Object get(Object key, long timeout) throws TimeoutException {
        Timeouter timeouter = timeouterFactory.createTimeouter();
        timeouter.start(timeout);
        Object value = map.get(key);
        synchronized(waitingPlace) {    
			while (value == null) {
					
				timeouter.checkTime();
			    try {
			        waitingPlace.wait(timeouter.getTimeLeftIfAny());
			    } catch (InterruptedException ie) {}
				value = map.get(key);
				
			}
		}
        return value;
    }

    public void remove(Object key) {
        map.remove(key);
    }    
}
