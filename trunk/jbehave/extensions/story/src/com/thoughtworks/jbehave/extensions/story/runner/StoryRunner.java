/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.runner;

import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.listener.ScenarioListener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryRunner implements Visitor {
    private final Environment environment;
    private final ScenarioListener listener;

    public StoryRunner(Environment environment, ScenarioListener listener) {
        this.environment = environment;
        this.listener = listener;
    }
    
    public void visit(Visitable visitable) {
        if (visitable instanceof Scenario) {
            visitScenario((Scenario) visitable);
        }
        else if (visitable instanceof Given) {
            visitGiven((Given) visitable);
        }
        else if (visitable instanceof Event) {
            visitEvent((Event) visitable);
        }
    }
    
    public void visitScenario(Scenario scenario) {
        scenario.setListener(listener);
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
//            e.printStackTrace();
        }
    }

    public void visitExpectationBeforeTheEvent(Expectation expectation) {
        try {
            expectation.setExpectationIn(environment);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
        }
    }
    
    public void visitEvent(Event event) {
        try {
            event.occurIn(environment);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
        }
    }

    public void visitExpectationAfterTheEvent(Expectation expectation) {
        try {
            expectation.verify(environment);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
        }
    }
}
