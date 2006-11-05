package com.sirenian.hellbound.engine;

import java.util.ArrayList;
import java.util.List;

public class ThreadedQueue {

    protected static final Runnable EMPTY_RUNNABLE = new Runnable() {
        public void run() {
        }
    };

    private ArrayList eventList = new ArrayList();
    private ArrayList afterEmptyEventList = new ArrayList();


    protected void startQueueThread(String threadName) {
        Runnable runnable = new Runnable() {
            public void run() {
                synchronized (eventList) {
                    while (true) {
                        runAllInList(eventList);
                        runAllInList(afterEmptyEventList);
                        waitForNextRequest();
                    }
                }
            }
        };
        new Thread(runnable, threadName).start();
    }

    private void waitForNextRequest() {
        try {
            eventList.wait();
        } catch (InterruptedException e) {
        }
    }

    private void runAllInList(List list) {
        while (!list.isEmpty()) {
            ((Runnable) list.remove(0)).run();
        }
    }

    protected void queue(Runnable runnable) {
        synchronized(eventList) {
            eventList.add(runnable);
            eventList.notifyAll();
        }
    }

    public void invokeAndWait(Runnable runnable) {
        synchronized (eventList) {
            afterEmptyEventList.add(runnable);
        }
    }
}
