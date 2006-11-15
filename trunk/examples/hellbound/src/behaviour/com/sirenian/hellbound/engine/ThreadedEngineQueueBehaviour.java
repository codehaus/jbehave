package com.sirenian.hellbound.engine;

import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.util.ThreadedQueueBehaviour;

public class ThreadedEngineQueueBehaviour extends ThreadedQueueBehaviour {
    
    public void shouldPassAlongGameRequests() throws Exception {
        
        Mock listener = mock(GameRequestListener.class);
        listener.expects("requestStartGame");
        listener.expects("requestDropGlyph");
        
        final ThreadedEngineQueue queue = new ThreadedEngineQueue();
        queue.setGameRequestDelegate((GameRequestListener) listener);
        queue.requestStartGame();
        queue.requestDropGlyph();
        
        synchronized(this) { wait(20); }
        // needs to be long enough for request queue to start up and pass along the request
        
        verifyMocks();
    }

    public void shouldHandleRequestsSilentlyIfListenersNotSet() {
        final ThreadedEngineQueue queue = new ThreadedEngineQueue();
        queue.requestStartGame();        
    }

}
