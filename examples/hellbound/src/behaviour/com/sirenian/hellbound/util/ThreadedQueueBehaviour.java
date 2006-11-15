package com.sirenian.hellbound.util;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.engine.ThreadedEngineQueue;

public class ThreadedQueueBehaviour extends UsingMiniMock {
    
    public void shouldInvokeOtherRunnablesAfterQueueIsEmptied() throws Exception {
        
        Mock runnable = mock(Runnable.class);
        Mock listener = mock(GameRequestListener.class);
        
        listener.expects("requestStartGame").times(2);
        runnable.expects("run").after(listener, "requestStartGame");
        
        final ThreadedEngineQueue queue = new ThreadedEngineQueue();
        queue.setGameRequestDelegate((GameRequestListener) listener);
        
        queue.requestStartGame();
        queue.invokeAndWait((Runnable) runnable);
        queue.requestStartGame();

        synchronized(this) { wait(20); }
        
        verifyMocks();
    }
    
    public void shouldStopQueueOnRequest() throws Exception {

        StubRunnable runnable = new StubRunnable();
        
        final ThreadedEngineQueue queue = new ThreadedEngineQueue();
        queue.stop();
        
        queue.invokeAndWait(runnable);
        
        synchronized(this) { wait(20); }
        
        ensureThat(!runnable.wasRun);
    }
    
    private class StubRunnable implements Runnable {
        private boolean wasRun;
        public void run() {
            wasRun = true;
        }
    }


}
