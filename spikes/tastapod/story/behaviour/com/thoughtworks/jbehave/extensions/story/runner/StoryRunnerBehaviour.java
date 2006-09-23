package com.thoughtworks.jbehave.extensions.story.runner;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMockWithCGLIB;
import com.thoughtworks.jbehave.extensions.story.base.GivenBase;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.listener.ScenarioListener;

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
public class StoryRunnerBehaviour extends UsingJMockWithCGLIB {
    private ScenarioListener listenerStub;
    
    public void setUp() {
        listenerStub = (ScenarioListener) stub(ScenarioListener.class);
    }
    
    public void shouldSetUpGivenInEnvironment() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        StoryRunner runner = new StoryRunner(environment, listenerStub);
        Mock given = new Mock(Given.class);
        
        // expect...
        given.expectsOnce("setUp", environment);
        
        // when...
        runner.visitGiven((Given)given.proxy());
    }
    
    public void shouldSetExpectationInEnvironmentBeforeEventOccurs() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        StoryRunner runner = new StoryRunner(environment, listenerStub);
        Mock expectation = new Mock(Expectation.class);
        
        // expect...
        expectation.expectsOnce("setExpectationIn", environment);
        
        // when...
        runner.visitExpectationBeforeTheEvent((Expectation)expectation.proxy());
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        StoryRunner runner = new StoryRunner(environment, listenerStub);
        Mock event = new Mock(Event.class);
        
        // expect...
        event.expectsOnce("occurIn", environment);
        
        // when...
        runner.visitEvent((Event)event.proxy());
    }
    
    public void shouldVerifyExpectationInEnvironmentAfterEventOccurs() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        StoryRunner runner = new StoryRunner(environment, listenerStub);
        Mock expectation = new Mock(Expectation.class);
        
        // expect...
        expectation.expectsOnce("verify", environment);
        
        // when...
        runner.visitExpectationAfterTheEvent((Expectation)expectation.proxy());
    }
    
    public void shouldTellListenerIfGivenUsesMocks() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        Mock givenWithMocks = new Mock(GivenBase.class, "givenWithMocks");
        Mock givenWithoutMocks = new Mock(GivenBase.class, "givenWithoutMocks");
        Mock listener = new Mock(ScenarioListener.class);
        StoryRunner runner = new StoryRunner(environment, (ScenarioListener) listener.proxy());

        givenWithMocks.stubs().method("containsMocks").will(returnValue(true));
        givenWithMocks.stubs().method("setUp");
        givenWithoutMocks.stubs().method("containsMocks").will(returnValue(false));
        givenWithoutMocks.stubs().method("setUp");
        
        // expect...
        listener.expects(once()).method("componentUsesMocks").with(same(givenWithMocks.proxy()));
        
        // when...
        runner.visitGiven((Given) givenWithMocks.proxy());
        runner.visitGiven((Given) givenWithoutMocks.proxy());
    }
}