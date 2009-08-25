/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.runner;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.listener.ScenarioListener;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryRunner implements Visitor {
    private final Environment environment;
    private final ScenarioListener listener;

    public StoryRunner(Environment environment, ScenarioListener listener) throws Exception {
        this.environment = environment;
        this.listener = listener;
    }

    public void visitStory(Story story) throws Exception {
    }

    public void visitScenario(Scenario scenario) throws Exception {
        scenario.setListener(listener);
    }

    public void visitContext(Context context) throws Exception {
    }
    
    public void visitGiven(Given given) throws Exception {
        given.setUp(environment);
        if (given instanceof UsingJMock && ((UsingJMock)given).containsMocks()) {
            listener.componentUsesMocks(given);
        }
    }

    public void visitOutcome(Outcome Outcome) throws Exception {
    }

    public void visitExpectationBeforeTheEvent(Expectation expectation) throws Exception {
        expectation.setExpectationIn(environment);
    }
    
    public void visitEvent(Event event) throws Exception {
        event.occurIn(environment);
    }

    public void visitExpectationAfterTheEvent(Expectation expectation) throws Exception {
        expectation.verify(environment);
    }
}
