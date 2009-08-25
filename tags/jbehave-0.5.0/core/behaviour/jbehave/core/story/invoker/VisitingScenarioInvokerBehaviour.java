package jbehave.core.story.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import jbehave.core.Ensure;
import jbehave.core.exception.NestedVerificationException;
import jbehave.core.exception.VerificationException;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.result.Result;
import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.World;
import jbehave.core.story.invoker.VisitingScenarioInvoker;
import jbehave.core.story.result.ScenarioResult;
import jbehave.core.story.visitor.Visitable;
import jbehave.core.story.visitor.Visitor;


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
    private World worldStub;
    
    public void setUp() {
        worldStub = (World)stub(World.class);
        invoker = new VisitingScenarioInvoker("story", worldStub);
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
        given.expects("setUp").with(worldStub);
        
        // when...
        invoker.visitGiven((Given)given);
    }

    public void shouldSetExpectationInEnvironment() throws Exception {
        // given...
        Mock outcome = mock(Outcome.class);
        
        // expect...
        outcome.expects("setExpectationIn").with(worldStub);
        
        // when...
        invoker.visitOutcome((Outcome)outcome);
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Mock event = mock(Event.class);
        
        // expect...
        event.expects("occurIn").with(worldStub);
        // when...
        invoker.visitEvent((Event)event);
    }

    public void shouldThrowNestedVerificationExceptionFromVisitWhenGivenThrowsException() throws Exception {
        // given...
        Mock given = mock(Given.class);
        Exception cause = new RuntimeException("oops");
        given.expects("setUp").with(worldStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visitGiven((Given) given);
            Ensure.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            ensureThat(expected.getCause(), sameInstanceAs(cause));
        }
    }
    
    public void shouldThrowNestedVerificationExceptionFromVisitWhenEventThrowsException() throws Exception {
        // given...
        Mock event = mock(Event.class);
        Exception cause = new RuntimeException("oops");
        event.expects("occurIn").with(worldStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visitEvent((Event) event);
            Ensure.impossible("should have thrown exception");
        }
        catch (NestedVerificationException expected) {
            ensureThat(expected.getCause(), sameInstanceAs(cause));
        }
    }
    
    public void shouldThrowNestedVerificationExceptionWhenExpectationThrowsException() throws Exception {
        // given...
        Mock outcome = mock(Outcome.class);
        Exception cause = new VerificationException("oops");
        
        // expect...
        outcome.expects("setExpectationIn").with(worldStub).will(throwException(cause));
        
        // when...
        try {
            invoker.visitOutcome((Outcome) outcome);
            Ensure.impossible("should have thrown exception");
        }
        catch (NestedVerificationException actualException) {
            ensureThat(actualException.getCause(), sameInstanceAs(cause));
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
        Ensure.that(result.succeeded());
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
        ensureThat(result.cause(), sameInstanceAs(cause));
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
        Ensure.that("should have used mocks", result.usedMocks());
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
        Ensure.that("should have used mocks", result.usedMocks());
    }
    
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButExpectationUsesMocks() throws Exception {
		// given...
		Mock scenario = mock(Scenario.class);
		InvocationHandler dispatchItself = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitOutcome((Outcome) proxy);
				return null;
			}
		};
		
        // expect...
        Mock outcome = mock(Outcome.class);
		outcome.expects("accept").will(dispatchItself);
        outcome.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitComponent((Visitable) outcome));
        
        // when...
        ScenarioResult result = invoker.invoke((Scenario)scenario);
        
        // verify...
        Ensure.that("should have used mocks", result.usedMocks());
    }
    
    public void shouldClearEnvironmentOnEachInvocationOfAScenario() {
    	Mock worldMock = mock(World.class);
    	worldMock.expects("clear");
    	World world = (World)worldMock;
    	
    	Scenario scenario = (Scenario)stub(Scenario.class);
    	
    	invoker = new VisitingScenarioInvoker("story", world);
    	invoker.invoke(scenario);
    	
    	worldMock.verify();
    }
    
    public void shouldSetExpectationsOnEachInvocationOfAScenario() {
		Mock scenario = mock(Scenario.class);
    	Mock outcome = mock(Outcome.class);
		InvocationHandler dispatchItself = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				((Visitor)args[0]).visitOutcome((Outcome) proxy);
				return null;
			}
		};
    	outcome.expects("accept").will(dispatchItself);
    	outcome.expects("setExpectationIn");
        scenario.expects("accept").will(visitComponent((Visitable) outcome));
    	
    	invoker.invoke((Scenario) scenario);
    	
    	outcome.verify();
    }
}
