package com.sirenian.hellbound.engine;

import java.util.ArrayList;
import java.util.List;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public class ThreadedRequestQueue implements RequestQueue {
    
    private static final Runnable EMPTY_RUNNABLE = new Runnable() {
        public void run() {}
    };
    
    private ArrayList eventList = new ArrayList();
    private ArrayList afterEmptyEventList = new ArrayList();
    
    // Putting these to "null" here means that we don't have to worry about the
    // order in which engine and gui are initialised - no null pointers, even
    // if the thread hasn't quite caught up yet!
    private Runnable startGameRequest = EMPTY_RUNNABLE;

    public ThreadedRequestQueue() {        

        Runnable runnable = new Runnable() {
            public void run() {
                synchronized(eventList) {
                    while (true)  {
                        runAllInList(eventList);
                        runAllInList(afterEmptyEventList);
                        waitForNextRequest();
                    }
                }
            }
        };
        new Thread(runnable, "EngineRequestQueue").start();            
    }

    public void requestStart() {
        synchronized(eventList) {
            eventList.add(startGameRequest);
            eventList.notifyAll();
        }
    }

    public void invokeAndWait(Runnable runnable) {
        synchronized(eventList) {
            afterEmptyEventList.add(runnable);
        }
    }

    private void waitForNextRequest() {
        try {
            eventList.wait();
        } catch (InterruptedException e) {}
    }

    private void runAllInList(List list) {
        while (!list.isEmpty()) {
            ((Runnable)list.remove(0)).run();
        }
    }

    public void setGameRequestListener(final GameRequestListener listener) {
        startGameRequest = new Runnable() { 
            public void run() { listener.requestStart(); }};    
    }
}
