package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;

public class MultiStepScenarioBehaviour extends UsingMiniMock {

    public void shouldPerformGiven() throws Exception {
        // given
        final Mock given = mock(Given.class);
        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                given((Given) given);
            }
        };
        World world = new HashMapWorld();
        
        // expect
        given.expects("setUp").with(world);
        
        // when
        scenario.run(world);
        
        // then
        verifyMocks();
    }
    
    public void shouldPerformEvent() throws Exception {
        // given
        final Mock event = mock(Event.class);
        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                when((Event)event);
            }
        };
        World world = new HashMapWorld();
        
        // expect
        event.expects("occurIn").with(world);
        
        // when
        scenario.run(world);
        
        // then
        verifyMocks();
    }
    
    public void shouldPerformOutcome() throws Exception {
        // given
        final Mock outcome = mock(Outcome.class);
        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                then((Outcome)outcome);
            }
        };
        World world = new HashMapWorld();
        
        // expect
        outcome.expects("verify").with(world);
        
        // when
        scenario.run(world);
        
        // then
        verifyMocks();
    }
    
    public void shouldPerformStepsInCorrectOrder() {
        // given
        final Mock given = mock(Given.class);
        final Mock event = mock(Event.class);
        final Mock outcome = mock(Outcome.class);
        World world = new HashMapWorld();
        
        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        
        // expect
        given.expects("setUp").with(world);
        event.expects("occurIn").with(world).after(given, "setUp");
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        // when
        scenario.run(world);
        
        // then
        verifyMocks();
    }
    
    public interface GivenWithCleanUp extends Given, HasCleanUp {}
    public interface OutcomeWithCleanUp extends Outcome, HasCleanUp {}

    public void shouldTellStepsToCleanUpWorldInReverseOrder() throws Exception {
        // given
        final Mock given = mock(GivenWithCleanUp.class);
        final Mock event = mock(Event.class); // no cleanUp
        final Mock outcome = mock(OutcomeWithCleanUp.class);
        World world = new HashMapWorld();

        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };

        // expect
        outcome.expects("cleanUp").with(world);
        event.expects("cleanUp").never();
        given.expects("cleanUp").with(world).after(outcome, "cleanUp");

        // when
        scenario.run(world);

        // then
        verifyMocks();
    }
    
    public void shouldNarrateScenarioAndStepsInOrder() throws Exception {
        // given
        final Mock given = mock(Given.class);
        final Mock event = mock(Event.class);
        final Mock outcome = mock(Outcome.class);
        Mock renderer = mock(Renderer.class);
        
        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        
        // expect
        renderer.expects("renderScenario").with(scenario);
        given.expects("narrateTo").with(renderer).after(renderer, "renderScenario");
        event.expects("narrateTo").with(renderer).after(given, "narrateTo");
        outcome.expects("narrateTo").with(renderer).after(event, "narrateTo");
        
        // when
        scenario.narrateTo((Renderer) renderer);
        
        // then
        verifyMocks();
    }
    
    public void shouldContainMocksIfAnyStepsContainMocks() throws Exception {
        // given
        final Mock given = mock(Given.class);
        final Mock event = mock(Event.class);
        final Mock outcome = mock(Outcome.class);
        
        // expect
        given.expects("containsMocks").will(returnValue(false));
        event.expects("containsMocks").will(returnValue(false));
        outcome.expects("containsMocks").will(returnValue(true)); // mock mock!

        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        
        // when
        boolean result = scenario.containsMocks();
        
        // then
        ensureThat(result, eq(true));
        verifyMocks();
    }
    
    public void shouldSucceedFastIfAnyStepsContainMocks() throws Exception {
        // given
        final Mock given = mock(Given.class);
        final Mock event = mock(Event.class);
        final Mock outcome = mock(Outcome.class);
        
        // expect
        given.expects("containsMocks").will(returnValue(false));
        event.expects("containsMocks").will(returnValue(true)); // succeeds fast at second step
        outcome.expects("containsMocks").never();               // so third step not checked
        
        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        
        // when
        boolean result = scenario.containsMocks();
        
        // then
        ensureThat(result, eq(true));
        verifyMocks();
    }
    
    public void shouldNotContainMocksIfNoStepsContainMocks() throws Exception {
        // given
        final Mock given = mock(Given.class);
        final Mock event = mock(Event.class);
        final Mock outcome = mock(Outcome.class);
        
        // expect
        given.expects("containsMocks").will(returnValue(false));
        event.expects("containsMocks").will(returnValue(false));
        outcome.expects("containsMocks").will(returnValue(false));
        
        Scenario scenario = new MultiStepScenario() {
            public void assemble() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        
        // when
        boolean result = scenario.containsMocks();
        
        // then
        verifyMocks();
        ensureThat(result, eq(false));
    }
}
