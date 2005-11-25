/*
 * Created on 23-Dec-2004
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.story.verifier;

import jbehave.story.domain.Outcome;
import jbehave.story.domain.Scenario;
import jbehave.story.domain.World;
import jbehave.story.result.ScenarioResult;
import jbehave.story.visitor.AbstractScenarioVisitor;


/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class VisitingScenarioVerifier extends AbstractScenarioVisitor implements ScenarioVerifier {

    public VisitingScenarioVerifier(String storyName, World world) {
        super(storyName, world);
    }
	
	public ScenarioResult verify(Scenario scenario) {
		return giveSelfToScenario(scenario);
	}

	public void visitOutcome(Outcome outcome) {
		outcome.verify(world);
        checkForMocks(outcome);
	}
}
