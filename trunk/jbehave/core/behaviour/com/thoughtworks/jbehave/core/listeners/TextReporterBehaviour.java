/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.io.StringWriter;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourClass;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.Verifier;
import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.util.Timer;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextReporterBehaviour extends UsingMiniMock {
    private static class StatefulTimer extends Timer {
        public boolean isRunning = false;
        public void start() {
            isRunning = true;
        }
        public void stop() {
            isRunning = false;
        }
    }
    
    private StringWriter writer;
    private TextReporter listener;
    private StatefulTimer timer;

    public void setUp() throws Exception {
        writer = new StringWriter();
        timer = new StatefulTimer();
        listener = new TextReporter(writer, timer);
    }

    public void shouldRenderSuccessSymbolForSuccess() throws Exception {
        listener.gotResult(new Result("shouldSucceed", Result.SUCCEEDED));
        Verify.equal(Result.SUCCEEDED.symbol(), writer.toString());
    }

    public void shouldRenderExceptionSymbolForException() throws Exception {
        listener.gotResult(new Result("shouldThrowException", Result.THREW_EXCEPTION));
        Verify.equal(Result.THREW_EXCEPTION.symbol(), writer.toString());
    }

    public void shouldRenderFailureSymbolForFailure() throws Exception {
        listener.gotResult(new Result("shouldFail", Result.FAILED));
        Verify.equal(Result.FAILED.symbol(), writer.toString());
    }

    public void shouldRenderPendingSymbolForPending() throws Exception {
        listener.gotResult(new Result("shouldBePending", new PendingException()));
        Verify.equal(Result.PENDING.symbol(), writer.toString());
    }

    private void verifyOutputContains(String expected) {
        String output = writer.toString();
        Verify.that("Output should contain: [" + expected + "] but was:\n>>>\n" + output + "\n<<<", output.indexOf(expected) != -1);
    }
    
    public void shouldSummarizeSingleSuccessfulMethod() throws Exception {
        // given...
        Visitable behaviour = (Visitable) stub(Behaviour.class);
        Result succeeded = new Result("shouldDoX", Result.SUCCEEDED);
        
        // when...
        listener.before(behaviour);
        listener.gotResult(succeeded);
        listener.after(behaviour);
        
        // then...
        verifyOutputContains("\nMethods: 1");
    }

    public void shouldSummarizeTwoSuccessfulMethods() throws Exception {
        // given...
        Result succeeded = new Result("shouldDoX", Result.SUCCEEDED);
        Visitable visitableClass = (Visitable) stub(Behaviour.class);
        
        // when...
        listener.before(visitableClass);
        listener.gotResult(succeeded);
        listener.gotResult(succeeded);
        listener.after(visitableClass);
        
        // then...
        verifyOutputContains("\nMethods: 2");
    }
    
    public static class FooBehaviour {}

    public void shouldPrintSummaryWhenMethodFails() throws Exception {
        // given...
        Visitable behaviour = new BehaviourClass(FooBehaviour.class, (Verifier)stub(Verifier.class));
        Result failed = new Result("shouldDoX", new VerificationException("oops"));
        
        // when...
        listener.before(behaviour);
        listener.gotResult(failed);
        listener.after(behaviour);
        
        // then...
        verifyOutputContains("\nMethods: 1. Failures: 1");
    }

    public void shouldPrintStackTraceWhenMethodFails() throws Exception {
        // given...
        Visitable behaviour = new BehaviourClass(FooBehaviour.class, (Verifier)stub(Verifier.class));
        Result failed = new Result("shouldDoX", new VerificationException("oops"));
        
        // expect...
        String expectedShortName = "Foo";
        String expectedFullName = FooBehaviour.class.getName();
        
        // when...
        listener.before(behaviour);
        listener.gotResult(failed);
        listener.after(behaviour);
        
        // then...
        verifyOutputContains("Failures:");
        verifyOutputContains("\n1) " + expectedShortName + " shouldDoX [" + expectedFullName + "]:");
        verifyOutputContains("VerificationException");
    }

    public void shouldPrintSummaryWhenMethodThrowsException() throws Exception {
        // given...
        Visitable behaviour = new BehaviourClass(FooBehaviour.class, (Verifier)stub(Verifier.class));
        Result threwException = new Result("shouldDoX", new Exception());
        
        // when...
        listener.before(behaviour);
        listener.gotResult(threwException);
        listener.after(behaviour);
        
        // then...
        verifyOutputContains("\nMethods: 1. Failures: 0, Exceptions: 1");
    }

    public void shouldPrintStackTraceWhenMethodThrowsException() throws Exception {
        // given...
        Visitable behaviour = new BehaviourClass(FooBehaviour.class, (Verifier)stub(Verifier.class));
        Result threwException = new Result("shouldDoX", new Exception());
        
        // expect...
        String expectedShortName = "Foo";
        String expectedFullName = FooBehaviour.class.getName();
        
        // when...
        listener.before(behaviour);
        listener.gotResult(threwException);
        listener.after(behaviour);
        
        // then...
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains("\n1) " + expectedShortName + " shouldDoX [" + expectedFullName + "]:");
        verifyOutputContains("java.lang.Exception");
    }
    
    public void shouldSummarizePendingMethod() throws Exception {
        // given...
        Visitable behaviour = new BehaviourClass(FooBehaviour.class, (Verifier)stub(Verifier.class));
        Result pending = new Result("shouldDoX", new PendingException());
        
        // when...
        listener.before(behaviour);
        listener.gotResult(pending);
        listener.after(behaviour);
        
        // then...
        verifyOutputContains("\nPending: 1");
    }
    
    public void shouldEnsureTimerIsRunningWhenBehaviourClassVerificationStarts() throws Exception {
        // given...
        Visitable behaviour = (Visitable)stub(Behaviour.class);
        
        // when...
        listener.before(behaviour);
        
        // verify...
        Verify.that(timer.isRunning);
    }
    
    public void shouldStopTimerWhenBehaviourClassVerificationEnds() throws Exception {
        // given...
        Visitable behaviour = (Visitable)stub(Behaviour.class);
        listener.before(behaviour);
        
        // when...
        listener.after(behaviour);
        
        // verify...
        Verify.not(timer.isRunning);
    }
    
    public void shouldNotStartTimerAgainWhenNestedBehaviourClassVerificationStarts() throws Exception {
        // given...
        Visitable outer = (Visitable)stub(Behaviour.class);
        Visitable inner = (Visitable)stub(Behaviour.class);
        
        listener.before(outer);
        timer.isRunning = false; // reset timer
        
        // when...
        listener.before(inner);
        
        // verify...
        Verify.not(timer.isRunning);
    }
    
    public void shouldNotStopTimerWhenNestedBehaviourClassVerificationEnds() throws Exception {
        // given...
        Visitable outer = (Visitable)stub(Behaviour.class);
        Visitable inner = (Visitable)stub(Behaviour.class);
        
        listener.before(outer);
        listener.before(inner);
        
        // when...
        listener.after(inner);
        
        // verify...
        Verify.that(timer.isRunning); // hasn't been stopped
    }
    
    public void shouldStopTimerWhenOuterBehaviourClassVerificationEnds() throws Exception {
        // given...
        Visitable outer = (Visitable)stub(Behaviour.class);
        Visitable inner = (Visitable)stub(Behaviour.class);
        
        listener.before(outer);
        listener.before(inner);
        listener.after(inner);
        
        // when...
        listener.after(outer);
        
        // verify...
        Verify.not(timer.isRunning); // hasn't been stopped
    }
}
