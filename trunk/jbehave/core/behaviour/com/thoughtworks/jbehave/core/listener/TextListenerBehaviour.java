/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listener;

import java.io.StringWriter;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.behaviour.BehaviourMethod;
import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.listener.TextListener;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.result.BehaviourMethodResult;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.util.Timer;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListenerBehaviour extends UsingMiniMock {
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
    private TextListener listener;
    private StatefulTimer timer;
    private BehaviourMethod behaviourMethod;
    
    public static class FooBehaviour {
        public void shouldDoSomething() {}
    }

    public void setUp() throws Exception {
        writer = new StringWriter();
        timer = new StatefulTimer();
        listener = new TextListener(writer, timer);
        behaviourMethod = new BehaviourMethod(new FooBehaviour(), FooBehaviour.class.getMethod("shouldDoSomething", null));
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
        Result succeeded = new BehaviourMethodResult(behaviourMethod);
        
        // when...
        listener.gotResult(succeeded);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nMethods: 1");
    }

    public void shouldSummarizeTwoSuccessfulMethods() throws Exception {
        // given...
        Result succeeded = new BehaviourMethodResult(behaviourMethod);
        
        // when...
        listener.gotResult(succeeded);
        listener.gotResult(succeeded);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nMethods: 2");
    }
    
    public void shouldPrintSummaryWhenMethodFails() throws Exception {
        // given...
        Result failed = new BehaviourMethodResult(behaviourMethod, new VerificationException("oops"));
        
        // when...
        listener.gotResult(failed);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nMethods: 1. Failures: 1");
    }

    public void shouldPrintStackTraceWhenMethodFails() throws Exception {
        // given...
        Result failed = new BehaviourMethodResult(behaviourMethod, new VerificationException("oops"));
        
        // expect...
        String expectedShortName = "Foo";
        String expectedFullName = FooBehaviour.class.getName();
        
        // when...
        listener.gotResult(failed);
        listener.printReport();
        
        // then...
        verifyOutputContains("Failures:");
        verifyOutputContains("\n1) " + expectedShortName + " shouldDoSomething [" + expectedFullName + "]:");
        verifyOutputContains("VerificationException");
    }

    public void shouldPrintSummaryWhenMethodThrowsException() throws Exception {
        // given...
        Result threwException = new BehaviourMethodResult(behaviourMethod, new Exception());
        
        // when...
        listener.gotResult(threwException);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nMethods: 1. Failures: 0, Exceptions: 1");
    }

    public void shouldPrintStackTraceWhenMethodThrowsException() throws Exception {
        // given...
        Result threwException = new BehaviourMethodResult(behaviourMethod, new Exception());
        
        // expect...
        String expectedShortName = "Foo";
        String expectedFullName = FooBehaviour.class.getName();
        
        // when...
        listener.gotResult(threwException);
        listener.printReport();
        
        // then...
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains("\n1) " + expectedShortName + " shouldDoSomething [" + expectedFullName + "]:");
        verifyOutputContains("java.lang.Exception");
    }
    
    public void shouldSummarizePendingMethod() throws Exception {
        // given...
        Result pending = new BehaviourMethodResult(behaviourMethod, new PendingException());
        
        // when...
        listener.gotResult(pending);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nPending: 1");
    }
    
    public void shouldStartTimerWhenConstructed() throws Exception {
        // verify...
        Verify.that(timer.isRunning);
    }
}
