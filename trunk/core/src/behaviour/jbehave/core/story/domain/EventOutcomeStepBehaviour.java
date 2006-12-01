package jbehave.core.story.domain;

import jbehave.core.Ensure;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;

public class EventOutcomeStepBehaviour extends UsingMiniMock {

    public void shouldMakeEventOccurThenVerifyOutcomeWhenPerformed() {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        World world = (World)stub(World.class);
        
        event.expects("occurIn").with(world);
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        step.perform(world);
        
        verifyMocks();        
    }
    
    public void shouldPassVisitorToEventThenOutcome() {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        Renderer renderer = (Renderer)stub(Renderer.class);
        
        event.expects("narrateTo").with(renderer);
        outcome.expects("narrateTo").with(renderer).after(event, "narrateTo");
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        step.narrateTo(renderer);
        
        verifyMocks();  
    }
    
    public void shouldContainMocksIfAndOnlyIfEitherEventOrOutcomeContainMocks() {
        
        Ensure.that(stepContainingMocks(false, true).containsMocks());
        Ensure.that(stepContainingMocks(true, false).containsMocks());        
        Ensure.not(stepContainingMocks(false, false).containsMocks());
        
        verifyMocks();          
    }
    
    public void shouldVerifyEventAndOutcomeMocksWhenVerified() {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        
        event.expects("verifyMocks");
        outcome.expects("verifyMocks");
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        step.verifyMocks();
        
        verifyMocks();        
    }
    
    public void shouldUndoEventsWhenUndone() {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        
        World world = new HashMapWorld();
        event.expects("undoIn").with(world);
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        step.undoIn(world);
        
        verifyMocks();  
    }
    
    private EventOutcomeStep stepContainingMocks(boolean eventContainsMocks, boolean outcomeContainsMocks) {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        
        event.expects("containsMocks").zeroOrMoreTimes().will(returnValue(eventContainsMocks));
        outcome.expects("containsMocks").zeroOrMoreTimes().will(returnValue(outcomeContainsMocks));
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        return step;
    }
    
}
