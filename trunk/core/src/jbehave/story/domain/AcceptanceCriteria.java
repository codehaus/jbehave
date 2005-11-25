/*
 * Created on 28-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.story.domain;

import java.util.List;

import jbehave.story.visitor.VisitableArrayList;
import jbehave.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AcceptanceCriteria implements Scenario {
	private final VisitableArrayList scenarios = new VisitableArrayList();

	public List scenarios() {
        return scenarios;
    }

	public void accept(Visitor visitor) {
		visitor.visitAcceptanceCriteria(this);
		scenarios.accept(visitor);
	}

	public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}

	public String getStoryName() {
		// TODO implement getStoryName
		throw new UnsupportedOperationException("TODO");
	}

	public String getDescription() {
		// TODO implement getDescription
		throw new UnsupportedOperationException("TODO");
	}
}
