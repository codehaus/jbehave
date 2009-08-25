/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.invoker;

import jbehave.core.exception.NestedVerificationException;
import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.World;
import jbehave.core.story.result.ScenarioResult;
import jbehave.core.story.visitor.AbstractScenarioVisitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitingScenarioInvoker extends AbstractScenarioVisitor implements ScenarioInvoker {
    
    public VisitingScenarioInvoker(String storyName, World world) {
        super(storyName, world);
    }
    
    public ScenarioResult invoke(Scenario scenario) {
        world.clear();
        return giveSelfToScenario(scenario);
    }
    
    public void visitGiven(Given given) {
        try {
			given.setUp(world);
	        checkForMocks(given);
		} catch (Exception e) {
			throw new NestedVerificationException(e);
		}
    }
    
    public void visitEvent(Event event) {
        try {
			event.occurIn(world);
			checkForMocks(event);
		} catch (Exception e) {
			throw new NestedVerificationException(e);
		}
    }
    
    public void visitOutcome(Outcome outcome) {
        try {
			outcome.setExpectationIn(world);
			checkForMocks(outcome);
		} catch (Exception e) {
			throw new NestedVerificationException(e);
		}
    }
}
