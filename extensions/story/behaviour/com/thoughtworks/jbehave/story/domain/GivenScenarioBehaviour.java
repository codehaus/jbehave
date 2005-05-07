/*
 * Created on 22-12-2004
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.story.domain.Context;
import com.thoughtworks.jbehave.story.domain.Environment;
import com.thoughtworks.jbehave.story.domain.Event;
import com.thoughtworks.jbehave.story.domain.Expectation;
import com.thoughtworks.jbehave.story.domain.Given;
import com.thoughtworks.jbehave.story.domain.GivenScenario;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Outcome;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.domain.ScenarioUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Story;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class GivenScenarioBehaviour extends UsingMiniMock {
	
	public void shouldPassScenarioInvokerToGivenExpectationAndEvent() throws Exception {
		Mock givenMock = mock(Given.class);
		Mock eventMock = mock(Event.class);
		Mock expectationMock = mock(Expectation.class);
		
		Given given = (Given)givenMock;
		Event event = (Event)eventMock;
		Expectation expectation = (Expectation)expectationMock;
		Environment environment = (Environment)stub(Environment.class);
		Visitor visitor = (Visitor)stub(Visitor.class);
		
		givenMock.expects("accept");
		expectationMock.expects("accept");
		eventMock.expects("accept");
		
	    Context context = new Context(given);
	    Outcome outcome = new Outcome(expectation);
	    
	    Story story = new Story(new Narrative("", "", ""),
	    		new AcceptanceCriteria());
	    
	    Scenario scenario = new ScenarioUsingMiniMock("first scenario",
	    		story,
	    		context,
				event,
				outcome);
	    
	    Given givenScenario = new GivenScenario(scenario);
	    
	    givenScenario.setUp(environment);
	    
	    verifyMocks();
	}
	
	public void shouldNotClearEnvironmentWhenVisited() {
		Given given = (Given)stub(Given.class);
		Event event = (Event)stub(Event.class);
		Expectation expectation = (Expectation)stub(Expectation.class);
		Mock environmentMock = mock(Environment.class);
		Environment environment = (Environment)environmentMock;
		Story story = new Story(new Narrative("", "", ""),
				new AcceptanceCriteria());
	    Context context = new Context(given);
	    Outcome outcome = new Outcome(expectation);
		
	    environmentMock.expects("clear").never();
	    
		Scenario scenario = new ScenarioUsingMiniMock("scenario",
				story,
				context,
				event,
				outcome);
		
		Given givenScenario = new GivenScenario(scenario);
		
		givenScenario.accept(new Visitor() {
			public void visit(Visitable visitable) {}
		});
		
		verifyMocks();
	}
}
