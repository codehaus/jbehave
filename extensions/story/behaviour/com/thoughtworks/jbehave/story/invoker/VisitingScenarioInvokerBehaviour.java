package com.thoughtworks.jbehave.story.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.story.domain.Context;
import com.thoughtworks.jbehave.story.domain.Environment;
import com.thoughtworks.jbehave.story.domain.Event;
import com.thoughtworks.jbehave.story.domain.Expectation;
import com.thoughtworks.jbehave.story.domain.Given;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Outcome;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.domain.Story;
import com.thoughtworks.jbehave.story.invoker.VisitingScenarioInvoker;
import com.thoughtworks.jbehave.story.result.ScenarioResult;

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
    private Environment environmentStub;
    
    public void setUp() {
        environmentStub = (Environment)stub(Environment.class);
        invoker = new VisitingScenarioInvoker("story", environmentStub);
        scenario = mock(Scenario.class);
    }
    
    public void shouldDispatchItselfAsVisitorToScenario() throws Exception {
        // given...

        // expect...
        scenario.expects("accept").with(invoker);
        
        // when...
        invoker.invoke((Scenario)scenario);
        
        // verify...
        verifyMocks();
    }
    
    public void shouldTellGivenToSetUpEnvironment() throws Exception {
        // given...
        Mock given = mock(Given.class);
        
        // expect...
        given.expects("setUp").with(environmentStub);
        
        // when...
        invoker.visit((Given)given);
        
        // verify...
        verifyMocks();
    }

    public void shouldSetExpectationInEnvironment() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        
        // expect...
        expectation.expects("setExpectationIn").with(environmentStub);
        
        // when...
        invoker.visit((Expectation)expectation);
        
        // verify...
        verifyMocks();
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Mock event = mock(Event.class);
        
        // expect...
        event.expects("occurIn").with(environmentStub);
        
        // when...
        invoker.visit((Event)event);
        
        // verify...
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
    
    public void shouldThrowNestedVerificationExceptionWhenExpectationThrowsException() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        Exception cause = new VerificationException("oops");
        
        // expect...
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
    
    public void shouldReturnSuccessfulResultWhenScenarioSucceeds() throws Exception {
        // expect...
        scenario.expects("accept");
        
        // when...
        Result result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.that(result.succeeded());
    }
    
    public void shouldReturnFailureResultWhenScenarioFails() throws Exception {
        // expect...
        Exception cause = new Exception("oops");
        scenario.expects("accept").will(throwException(new NestedVerificationException(cause)));
        
        // when...
        Result result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.identical(cause, result.cause());
    }
    
    /** Custom invocation handler so a Scenario can pass a component to the visitor */
    private InvocationHandler visitComponent(final Visitable component) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("accept")) {
                    Visitor visitor = (Visitor) args[0];
                    visitor.visit(component);
                }
                return null;
            }
        };
    }
    
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButGivenUsesMocks() throws Exception {
        // expect...
        Mock given = mock(Given.class);
        given.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitComponent((Visitable) given));
        
        // when...
        ScenarioResult result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.that("should have used mocks", result.usedMocks());
    }
    
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButEventUsesMocks() throws Exception {
        // expect...
        Mock event = mock(Event.class);
        event.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitComponent((Visitable) event));
        
        // when...
        ScenarioResult result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.that("should have used mocks", result.usedMocks());
    }
    
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButExpectationUsesMocks() throws Exception {
        // expect...
        Mock expectation = mock(Expectation.class);
        expectation.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitComponent((Visitable) expectation));
        
        // when...
        ScenarioResult result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.that("should have used mocks", result.usedMocks());
    }
    
    public void shouldClearEnvironmentOnEachInvocationOfAScenario() {
    	Mock environmentMock = mock(Environment.class);
    	environmentMock.expects("clear");
    	Environment environment = (Environment)environmentMock;
    	
    	Scenario scenario = (Scenario)stub(Scenario.class);
    	
    	invoker = new VisitingScenarioInvoker("story", environment);
    	invoker.invoke(scenario);
    	invoker.invoke(scenario);
    	
    	environmentMock.verify();
    }
    
    public void shouldSetExpectationsOnEachInvocationOfAScenario() {
    	Mock expectationMock = mock(Expectation.class);
    	final Expectation expectation = (Expectation)expectationMock;
    	expectationMock.expects("accept").will(
    			visitComponent((Visitable) expectation));
    	
    	Scenario scenario = new Scenario() {
			public Story getStory() {
				return new Story(new Narrative("","",""), 
						new AcceptanceCriteria());
			}

			public String getDescription() { return ""; }
			public Context getContext() { return new Context(new Given[0]); }
			public Event getEvent() { return (Event)stub(Event.class); }

			public Outcome getOutcome() {
				 return new Outcome(expectation);
			}

			public void accept(Visitor visitor) {
				visitor.visit(this);
				getContext().accept(visitor);
				getOutcome().accept(visitor);
				getEvent().accept(visitor);
			}
    	};
    	
    	expectationMock.expects("setExpectationIn");
    	expectationMock.expects("verify").zeroOrMoreTimes(); // not worried
    	
    	invoker.invoke(scenario);
    	invoker.invoke(scenario);
    	
    	expectationMock.verify();
    }
    
    public void shouldDoSomething() throws Exception {
        // given...
        

        // expect...
        // when...
        // verify...
    }
}
