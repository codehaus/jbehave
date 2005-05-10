package com.thoughtworks.jbehave.story.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.story.domain.Environment;
import com.thoughtworks.jbehave.story.domain.Event;
import com.thoughtworks.jbehave.story.domain.Expectation;
import com.thoughtworks.jbehave.story.domain.Given;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.result.ScenarioResult;
import com.thoughtworks.jbehave.story.visitor.Visitable;
import com.thoughtworks.jbehave.story.visitor.Visitor;

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
    private Environment environmentStub;
    
    public void setUp() {
        environmentStub = (Environment)stub(Environment.class);
        invoker = new VisitingScenarioInvoker("story", environmentStub);
    }
    
    public void shouldDispatchItselfAsVisitorToScenario() throws Exception {
        // given...
		Mock scenario = mock(Scenario.class);

        // expect...
        scenario.expects("accept").with(invoker);
        
        // when...
        invoker.invoke((Scenario)scenario);
    }
    
    public void shouldTellGivenToSetUpEnvironment() throws Exception {
        // given...
        Mock given = mock(Given.class);
        
        // expect...
        given.expects("setUp").with(environmentStub);
        
        // when...
        invoker.visitGiven((Given)given);
    }

    public void shouldSetExpectationInEnvironment() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        
        // expect...
        expectation.expects("setExpectationIn").with(environmentStub);
        
        // when...
        invoker.visitExpectation((Expectation)expectation);
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Mock event = mock(Event.class);
        
        // expect...
        event.expects("occurIn").with(environmentStub);
        // when...
        invoker.visitEvent((Event)event);
    }

    public void shouldThrowNestedVerificationExceptionFromVisitWhenGivenThrowsException() throws Exception {
        // given...
        Mock given = mock(Given.class);
        Exception cause = new Exception("oops");
        given.expects("setUp").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visitGiven((Given) given);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
    }
    
    public void shouldThrowNestedVerificationExceptionFromVisitWhenEventThrowsException() throws Exception {
        // given...
        Mock event = mock(Event.class);
        Exception cause = new Exception("oops");
        event.expects("occurIn").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visitEvent((Event) event);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
    }
    
    public void shouldThrowNestedVerificationExceptionWhenExpectationThrowsException() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);
        Exception cause = new VerificationException("oops");
        
        // expect...
        expectation.expects("setExpectationIn").with(environmentStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visitExpectation((Expectation) expectation);
            Verify.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            Verify.identical(cause, expected.getCause());
        }
    }
    
    public void shouldReturnSuccessfulResultWhenScenarioSucceeds() throws Exception {
		// given...
		Mock scenario = mock(Scenario.class);
		
        // expect...
        scenario.expects("accept");
        
        // when...
        Result result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.that(result.succeeded());
    }
    
    public void shouldReturnFailureResultWhenScenarioFails() throws Exception {
		// given...
		Mock scenario = mock(Scenario.class);
		
        // expect...
        Exception cause = new Exception("oops");
        scenario.expects("accept").will(throwException(new NestedVerificationException(cause)));
        
        // when...
        Result result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.identical(cause, result.cause());
    }
    
    /** Custom invocation handler so a Scenario can pass a component to the visitor */
    private InvocationHandler visitComponent(final Object component) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                Visitor visitor = (Visitor) args[0];
                ((Visitable)component).accept(visitor);
                return null;
            }
        };
    }
    
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButGivenUsesMocks() throws Exception {
		// given...
		Mock scenario = mock(Scenario.class);
        Mock given = mock(Given.class);
		InvocationHandler dispatchItself = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitGiven((Given) proxy);
				return null;
			}
		};
		
        // expect...
		given.expects("accept").will(dispatchItself);
        given.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitComponent((Visitable) given));
        
        // when...
        ScenarioResult result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.that("should have used mocks", result.usedMocks());
    }

	public void shouldReturnResultUsingMocksWhenScenarioSucceedsButEventUsesMocks() throws Exception {
		// given...
		Mock scenario = mock(Scenario.class);
        Mock event = mock(Event.class);
		InvocationHandler dispatchItself = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitEvent((Event) proxy);
				return null;
			}
		};
		
        // expect...
		event.expects("accept").will(dispatchItself);
        event.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitComponent((Visitable) event));
        
        // when...
        ScenarioResult result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Verify.that("should have used mocks", result.usedMocks());
    }
    
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButExpectationUsesMocks() throws Exception {
		// given...
		Mock scenario = mock(Scenario.class);
		InvocationHandler dispatchItself = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitExpectation((Expectation) proxy);
				return null;
			}
		};
		
        // expect...
        Mock expectation = mock(Expectation.class);
		expectation.expects("accept").will(dispatchItself);
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
    	
    	environmentMock.verify();
    }
    
    public void shouldSetExpectationsOnEachInvocationOfAScenario() {
		Mock scenario = mock(Scenario.class);
    	Mock expectation = mock(Expectation.class);
		InvocationHandler dispatchItself = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitExpectation((Expectation) proxy);
				return null;
			}
		};
    	expectation.expects("accept").will(dispatchItself);
    	expectation.expects("setExpectationIn");
        scenario.expects("accept").will(visitComponent((Visitable) expectation));
    	
    	invoker.invoke((Scenario) scenario);
    	
    	expectation.verify();
    }
}
