package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.minimock.Constraint;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;

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
public class VisitingScenarioVerifierBehaviour extends UsingMiniMock {
    
    private VisitingScenarioVerifier verifier;
    private Mock scenario;
    private Mock listener1;
    private Mock listener2;
    private Environment environmentStub;
    
    public void setUp() {
        environmentStub = (Environment)stub(Environment.class);
        verifier = new VisitingScenarioVerifier(environmentStub);
        scenario = mock(Scenario.class);
        listener1 = mock(ResultListener.class, "listener1");
        listener2 = mock(ResultListener.class, "listener2");
    }
    
    public void shouldDispatchItselfAsVisitorToScenario() throws Exception {
        // given...

        // expect...
        scenario.expects("accept").with(verifier);
        scenario.stubsEverythingElse();
        
        // when...
        verifier.verifyScenario((Scenario)scenario);
        
        // then...
        verifyMocks();
    }
    
    public void shouldTellGivenToSetUpEnvironment() throws Exception {
        // given...
        Mock given = mock(Given.class);
        
        // expect...
        given.expects("setUp").with(environmentStub);
        given.stubsEverythingElse();
        
        // when...
        verifier.visit((Given)given);
        
        // then...
        verifyMocks();
    }

    public void shouldSetExpectationInEnvironmentBeforeEventOccurs() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        
        // expect...
        expectation.expects("setExpectationIn").with(environmentStub);
        expectation.stubsEverythingElse();
        
        // when...
        verifier.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Mock event = mock(Event.class);
        
        // expect...
        event.expects("occurIn").with(environmentStub);
        event.stubsEverythingElse();
        
        // when...
        verifier.visit((Event)event);
        
        // then...
        verifyMocks();
    }
    
    public void shouldVerifyExpectationInEnvironmentAfterEventOccurs() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        Event eventStub = (Event) stub(Event.class);

        // expect...
        expectation.expects("verify").with(same(environmentStub));
        expectation.stubsEverythingElse();
        
        // when...
        verifier.visit(eventStub);
        verifier.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }

    public void shouldThrowNestedVerificationExceptionFromVisitIfGivenThrowsException() throws Exception {
        // given...
        Mock given = mock(Given.class);
        Exception cause = new Exception("oops");
        given.expects("setUp").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            verifier.visit((Given) given);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowNestedVerificationExceptionFromVisitIfEventThrowsException() throws Exception {
        // given...
        Mock event = mock(Event.class);
        Exception cause = new Exception("oops");
        event.expects("occurIn").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            verifier.visit((Event) event);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowNestedVerificationExceptionIfExpectationThrowsExceptionBeforeEvent() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        RuntimeException cause = new RuntimeException("oops");
        expectation.expects("setExpectationIn").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            verifier.visit((Expectation) expectation);
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
        verifier.visit(eventStub); // we are visiting the verifier after the event
        
        // expect...
        expectation.expects("verify").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            verifier.visit((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
        
        // then
        verifyMocks();
    }
    
    /** parameter should represent a succesful ScenarioResult */
    private Constraint successfulScenarioResult() {
        return new Constraint() {
            public boolean matches(Object arg) {
                return (arg instanceof ScenarioResult && ((ScenarioResult)arg).succeeded());
            }
        };
    }
    
    public void shouldNotifyListenersWhenScenarioSucceeds() throws Exception {
        // given...
        verifier.addListener((ResultListener)listener1);
        verifier.addListener((ResultListener)listener2);
        scenario.stubs("accept").with(verifier);
        scenario.stubsEverythingElse();

        // expect...
        listener1.expects("gotResult").with(successfulScenarioResult());
        listener2.expects("gotResult").with(successfulScenarioResult());
        
        // when...
        verifier.verifyScenario((Scenario) scenario);
        
        // verify...
        verifyMocks();
    }
    
    /** parameter should represent a ScenarioResult with a given Exception */
    private Constraint exceptionScenarioResult(final Exception cause) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return (arg instanceof ScenarioResult && ((ScenarioResult)arg).cause() == cause);
            }
        };
    }
    
    public void shouldNotifyListenersWithCauseWhenScenarioThrowsException() throws Exception {
        // given...
        verifier.addListener((ResultListener)listener1);
        verifier.addListener((ResultListener)listener2);
        Exception cause = new Exception("oops");
        scenario.stubs("accept").with(verifier).will(throwException(new NestedVerificationException(cause)));
        scenario.stubsEverythingElse();

        // expect...
        listener1.expects("gotResult").with(exceptionScenarioResult(cause));
        listener2.expects("gotResult").with(exceptionScenarioResult(cause));
        
        // when...
        verifier.verifyScenario((Scenario)scenario);
        
        // then...
        verifyMocks();
    }
    
    /** parameter should represent a ScenarioResult with a given Exception */
    private Constraint scenarioResultUsingMocks() {
        return new Constraint() {
            public boolean matches(Object arg) {
                return (arg instanceof ScenarioResult && ((ScenarioResult)arg).succeededUsingMocks());
            }
        };
    }
    
    public void shouldSetResultTypeIfScenarioSucceedsButGivenUsesMocks() throws Exception {
        // given...
        verifier.addListener((ResultListener) listener1);
        Mock givenThatUsesMocks = mock(Given.class);
        givenThatUsesMocks.stubs("containsMocks").will(returnValue(true));
        givenThatUsesMocks.stubsEverythingElse();
        verifier.visit((Given)givenThatUsesMocks);
        scenario.stubsEverythingElse();

        // expect...
        listener1.expects("gotResult").with(scenarioResultUsingMocks());
        
        // when...
        verifier.verifyScenario((Scenario) scenario);
        
        // then...
        verifyMocks();
    }
}
