package com.sirenian.hellbound.engine;

import java.util.ArrayList;
import java.util.List;

import com.sirenian.hellbound.util.Logger;

public abstract class ThreadedQueue {

    protected static final Runnable EMPTY_RUNNABLE = new Runnable() {
        public void run() {
        }
    };

    private ArrayList eventList = new ArrayList();
    private ArrayList afterEmptyEventList = new ArrayList();
    private boolean shouldRun = true;

    protected ThreadedQueue(String queueName) {
        Runnable runnable = new Runnable() {
            public void run() {
                synchronized (eventList) {
                    while (shouldRun) {
                        runAllInList(eventList);
                        runAllInList(afterEmptyEventList);
                        waitForNextRequest();
                    }
                }
            }
        };
        Logger.debug(this, "Starting thread for " + queueName);
        new Thread(runnable, queueName).start();
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

    public void invokeAndWait(Runnable runnable) {
        synchronized (eventList) {
            afterEmptyEventList.add(runnable);
            eventList.notifyAll();
        }
    }
    

    public void stop() {
        shouldRun = false;
        synchronized(eventList) {
            eventList.notifyAll();
        }
    }    
}
