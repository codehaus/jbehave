/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.runner;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.visitor.VisitorSupport;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryRunner extends VisitorSupport {

    private final Environment environment;

    public StoryRunner(Environment environment) {
        this.environment = environment;
    }
    
    public void visitGiven(Given given) {
        try {
            given.setUp(environment);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void visitExpectationBeforeEvent(Expectation expectation) {
        try {
            expectation.setExpectation(environment);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void visitEvent(Event event) {
        try {
            event.occurIn(environment);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
