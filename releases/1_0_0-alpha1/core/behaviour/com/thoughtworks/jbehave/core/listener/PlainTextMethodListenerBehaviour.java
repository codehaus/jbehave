/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listener;

import com.thoughtworks.jbehave.core.behaviour.BehaviourMethod;
import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.result.BehaviourMethodResult;
import com.thoughtworks.jbehave.core.result.Result;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class PlainTextMethodListenerBehaviour extends PlainTextListenerBehaviour {
    private BehaviourMethod behaviourMethod;

    public void setUp() throws Exception {
        super.setUp();
        behaviourMethod = new BehaviourMethod(new FooBehaviour(), FooBehaviour.class.getMethod("shouldDoSomething", null));
    }

    protected PlainTextListener newPlainTextListener() {
        return new PlainTextMethodListener(writer, timer);
    }

    protected Result newSuccessResult() {
        return new BehaviourMethodResult(behaviourMethod);
    }

    protected Result newFailureResult() {
        return new BehaviourMethodResult(behaviourMethod, new VerificationException("oops"));
    }

    protected Result newExceptionResult() {
        return new BehaviourMethodResult(behaviourMethod, new Exception());
    }
    
    protected Result newPendingResult() {
        return new BehaviourMethodResult(behaviourMethod, new PendingException());
    }
    
    public void shouldPrintStackTraceForFailure() throws Exception {
        // given...
        Result failed = newFailureResult();
        
        // expect...
        String expectedShortName = "Foo";
        String expectedFullName = FooBehaviour.class.getName();
        
        // when...
        listener.gotResult(failed);
        listener.printReport();
        
        // then...
        verifyOutputContains("Failures:");
        verifyOutputContains(expectedShortName);
        verifyOutputContains(expectedFullName);
        verifyOutputContains("VerificationException");
    }
    
    public void shouldPrintStackTraceWhenExceptionThrown() throws Exception {
        // given...
        Result threwException = newExceptionResult();
        
        // expect...
        String expectedShortName = "Foo";
        String expectedFullName = FooBehaviour.class.getName();
        
        // when...
        listener.gotResult(threwException);
        listener.printReport();
        
        // then...
        verifyOutputContains("Exceptions Thrown:");
        verifyOutputContains(expectedShortName);
        verifyOutputContains(expectedFullName);
        verifyOutputContains("java.lang.Exception");
    }
}
