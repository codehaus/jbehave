/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listener;

import java.io.StringWriter;

import com.thoughtworks.jbehave.core.Ensure;
import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.minimock.Constraint;
import com.thoughtworks.jbehave.core.minimock.ConstraintSupport;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.util.Timer;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public abstract class PlainTextListenerBehaviour extends UsingMiniMock {
    private static class StatefulTimer extends Timer {
        public boolean isRunning = false;
        public void start() {
            isRunning = true;
        }
        public void stop() {
            isRunning = false;
        }
    }
    
    protected StringWriter writer;
    protected PlainTextListener listener;
    protected StatefulTimer timer;

    protected abstract PlainTextListener newPlainTextListener();
    protected abstract Result newSuccessResult();
    protected abstract Result newFailureResult();
    protected abstract Result newExceptionResult();
    protected abstract Result newPendingResult();
    
    public static class FooBehaviour {
        public void shouldDoSomething() {}
    }

    public void setUp() throws Exception {
        writer = new StringWriter();
        timer = new StatefulTimer();
        listener = newPlainTextListener();
    }
    
    private Constraint contains(final Result.Type expected) {
    	return new ConstraintSupport(){
			public boolean matches(Object arg) {
				return ((StringWriter)arg).toString().indexOf(expected.symbol()) != -1;
			}
			public String toString() {
				return "StringWriter containing <" + expected + ">";
			}
    	};
    }

    public void shouldRenderSuccessSymbolForSuccess() throws Exception {
        listener.gotResult(new Result("shouldSucceed", "Container", Result.SUCCEEDED));
        ensureThat(writer, contains(Result.SUCCEEDED));
    }

    public void shouldRenderExceptionSymbolForException() throws Exception {
        listener.gotResult(new Result("shouldThrowException", "Container", Result.THREW_EXCEPTION));
        ensureThat(writer, contains(Result.THREW_EXCEPTION));
    }

    public void shouldRenderFailureSymbolForFailure() throws Exception {
        listener.gotResult(new Result("shouldFail", "Container", Result.FAILED));
        ensureThat(writer, contains(Result.FAILED));
    }

    public void shouldRenderPendingSymbolForPending() throws Exception {
        listener.gotResult(new Result("shouldBePending", "Container", new PendingException()));
        ensureThat(writer, contains(Result.PENDING));
    }

    protected void verifyOutputContains(String expected) {
        String output = writer.toString();
        Ensure.that("Output should contain: [" + expected + "] but was:\n>>>\n" + output + "\n<<<", output.indexOf(expected) != -1);
    }
    
    public void shouldSummarizeSingleSuccessfulResult() throws Exception {
        // given...
        Result succeeded = newSuccessResult();
        
        // when...
        listener.gotResult(succeeded);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nTotal: 1");
    }

    public void shouldSummarizeTwoSuccessfulResults() throws Exception {
        // given...
        Result succeeded = newSuccessResult();
        
        // when...
        listener.gotResult(succeeded);
        listener.gotResult(succeeded);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nTotal: 2");
    }
    
    public void shouldPrintSummaryForFailure() throws Exception {
        // given...
        Result failed = newFailureResult();
        
        // when...
        listener.gotResult(failed);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nTotal: 1. Failures: 1");
    }
    
    public void shouldPrintSummaryWhenExceptionThrown() throws Exception {
        // given...
        Result threwException = newExceptionResult();
        
        // when...
        listener.gotResult(threwException);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nTotal: 1. Failures: 0, Exceptions: 1");
    }
    
    public void shouldSummarizePendingResult() throws Exception {
        // given...
        Result pending = newPendingResult();
        
        // when...
        listener.gotResult(pending);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nPending: 1");
    }

    public void shouldStartTimerWhenConstructed() throws Exception {
        // verify...
        Ensure.that(timer.isRunning);
    }
}
