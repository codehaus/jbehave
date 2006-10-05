package jbehave.core.story.domain;

import jbehave.core.Ensure;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.visitor.Visitor;

public class EventOutcomeStepBehaviour extends UsingMiniMock {

    public void shouldSetUpOutcomeThenMakeEventOccurThenVerifyOutcomeWhenPerformed() {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        World world = (World)stub(World.class);
        
        outcome.expects("setExpectationIn").with(world);
        event.expects("occurIn").with(world).after(outcome, "setExpectationIn");
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        step.perform(world);
        
        verifyMocks();        
    }
    
    public void shouldPassVisitorToEventThenOutcome() {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        Visitor visitor = (Visitor)stub(Visitor.class);
        
        event.expects("accept").with(visitor);
        outcome.expects("accept").with(visitor).after(event, "accept");
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        step.accept(visitor);
        
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
    
    private EventOutcomeStep stepContainingMocks(boolean eventContainsMocks, boolean outcomeContainsMocks) {
        Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        
        event.expects("containsMocks").zeroOrMoreTimes().will(returnValue(eventContainsMocks));
        outcome.expects("containsMocks").zeroOrMoreTimes().will(returnValue(outcomeContainsMocks));
        
        EventOutcomeStep step = new EventOutcomeStep((Event)event, (Outcome)outcome);
        return step;
    }
}
