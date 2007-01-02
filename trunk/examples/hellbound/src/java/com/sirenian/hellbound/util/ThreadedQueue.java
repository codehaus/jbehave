package com.sirenian.hellbound.util;

import java.util.ArrayList;
import java.util.List;


public abstract class ThreadedQueue implements Queue {
	
    protected static final Runnable EMPTY_RUNNABLE = new Runnable() {
        public void run() {
        }
        public String toString() {
            return "ThreadedQueue.EMPTY_RUNNABLE";
        }
    };

    private ArrayList eventList = new ArrayList();
    private ArrayList afterEmptyEventList = new ArrayList();
	private boolean shouldRun = true;
    private Throwable throwable;

    private final String queueName;
    
    protected ThreadedQueue(String queueName) {
        this.queueName = queueName;
        Runnable runnable = new Runnable() {
            public void run() {
                synchronized (eventList) {
                    while (shouldRun && throwable == null) {
                        try {
                            runAllInList(eventList);
                            runAllInList(afterEmptyEventList);
                            waitForNextRequest();
                        } catch (Throwable t) {
                            throwable = t;
                        }
                    }

                }
            }
        };
        Logger.debug(this, "Starting thread for " + queueName);
        new Thread(runnable, queueName).start();
        if (throwable != null) {
            throw new RuntimeException(throwable);
        }
    }
    
    public void stop() {
        Logger.debug(this, "Stopping queue " + queueName);
    	synchronized (eventList) {
    		shouldRun = false;
            eventList.notifyAll();
		}
    }

    private void waitForNextRequest() {
        try {
            eventList.wait();
        } catch (InterruptedException e) {
        }
    }

    private void runAllInList(List list) {
        while (!list.isEmpty()) {
            Runnable action = (Runnable) list.remove(0);
            Logger.debug(this, "Running " + action.toString());
            perform(action);
        }
    }



    protected abstract void perform(Runnable action);

    protected void queue(Runnable runnable) {
        synchronized(eventList) {
            eventList.add(runnable);
            eventList.notifyAll();
        }
    }

    public void waitForIdle() {
    	final Object localLock = new Object();
    	synchronized (localLock) {
	        synchronized (eventList) {
	            afterEmptyEventList.add(runnableOnLock(localLock));
	            eventList.notifyAll();
	        }
	        
	        try {
				localLock.wait();
			} catch (InterruptedException e) {}
    	}
    }

	private Runnable runnableOnLock(final Object localLock) {
		return new Runnable() {
			public void run() {
				synchronized (localLock) {
					localLock.notifyAll();
				}
			}
            public String toString() {
                return "Idler";
            }
		};
	}
}
