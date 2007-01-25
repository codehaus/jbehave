package com.sirenian.hellbound.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.threaded.QueuedObjectHolder;

public class ThreadedQueueBehaviour extends UsingMiniMock {
    
    public void shouldWaitForIdle() throws Exception {
        /// Can't think how to test this.
    }
    
    public void shouldStopQueueWhenRequested() throws Exception {
    	// Can't think how to test this.
    }
    
    public void shouldPassCaughtExceptionsToHandler() throws Exception {
        final QueuedObjectHolder exceptionHolder = new QueuedObjectHolder();
        final RuntimeException exception = new RuntimeException("An exception occurred");
        
        Mock handler = mock(ErrorHandler.class);
        handler.expects("handle").will(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                exceptionHolder.set(args[0]);
                return null;
            }
        });
        
        ThreadedQueue queue = new ThreadedQueue("test queue", (ErrorHandler) handler) {
            protected void perform(Runnable action) {
                action.run();
            }
        };
        
        queue.queue(new Runnable(){ public void run() {
            throw exception;
        }});

        ensureThat(exceptionHolder.get(), is(exception));
    }
}
