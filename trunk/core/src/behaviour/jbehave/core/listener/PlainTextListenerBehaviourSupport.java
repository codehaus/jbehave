/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.core.listener;

import java.io.StringWriter;

import jbehave.core.Ensure;
import jbehave.core.exception.PendingException;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Matcher;
import jbehave.core.result.Result;
import jbehave.core.util.Timer;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public abstract class PlainTextListenerBehaviourSupport extends UsingMiniMock {
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
    protected abstract Result newPendingResult();
    
    public static class FooBehaviour {
        public void shouldDoSomething() {}
    }

    public void setUp() throws Exception {
        writer = new StringWriter();
        timer = new StatefulTimer();
        listener = newPlainTextListener();
    }
    
    private Matcher contains(final Result.Type expected) {
    	return new CustomMatcher("StringWriter containing <" + expected + ">"){
			public boolean matches(Object arg) {
				return ((StringWriter)arg).toString().indexOf(expected.symbol()) != -1;
			}
    	};
    }

    public void shouldRenderSuccessSymbolForSuccess() throws Exception {
        listener.gotResult(new Result("shouldSucceed", "Container", Result.SUCCEEDED));
        ensureThat(writer, contains(Result.SUCCEEDED));
    }

    public void shouldRenderFailureSymbolForFailure() throws Exception {
        listener.gotResult(new Result("shouldFail", "Container", Result.FAILED));
        ensureThat(writer, contains(Result.FAILED));
    }

    public void shouldRenderPendingSymbolForPending() throws Exception {
        listener.gotResult(new Result("shouldBePending", "Container", new PendingException()));
        ensureThat(writer, contains(Result.PENDING));
    }

    public void shouldSummarizeSingleSuccessfulResult() throws Exception {
        // given...
        Result succeeded = newSuccessResult();
        
        // when...
        listener.gotResult(succeeded);
        listener.printReport();
        
        // then...
        ensureThat(writer, contains("\nTotal: 1"));
    }

    public void shouldSummarizeTwoSuccessfulResults() throws Exception {
        // given...
        Result succeeded = newSuccessResult();
        
        // when...
        listener.gotResult(succeeded);
        listener.gotResult(succeeded);
        listener.printReport();
        
        // then...
        ensureThat(writer, contains("\nTotal: 2"));
    }
    
    public void shouldPrintSummaryForFailure() throws Exception {
        // given...
        Result failed = newFailureResult();
        
        // when...
        listener.gotResult(failed);
        listener.printReport();
        
        // then...
        ensureThat(writer, contains("\nTotal: 1. Failures: 1"));
    }
    
    public void shouldSummarizePendingResult() throws Exception {
        // given...
        Result pending = newPendingResult();
        
        // when...
        listener.gotResult(pending);
        listener.printReport();
        
        // then...
        ensureThat(writer, contains("\nPending: 1"));
    }

    public void shouldStartTimerWhenConstructed() throws Exception {
        // verify...
        Ensure.that(timer.isRunning);
    }
}
