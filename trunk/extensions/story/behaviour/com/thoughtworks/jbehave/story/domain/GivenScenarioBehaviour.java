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
import com.thoughtworks.jbehave.story.visitor.Visitor;
import com.thoughtworks.jbehave.story.visitor.VisitorSupport;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class GivenScenarioBehaviour extends UsingMiniMock {
	
	public void shouldPassScenarioInvokerToGivenExpectationAndEvent() throws Exception {
		// given...
		Mock givenMock = mock(Given.class);
		Mock eventMock = mock(Event.class);
		Mock outcomeMock = mock(Outcome.class);
		
		World world = (World)stub(World.class);
		Visitor visitor = (Visitor)stub(Visitor.class);
		
		givenMock.expects("accept");
		outcomeMock.expects("accept");
		eventMock.expects("accept");
		
	    Givens context = new Givens(((Given)givenMock));
	    
	    Story story = new Story(new Narrative("", "", ""),
	    		new AcceptanceCriteria());
	    
	    Scenario scenario = new ScenarioUsingMiniMock("first scenario",
	    		story,
	    		context,
				((Event)eventMock),
				((Outcome)outcomeMock));
	    
	    Given givenScenario = new GivenScenario(scenario);
	    
	    givenScenario.setUp(world);
	}
	
	public void shouldNotResetWorldWhenVisited() {
		Given given = (Given)stub(Given.class);
		Event event = (Event)stub(Event.class);
		Outcome outcome = (Outcome)stub(Outcome.class);
		Mock worldMock = mock(World.class);
		World world = (World)worldMock;
		Story story = new Story(new Narrative("", "", ""), new AcceptanceCriteria());
	    Outcomes outcomes = new Outcomes(outcome);
		
	    worldMock.expects("clear").never();
	    
		Scenario scenario = new ScenarioUsingMiniMock("scenario",
				story,
				given,
				event,
				outcomes);
		
		Given givenScenario = new GivenScenario(scenario);
		
		givenScenario.accept(new VisitorSupport() {});
	}
}
