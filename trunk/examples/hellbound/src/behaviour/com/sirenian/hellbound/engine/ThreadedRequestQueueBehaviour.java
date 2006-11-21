package com.sirenian.hellbound.engine;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public class ThreadedRequestQueueBehaviour extends UsingMiniMock {
    
    public void shouldPassAlongGameRequests() throws Exception {
        
        Mock listener = mock(GameRequestListener.class);
        listener.expects("requestStartGame");
        
        final ThreadedEngineQueue queue = new ThreadedEngineQueue();
        queue.setGameRequestDelegate((GameRequestListener) listener);
        queue.requestStartGame();
        
        synchronized(this) { wait(20); }
        // needs to be long enough for request queue to start up and pass along the request
        
        verifyMocks();
    }

    public void shouldHandleRequestsSilentlyIfListenersNotSet() {
        final ThreadedEngineQueue queue = new ThreadedEngineQueue();
        queue.requestStartGame();        
    }
    
    public void shouldWaitForIdle() throws Exception {
        // Can't think how to test this any more.
    }
}
