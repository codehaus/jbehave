package com.thoughtworks.jbehave.extensions.harness;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.extensions.harness.time.ClockedTimeouterFactory;
import com.thoughtworks.jbehave.extensions.harness.time.PseudoClock;
import com.thoughtworks.jbehave.extensions.harness.time.TimeoutException;

public class QueuedMiniHashMapBehaviour extends UsingMiniMock {
    
	private PseudoClock clock = new PseudoClock();
	
    public void shouldReturnExistingObjectMatchingKey() {
        QueuedMiniHashMap queuedMiniMap = new QueuedMiniHashMap(new ClockedTimeouterFactory(clock));
        
        queuedMiniMap.put("F", "Frodo");
        Verify.that(queuedMiniMap.get("F", 100).equals("Frodo"));
    }
    
    public void shouldReturnObjectMatchingKeyWhenAddedLater() {
        final QueuedMiniHashMap queuedMiniMap = new QueuedMiniHashMap(new ClockedTimeouterFactory(clock));
        final ObjectHolder objectHolder = new ObjectHolder();
        
		Thread threadForGetToRun = new Thread(new Runnable() {
			public void run() {
				objectHolder.object = queuedMiniMap.get("F", 100);
				synchronized(QueuedMiniHashMapBehaviour.this) {
					QueuedMiniHashMapBehaviour.this.notifyAll();
				}
			}
		});
		
		threadForGetToRun.start();
		
		// Add an item to the map. Wait until all objects verified
		// before ending the test.
		synchronized (this) {
			// Wait for a short while, just to be sure that the getThread is running and
			// waiting on the item (otherwise this test passes anyway, bizarrely)
			try {
			    wait(200); 
			} catch (InterruptedException e) {};
		    
			queuedMiniMap.put("F", "Frodo");
			try {
				while (threadForGetToRun.isAlive()) { wait(200); }
			} catch (InterruptedException e) {}
		}
		Verify.that("Frodo".equals(objectHolder.object));
    }
    
    public void shouldTimeout() {
        final ObjectHolder objectHolder = new ObjectHolder();
        final QueuedMiniHashMap queuedMiniMap = 
        	new QueuedMiniHashMap(new ClockedTimeouterFactory(clock));
        
		Thread threadForGetToRun = new Thread(new Runnable() {
			public void run() {
				try  {
					queuedMiniMap.get("F", 100);
				} catch (TimeoutException e) {
					objectHolder.object = e;
				}
				synchronized(QueuedMiniHashMapBehaviour.this) {
					QueuedMiniHashMapBehaviour.this.notifyAll();
				}
			}
		});
		
		threadForGetToRun.start();   		
		
		synchronized (this) {
			// Wait for a short while to let the thread run
			try {
			    wait(100); 
			} catch (InterruptedException e) {};
		}
		

		clock.setTimeInMillis(clock.getTimeInMillis() + 2000);	         

		synchronized (this) {
			// Wait for a short while to let the thread throw exception
			try {
			    wait(100); 
			} catch (InterruptedException e) {};
		}
		
        Verify.that(objectHolder.object != null);        		
        Verify.that(objectHolder.object instanceof TimeoutException);
    }
    
    private class ObjectHolder {
        public Object object;
    }
}
