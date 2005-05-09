/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.invoker;

import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.story.domain.Environment;
import com.thoughtworks.jbehave.story.domain.Event;
import com.thoughtworks.jbehave.story.domain.Expectation;
import com.thoughtworks.jbehave.story.domain.Given;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.result.ScenarioResult;
import com.thoughtworks.jbehave.story.visitor.AbstractScenarioVisitor;
/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitingScenarioInvoker extends AbstractScenarioVisitor implements ScenarioInvoker {
    
    public VisitingScenarioInvoker(String storyName, Environment environment) {
        super(storyName, environment);
    }
    
    public ScenarioResult invoke(Scenario scenario) {
        environment.clear();
        return giveSelfToScenario(scenario);
    }
    
    public void visitGiven(Given given) {
        try {
			given.setUp(environment);
	        checkForMocks(given);
		} catch (Exception e) {
			throw new NestedVerificationException(e);
		}
    }
    
    public void visitEvent(Event event) {
        try {
			event.occurIn(environment);
			checkForMocks(event);
		} catch (Exception e) {
			throw new NestedVerificationException(e);
		}
    }
    
    public void visitExpectation(Expectation expectation) {
        try {
			expectation.setExpectationIn(environment);
			checkForMocks(expectation);
		} catch (Exception e) {
			throw new NestedVerificationException(e);
		}
    }
}
