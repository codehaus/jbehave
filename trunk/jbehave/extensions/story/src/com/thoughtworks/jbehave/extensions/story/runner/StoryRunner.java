/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.runner;

import com.thoughtworks.jbehave.extensions.story.base.Event;
import com.thoughtworks.jbehave.extensions.story.base.Expectation;
import com.thoughtworks.jbehave.extensions.story.base.Given;
import com.thoughtworks.jbehave.extensions.story.base.Story;
import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Narrative;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
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

    public void visitStory(Story story) {
    }
    
    public void visitNarrative(Narrative narrative) {
    }

    public void visitScenario(Scenario scenario) {
        scenario.setListener(listener);
    }

    public void visitContext(Context context) {
    }
    
    public void visitGiven(Given given) {
        try {
            given.setUp(environment);
            if (given.containsMocks()) {
                listener.componentUsesMocks(given);
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void visitOutcome(Outcome Outcome) {
    }

    public void visitExpectationBeforeTheEvent(Expectation expectation) {
        try {
            expectation.setExpectationIn(environment);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void visitEvent(Event event) {
        try {
            event.occurIn(environment);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void visitExpectationAfterTheEvent(Expectation expectation) {
        try {
            expectation.verify(environment);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
