/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Visitor {
    void visitStory(Story story) throws Exception;
    void visitScenario(Scenario scenario) throws Exception;
    void visitContext(Context context) throws Exception;
    void visitGiven(Given given) throws Exception;
    void visitOutcome(Outcome Outcome) throws Exception;
    void visitExpectationBeforeTheEvent(Expectation expectation) throws Exception;
    void visitEvent(Event event) throws Exception;
    void visitExpectationAfterTheEvent(Expectation expectation) throws Exception;
}
