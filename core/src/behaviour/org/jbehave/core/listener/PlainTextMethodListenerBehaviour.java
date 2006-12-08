/*
 * Created on 29-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package org.jbehave.core.listener;

import org.jbehave.core.behaviour.BehaviourMethod;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.result.BehaviourMethodResult;
import org.jbehave.core.result.Result;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class PlainTextMethodListenerBehaviour extends PlainTextListenerBehaviourSupport {
    private BehaviourMethod shouldDoSomething;

    public void setUp() throws Exception {
        super.setUp();
        shouldDoSomething = new BehaviourMethod(new FooBehaviour(), FooBehaviour.class.getMethod("shouldDoSomething", null));
    }

    protected PlainTextListener newPlainTextListener() {
        return new PlainTextListener(writer, timer);
    }

    protected Result newSuccessResult() {
        return new BehaviourMethodResult(shouldDoSomething);
    }

    protected Result newFailureResult() {
        return new BehaviourMethodResult(shouldDoSomething, new VerificationException("oops"));
    }

    protected Result newPendingResult() {
        return new BehaviourMethodResult(shouldDoSomething, new PendingException());
    }

    public void shouldPrintStackTraceForFailure() throws Exception {
        // given...
        Result failed = newFailureResult();
        listener.gotResult(failed);

        // when...
        listener.printReport();

        // then...
        ensureThat(writer, contains("Failures:"));
        ensureThat(writer, contains("Foo"));
        ensureThat(writer, contains("VerificationException"));
    }

    public void shouldPrintShortBehaviourClassNameForPending() throws Exception {
        // given...
        Result pending = new PendingResult(shouldDoSomething);
        listener.gotResult(pending);

        // when...
        listener.printReport();

        // then...
        ensureThat(writer, contains("Pending:"));
        ensureThat(writer, contains("Foo"));
    }
}
