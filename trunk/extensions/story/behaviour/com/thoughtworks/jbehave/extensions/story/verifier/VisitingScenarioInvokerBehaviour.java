package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;

/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitingScenarioInvokerBehaviour extends UsingMiniMock {
    
    private VisitingScenarioInvoker invoker;
    private Mock scenario;
    private Mock listener1;
    private Mock listener2;
    private Environment environmentStub;
    
    public void setUp() {
        environmentStub = (Environment)stub(Environment.class);
        invoker = new VisitingScenarioInvoker(environmentStub);
        scenario = mock(Scenario.class);
        listener1 = mock(ResultListener.class, "listener1");
        listener2 = mock(ResultListener.class, "listener2");
    }
    
    public void shouldDispatchItselfAsVisitorToScenario() throws Exception {
        // given...

        // expect...
        scenario.expects("accept").with(invoker);
        
        // when...
        invoker.invoke((Scenario)scenario);
        
        // then...
        verifyMocks();
    }
    
    public void shouldTellGivenToSetUpEnvironment() throws Exception {
        // given...
        Mock given = mock(Given.class);
        
        // expect...
        given.expects("setUp").with(environmentStub);
        
        // when...
        invoker.visit((Given)given);
        
        // then...
        verifyMocks();
    }

    public void shouldSetExpectationInEnvironmentBeforeEventOccurs() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        
        // expect...
        expectation.expects("setExpectationIn").with(environmentStub);
        
        // when...
        invoker.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Mock event = mock(Event.class);
        
        // expect...
        event.expects("occurIn").with(environmentStub);
        
        // when...
        invoker.visit((Event)event);
        
        // then...
        verifyMocks();
    }
    
    public void shouldVerifyExpectationInEnvironmentAfterEventOccurs() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        Event eventStub = (Event) stub(Event.class);

        // expect...
        expectation.expects("verify").with(same(environmentStub));
        
        // when...
        invoker.visit(eventStub);
        invoker.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }

    public void shouldThrowNestedVerificationExceptionFromVisitWhenGivenThrowsException() throws Exception {
        // given...
        Mock given = mock(Given.class);
        Exception cause = new Exception("oops");
        given.expects("setUp").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visit((Given) given);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowNestedVerificationExceptionFromVisitWhenEventThrowsException() throws Exception {
        // given...
        Mock event = mock(Event.class);
        Exception cause = new Exception("oops");
        event.expects("occurIn").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visit((Event) event);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowNestedVerificationExceptionWhenExpectationThrowsExceptionBeforeEvent() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        RuntimeException cause = new RuntimeException("oops");
        expectation.expects("setExpectationIn").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visit((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowNestedVerificationExceptionIfExpectationFailsAfterTheEvent() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        Event eventStub = (Event)stub(Event.class);
        Exception cause = new VerificationException("oops");
        invoker.visit(eventStub); // we are visiting the verifier after the event
        
        // expect...
        expectation.expects("verify").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visit((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldReturnSuccessfulResultWhenScenarioSucceeds() throws Exception {
        // expect...
        scenario.expects("accept");
        
        // when...
        Result result = invoker.invoke((Scenario)scenario);
        
        // then...
        Verify.that(result.succeeded());
    }
    
    public void shouldReturnFailureResultWhenScenarioFails() throws Exception {
        // expect...
        Exception cause = new Exception("oops");
        scenario.expects("accept").will(throwException(new NestedVerificationException(cause)));
        
        // when...
        Result result = invoker.invoke((Scenario)scenario);
        
        // then...
        Verify.identical(cause, result.cause());
    }
}
