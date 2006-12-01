/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioUsingMiniMockBehaviour extends UsingMiniMock {
    public void shouldSetupAndRunEventsThenVerifyBehaviourWhenRun() throws Exception {
        // given...
        Mock given = mock(Given.class);
		Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        World world = (World) stub(World.class);
        
        ScenarioUsingMiniMock scenario =
			new ScenarioUsingMiniMock((Given)given, (Event)event, (Outcome) outcome);

        // expect...
        given.expects("setUp").with(world);
        event.expects("occurIn").with(world).after(given, "setUp");
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        // when...
        scenario.run(world);
        
        // then...
        verifyMocks();
    }
    
    public void shouldUndoEventsThenTidyUpGivensOnTidyUp() {
        //given...
        Mock given = mock(Given.class);
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        World world = (World) stub(World.class);
        
        //expect...
        event.expects("undoIn").with(world);
        given.expects("tidyUp").with(world).after(event, "undoIn");
        
        //when...
        ScenarioUsingMiniMock scenario =
            new ScenarioUsingMiniMock((Given)given, (Event)event, (Outcome) outcome);
        scenario.tidyUp(world);
        
        //then...
        verifyMocks();
    }
    
    public void shouldAllowScenarioElementsToBeWrappedSoThatTheStoryIsEasyToReadAndConstruct() {
        //given...
    	Given given = new GivenUsingMiniMock(){
			public void setUp(World world) {}
            public void tidyUp(World world) {}
		};
		
    	Event event = new EventUsingMiniMock(){ 
    		public void occurIn(World world) {}
            public void undoIn(World world) {}
    	};
    	
    	Outcome outcome = new OutcomeUsingMiniMock(){
			public void verify(World world) {}
		};
		
    	Step step = new EventOutcomeStep(event, outcome);
        
        //expect when... then...
        ensureThat(ScenarioUsingMiniMock.given(given), eq(given));
        ensureThat(ScenarioUsingMiniMock.given(given, given), isA(Givens.class));
        ensureThat(ScenarioUsingMiniMock.given(given, given, given), isA(Givens.class));
        ensureThat(ScenarioUsingMiniMock.given(new Given[]{given, given, given}), isA(Givens.class));
        ensureThat(ScenarioUsingMiniMock.given((Scenario)null), isA(GivenScenario.class));
        
        ensureThat(ScenarioUsingMiniMock.when(event), eq(event));
        ensureThat(ScenarioUsingMiniMock.when(event, event), isA(Events.class));
        ensureThat(ScenarioUsingMiniMock.when(event, event, event), isA(Events.class));
        ensureThat(ScenarioUsingMiniMock.when(new Event[]{event, event, event}), isA(Events.class));
        
        ensureThat(ScenarioUsingMiniMock.then(outcome), eq(outcome));
        ensureThat(ScenarioUsingMiniMock.then(outcome, outcome), isA(Outcomes.class));
        ensureThat(ScenarioUsingMiniMock.then(outcome, outcome, outcome), isA(Outcomes.class));
        ensureThat(ScenarioUsingMiniMock.then(new Outcome[]{outcome, outcome, outcome}), isA(Outcomes.class));
        
        ensureThat(ScenarioUsingMiniMock.step(event, outcome), isA(EventOutcomeStep.class));
        ensureThat(ScenarioUsingMiniMock.step(step), eq(step));
        ensureThat(ScenarioUsingMiniMock.step(step, step), isA(Steps.class));
        ensureThat(ScenarioUsingMiniMock.step(step, step, step), isA(Steps.class));
        ensureThat(ScenarioUsingMiniMock.step(new Step[]{step, step, step}), isA(Steps.class));
    }
}
