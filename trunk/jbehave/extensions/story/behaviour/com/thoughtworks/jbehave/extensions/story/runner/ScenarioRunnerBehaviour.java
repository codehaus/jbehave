package com.thoughtworks.jbehave.extensions.story.runner;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.minimock.UsingMiniMock;

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
public class ScenarioRunnerBehaviour extends UsingMiniMock {
    
    public void shouldTellGivenToSetUpEnvironment() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock given = mock(Given.class);
        
        given.expects("setUp").with(environment);
        given.stubs("containsMocks").will(returnValue(false));
        
        // when...
        runner.visit((Given)given);
        
        // then...
        verifyMocks();
    }
    
    public void shouldSetExpectationInEnvironmentBeforeEventOccurs() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock expectation = mock(Expectation.class);
        
        // expect...
        expectation.expects("setExpectationIn").with(environment);
        
        // when...
        runner.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock event = mock(Event.class);
        
        // expect...
        event.expects("occurIn").with(environment);
        
        // when...
        runner.visit((Event)event);
        
        // then...
        verifyMocks();
    }
    
    public void shouldVerifyExpectationInEnvironmentAfterEventOccurs() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock expectation = mock(Expectation.class);
        Event eventStub = (Event) stub(Event.class);

        // expect...
        expectation.expects("verify").with(same(environment));
        
        // when...
        runner.visit(eventStub);
        runner.visit((Expectation)expectation);
        
        // then...
        verifyMocks();
    }
    
    public void shouldWrapAndRethrowExceptionIfGivenThrowsException() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock given = mock(Given.class);
        given.expects("setUp").with(environment).will(throwException(new Exception("oops")));
        
        // when...
        try {
            runner.visit((Given) given);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowVerxficationExceptionIfEventThrowsException() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock event = mock(Event.class);
        event.expects("occurIn").with(environment).will(throwException(new Exception("oops")));
        
        // when...
        try {
            runner.visit((Event) event);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldThrowVerificationExceptionIfExpectationThrowsExceptionBeforeEvent() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock expectation = mock(Expectation.class);
        expectation.expects("setExpectationIn").with(environment).will(throwException(new RuntimeException("oops")));
        
        // when...
        try {
            runner.visit((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
        }
        
        // then
        verifyMocks();
    }
    
    public void shouldPropagateVerificationExceptionIfExpectationFailsAfterTheEvent() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        ScenarioRunner runner = new ScenarioRunner(environment);
        Mock expectation = mock(Expectation.class);
        Event eventStub = (Event)stub(Event.class);
        VerificationException fromExpectation = new VerificationException("oops");
        runner.visit(eventStub);
        
        // expect...
        expectation.expects("verify").with(environment).will(throwException(fromExpectation));
        
        // when...
        try {
            runner.visit((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (VerificationException expected) {
            Verify.identical(fromExpectation, expected);
        }
        
        // then
        verifyMocks();
    }
}
