/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import com.thoughtworks.jbehave.extensions.story.base.Event;
import com.thoughtworks.jbehave.extensions.story.base.Expectation;
import com.thoughtworks.jbehave.extensions.story.base.Given;
import com.thoughtworks.jbehave.extensions.story.base.Story;
import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.Narrative;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Visitor {
    void visitContext(Context context);
    void visitEvent(Event event);
    void visitExpectationBeforeTheEvent(Expectation expectation);
    void visitExpectationAfterTheEvent(Expectation expectation);
    void visitGiven(Given given);
    void visitNarrative(Narrative narrative);
    void visitOutcome(Outcome Outcome);
    void visitScenario(Scenario scenario);
    void visitStory(Story story);
}
