/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.invoker;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;
import com.thoughtworks.jbehave.extensions.story.visitor.AbstractScenarioVisitor;
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
    
    protected void visitGiven(Given given) throws Exception {
        given.setUp(environment);
        checkForMocks(given);
    }
    
    protected void visitEvent(Event event) throws Exception {
        event.occurIn(environment);
        checkForMocks(event);
    }
    
    protected void visitExpectation(Expectation expectation) {
        expectation.setExpectationIn(environment);
        checkForMocks(expectation);
    }
}
