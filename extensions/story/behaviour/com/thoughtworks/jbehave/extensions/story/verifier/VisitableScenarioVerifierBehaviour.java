package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.minimock.Constraint;
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
public class VisitableScenarioVerifierBehaviour extends UsingMiniMock {
    
    public void shouldDispatchItselfAsVisitorToScenario() throws Exception {
        // given...
        Environment environmentStub = (Environment)stub(Environment.class);
        ScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock scenario = mock(Scenario.class);
        scenario.stubs("getDescription").withNoArguments();

        // expect...
        scenario.expects("accept").with(verifier);
        
        // when...
        verifier.verifyScenario((Scenario)scenario);
        
        // then...
        verifyMocks();
    }
    
    public void shouldTellGivenToSetUpEnvironment() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock given = mock(Given.class);
        
        given.expects("setUp").with(environmentStub);
        given.stubs("containsMocks").will(returnValue(false));
        
        // when...
        verifier.visit((Given)given);
        
        // then...
        verifyMocks();
    }

    public void shouldSetExpectationInEnvironmentBeforeEventOccurs() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock expectation = mock(Expectation.class);
        
        // expect...
        expectation.expects("setExpectationIn").with(environmentStub);
        
        // when...
        verifier.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock event = mock(Event.class);
        
        // expect...
        event.expects("occurIn").with(environmentStub);
        
        // when...
        verifier.visit((Event)event);
        
        // then...
        verifyMocks();
    }
    
    public void shouldVerifyExpectationInEnvironmentAfterEventOccurs() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock expectation = mock(Expectation.class);
        Event eventStub = (Event) stub(Event.class);

        // expect...
        expectation.expects("verify").with(same(environmentStub));
        
        // when...
        verifier.visit(eventStub);
        verifier.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }
    
    private Constraint successfulResult() {
        return new Constraint() {
            public boolean matches(Object arg) {
                return (arg instanceof Result && ((Result)arg).succeeded());
            }
        };
    }
    
    public void shouldNotifyListenersWhenScenarioSucceeds() throws Exception {
        // given...
        Environment environmentStub = (Environment)stub(Environment.class);
        ScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock scenario = mock(Scenario.class);
        Mock listener1 = mock(ResultListener.class, "listener1");
        Mock listener2 = mock(ResultListener.class, "listener2");
        verifier.addListener((ResultListener)listener1);
        verifier.addListener((ResultListener)listener2);
        scenario.stubs("getDescription").withNoArguments().will(returnValue("scenario"));

        // expect...
        scenario.expects("accept").with(verifier).isVoid();
        listener1.expects("gotResult").with(successfulResult()).isVoid();
        listener2.expects("gotResult").with(successfulResult()).isVoid();
        
        // when...
        verifier.verifyScenario((Scenario) scenario);
        
        // then...
        verifyMocks();
    }
    
    public void shouldNotifyListenersWhenGivenThrowsException() throws Exception {
        // given...
        Verify.pending();

        // expect...
        // when...
        // then...
    }

    public void shouldWrapAndRethrowExceptionIfGivenThrowsException() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock given = mock(Given.class);
        given.expects("setUp").with(environmentStub).will(throwException(new Exception("oops")));
        
        // when...
        try {
            verifier.visit((Given) given);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowVerxficationExceptionIfEventThrowsException() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock event = mock(Event.class);
        event.expects("occurIn").with(environmentStub).will(throwException(new Exception("oops")));
        
        // when...
        try {
            verifier.visit((Event) event);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowVerificationExceptionIfExpectationThrowsExceptionBeforeEvent() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock expectation = mock(Expectation.class);
        expectation.expects("setExpectationIn").with(environmentStub).will(throwException(new RuntimeException("oops")));
        
        // when...
        try {
            verifier.visit((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldPropagateVerificationExceptionIfExpectationFailsAfterTheEvent() throws Exception {
        // given...
        Environment environmentStub = (Environment) stub(Environment.class);
        VisitingScenarioVerifier verifier = new VisitingScenarioVerifier(environmentStub);
        Mock expectation = mock(Expectation.class);
        Event eventStub = (Event)stub(Event.class);
        VerificationException exceptionFromExpectation = new VerificationException("oops");
        verifier.visit(eventStub);
        
        // expect...
        expectation.expects("verify").with(environmentStub).will(throwException(exceptionFromExpectation));
        
        // when...
        try {
            verifier.visit((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (VerificationException expected) {
            Verify.identical(exceptionFromExpectation, expected);
        }
        
        // then
        verifyMocks();
    }
}
