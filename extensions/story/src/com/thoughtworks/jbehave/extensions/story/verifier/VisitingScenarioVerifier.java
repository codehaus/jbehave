/*
 * Created on 23-Dec-2004
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;
import com.thoughtworks.jbehave.extensions.story.visitor.AbstractScenarioVisitor;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class VisitingScenarioVerifier extends AbstractScenarioVisitor implements ScenarioVerifier {

    public VisitingScenarioVerifier(String storyName, Environment environment) {
        super(storyName, environment);
    }
	
	public ScenarioResult verify(Scenario scenario) {
		return giveSelfToScenario(scenario);
	}

	protected void visitExpectation(Expectation expectation) {
		expectation.verify(environment);
        checkForMocks(expectation);
	}
	
	protected void visitGiven(Given given) throws Exception {}
	protected void visitEvent(Event event) throws Exception {}
}
