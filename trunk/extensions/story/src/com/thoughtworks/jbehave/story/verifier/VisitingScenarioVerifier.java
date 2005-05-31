/*
 * Created on 23-Dec-2004
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.verifier;

import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.domain.Outcome;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.result.ScenarioResult;
import com.thoughtworks.jbehave.story.visitor.AbstractScenarioVisitor;

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

	public void visitExpectation(Outcome expectation) {
		expectation.verify(world);
        checkForMocks(expectation);
	}
}
