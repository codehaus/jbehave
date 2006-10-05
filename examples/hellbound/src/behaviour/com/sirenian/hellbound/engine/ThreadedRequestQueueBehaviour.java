package com.sirenian.hellbound.engine;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public class ThreadedRequestQueueBehaviour extends UsingMiniMock {
    
    public void shouldPassAlongGameRequests() throws Exception {
        
        Mock listener = mock(GameRequestListener.class);
        listener.expects("requestStart");
        
        final ThreadedRequestQueue queue = new ThreadedRequestQueue();
        queue.setGameRequestListener((GameRequestListener) listener);
        queue.requestStart();
        
        synchronized(this) { wait(20); }
        // needs to be long enough for request queue to start up and pass along the request
        
        verifyMocks();
    }

    public void shouldHandleRequestsSilentlyIfListenersNotSet() {
        final ThreadedRequestQueue queue = new ThreadedRequestQueue();
        queue.requestStart();        
    }
    
    public void shouldInvokeOtherRunnablesAfterQueueIsEmptied() throws Exception {
        
        Mock runnable = mock(Runnable.class);
        Mock listener = mock(GameRequestListener.class);
        
        listener.expects("requestStart").times(2);
        runnable.expects("run").after(listener, "requestStart");
        
        final ThreadedRequestQueue queue = new ThreadedRequestQueue();
        queue.setGameRequestListener((GameRequestListener) listener);
        
        queue.requestStart();
        queue.invokeAndWait((Runnable) runnable);
        queue.requestStart();

        synchronized(this) { wait(20); }
        
        verifyMocks();
    }
}
