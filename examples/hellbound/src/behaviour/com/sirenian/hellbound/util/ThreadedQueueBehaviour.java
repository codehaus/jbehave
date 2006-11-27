package com.sirenian.hellbound.util;

import jbehave.core.Block;
import jbehave.core.minimock.UsingMiniMock;

public class ThreadedQueueBehaviour extends UsingMiniMock {
    
    public void shouldWaitForIdle() throws Exception {
        /// Can't think how to test this.
    }
    
    public void shouldStopQueueWhenRequested() throws Exception {
    	// Can't think how to test this.
    }
    
    public void shouldRethrowCaughtExceptionsInCallingThreadThenTerminate() throws Exception {
        final ThreadedQueue queue = new ThreadedQueue("test queue") {
            protected void perform(Runnable action) {
                throw new RuntimeException("An exception occurred");
            }
        };
        
        ensureThrows(Throwable.class, new Block() {
            public void run() throws Exception {
                queue.queue(new Runnable(){ public void run() {}});
            }
        });
    }
}
