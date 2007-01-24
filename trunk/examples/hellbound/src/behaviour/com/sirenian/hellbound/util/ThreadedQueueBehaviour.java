package com.sirenian.hellbound.util;

import org.jbehave.core.Block;
import org.jbehave.core.minimock.UsingMiniMock;

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
                action.run();
            }
        };
        
        Exception exception = runAndCatch(Throwable.class, new Block() {
            public void run() throws Exception {
                queue.queue(new Runnable(){ public void run() {
                    
                    throw new RuntimeException("An exception occurred");
                }});
            }
        });
        
        ensureThat(exception, isNotNull());
    }
}
