package com.thoughtworks.jbehave.extensions.story.runner;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.runner.StoryRunner;

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
public class StoryRunnerBehaviour extends UsingJMock {
    
    public void shouldSetUpGivenInEnvironment() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        StoryRunner runner = new StoryRunner(environment);
        Mock given = new Mock(Given.class);
        
        // expect...
        given.expectsOnce("setUp", environment);
        
        // when...
        runner.visitGiven((Given)given.proxy());
    }
    
    public void shouldSetExpectationInEnvironmentBeforeEventOccurs() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        StoryRunner runner = new StoryRunner(environment);
        Mock expectation = new Mock(Expectation.class);
        
        // expect...
        expectation.expectsOnce("setExpectation", environment);
        
        // when...
        runner.visitExpectationBeforeEvent((Expectation)expectation.proxy());
    }
    
    public void shouldMakeEventOccurInEnvironment() throws Exception {
        // given...
        Environment environment = (Environment) stub(Environment.class);
        StoryRunner runner = new StoryRunner(environment);
        Mock event = new Mock(Event.class);
        
        // expect...
        event.expectsOnce("occurIn", environment);
        
        // when...
        runner.visitEvent((Event)event.proxy());
    }
}
