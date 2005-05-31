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
		Mock givenMock = mock(Given.class);
		Mock eventMock = mock(Event.class);
		Mock expectationMock = mock(Outcome.class);
		
		Given given = (Given)givenMock;
		Event event = (Event)eventMock;
		Outcome expectation = (Outcome)expectationMock;
		World world = (World)stub(World.class);
		Visitor visitor = (Visitor)stub(Visitor.class);
		
		givenMock.expects("accept");
		expectationMock.expects("accept");
		eventMock.expects("accept");
		
	    Context context = new Context(given);
	    Outcomes outcomes = new Outcomes(expectation);
	    
	    Story story = new Story(new Narrative("", "", ""),
	    		new AcceptanceCriteria());
	    
	    Scenario scenario = new ScenarioUsingMiniMock("first scenario",
	    		story,
	    		context,
				event,
				outcomes);
	    
	    Given givenScenario = new GivenScenario(scenario);
	    
	    givenScenario.setUp(world);
	}
	
	public void shouldNotClearEnvironmentWhenVisited() {
		Given given = (Given)stub(Given.class);
		Event event = (Event)stub(Event.class);
		Outcome expectation = (Outcome)stub(Outcome.class);
		Mock worldMock = mock(World.class);
		World world = (World)worldMock;
		Story story = new Story(new Narrative("", "", ""), new AcceptanceCriteria());
	    Context context = new Context(given);
	    Outcomes outcomes = new Outcomes(expectation);
		
	    worldMock.expects("clear").never();
	    
		Scenario scenario = new ScenarioUsingMiniMock("scenario",
				story,
				context,
				event,
				outcomes);
		
		Given givenScenario = new GivenScenario(scenario);
		
		givenScenario.accept(new VisitorSupport() {});
	}
}
