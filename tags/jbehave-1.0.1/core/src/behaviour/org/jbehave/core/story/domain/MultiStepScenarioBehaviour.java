package org.jbehave.core.story.domain;

import org.jbehave.core.Block;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.story.renderer.Renderer;

public class MultiStepScenarioBehaviour extends UsingMiniMock {
    
    public interface GivenWithCleanUp extends Given, CleansUpWorld {}
    public interface OutcomeWithCleanUp extends Outcome, CleansUpWorld {}
    public interface GivenWorld extends Given, CleansUpWorld, World {}
    
    public void shouldPerformGiven() throws Exception {
        // given
        final Mock given = mock(Given.class);
        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Given) given);
            }
        };
        scenario.specify();
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
            public void specifySteps() {
                when((Event)event);
            }
        };
        scenario.specify();
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
            public void specifySteps() {
                then((Outcome)outcome);
            }
        };
        scenario.specify();
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
            public void specifySteps() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        scenario.specify();
        
        // expect
        given.expects("setUp").with(world);
        event.expects("occurIn").with(world).after(given, "setUp");
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        // when
        scenario.run(world);
        
        // then
        verifyMocks();
    }


    public void shouldTellStepsToCleanUpWorldInReverseOrder() throws Exception {
        // given
        final Mock given = mock(GivenWithCleanUp.class);
        final Mock event = mock(Event.class); // no cleanUp
        final Mock outcome = mock(OutcomeWithCleanUp.class);
        World world = new HashMapWorld();

        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        scenario.specify();

        // expect
        outcome.expects("cleanUp").with(world);
        event.expects("cleanUp").never();
        given.expects("cleanUp").with(world).after(outcome, "cleanUp");

        // when
        scenario.run(world);
        scenario.cleanUp(world);

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
            public void specifySteps() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        scenario.specify();
        
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
        outcome.expects("containsMocks").will(returnValue(true)); // has mocks

        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        scenario.specify();
        
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
            public void specifySteps() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        scenario.specify();
        
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
            public void specifySteps() {
                given((Given)given);
                when((Event)event);
                then((Outcome)outcome);
            }
        };
        scenario.specify();
        
        // when
        boolean result = scenario.containsMocks();
        
        // then
        verifyMocks();
        ensureThat(result, eq(false));
    }
    
    public void shouldCopyOutcomeWithExpectationsAfterLastGiven() throws Exception {
        // given
        final Mock given1 = mock(Given.class, "given1");
        final Mock given2 = mock(Given.class, "given2");
        final Mock event = mock(Event.class, "event");
        final Mock outcome = mock(OutcomeWithExpectations.class, "outcome");
        World world = (World)stub(World.class);
        
        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Given) given1);
                given((Given) given2);
                when((Event) event);
                then((Outcome) outcome);
            }
        };
        scenario.specify();
        
        // expect
        given1.expects("setUp").with(world);
        given2.expects("setUp").with(world).after(given1, "setUp");
        outcome.expects("setExpectationsIn").with(world).after(given2, "setUp");
        event.expects("occurIn").with(world).after(outcome, "setExpectationsIn");
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        // when
        scenario.run(world);
        
        // then
        verifyMocks();
    }
    
    public void shouldCopyOutcomeWithExpectationsAtBeginningIfThereAreNoGivens() throws Exception {
        // given
        final Mock event = mock(Event.class);
        final Mock outcome = mock(OutcomeWithExpectations.class, "outcome");
        World world = (World)stub(World.class);
        
        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                when((Event) event);
                then((Outcome) outcome);
            }
        };
        scenario.specify();
        
        // expect
        outcome.expects("setExpectationsIn").with(world);
        event.expects("occurIn").with(world).after(outcome, "setExpectationsIn");
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        // when
        scenario.run(world);
        
        // then
        verifyMocks();
    }
    
    public void shouldSpecifyScenarioWhenAddingAsAGiven() {
        final Mock scenario = mock(Scenario.class);
        World world = (World)stub(World.class);
        scenario.expects("specify");
        
        Scenario parentScenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Scenario)scenario);
            }
        };
        parentScenario.specify();
        
        verifyMocks();
    }
    
    public void shouldThrowIllegalStateExceptionIfRunBeforeSpecified() throws Exception {
        final Scenario scenario = new MultiStepScenario(){
            public void specifySteps() {}};
            
        Exception exception = runAndCatch(IllegalStateException.class, new Block() {
            public void run() throws Exception {
                scenario.run((World)stub(World.class));
            }
        });
        
        ensureThat(exception, isNotNull());
    }

    public void shouldThrowIllegalStateExceptionIfNarratedBeforeSpecified() throws Exception {
        final Scenario scenario = new MultiStepScenario(){
            public void specifySteps() {}};
            
        Exception exception = runAndCatch(IllegalStateException.class, new Block() {
            public void run() throws Exception {
                scenario.narrateTo((Renderer)stub(Renderer.class));
            }
        });
        
        ensureThat(exception, isNotNull());
    }
    
    public void shouldRethrowExceptionsInStepsAsVerificationExceptions() throws Exception {
        final Mock given = mock(GivenWithCleanUp.class, "given");
        given.expects("setUp").will(throwException(new Exception()));
        
        final Scenario scenario = new MultiStepScenario(){
            public void specifySteps() {
                given((Given) given);
            }};
            
        Exception exception = runAndCatch(VerificationException.class, new Block() {
            public void run() throws Exception {
                scenario.specify();
                scenario.run((World)stub(World.class));
            }
        });
        
        ensureThat(exception, isNotNull());        
    }
    
    public void shouldNotRethrowVerificationExceptionsInSteps() throws Exception {
        final Mock given = mock(GivenWithCleanUp.class, "given");
        VerificationException verificationException = new VerificationException("My message");
        given.expects("setUp").will(throwException(verificationException));
        
        final Scenario scenario = new MultiStepScenario(){
            public void specifySteps() {
                given((Given) given);
            }};
            
        Exception exception = runAndCatch(VerificationException.class, new Block() {
            public void run() throws Exception {
                scenario.specify();
                scenario.run((World)stub(World.class));
            }
        });
        
        ensureThat(exception, is(verificationException));        
    }
    
    public void shouldNotCleanUpIfNotRun() throws Exception {
        // given
        final Mock given = mock(GivenWithCleanUp.class, "given");
        World world = (World)stub(World.class);
        
        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Given) given);
            }};
            
        given.expects("cleanUp").never();
            
        scenario.specify();
        scenario.cleanUp(world);
        
        verifyMocks();
    }
    
    public void shouldNotCleanUpIfAlreadyCleanedUp() throws Exception {
        // given
        final Mock given = mock(GivenWithCleanUp.class, "given");
        World world = (World)stub(World.class);
        
        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Given) given);
                
            }};
            
        given.expects("cleanUp").once();
            
        scenario.specify();
        scenario.run(world);
        scenario.cleanUp(world);
        scenario.cleanUp(world);
        
        verifyMocks();
    }
    
    public void shouldCleanUpEvenIfStepFailed() {
        // given
        final Mock given = mock(GivenWithCleanUp.class, "given");
        World world = (World)stub(World.class);
        
        Scenario scenario = new MultiStepScenario() {
            public void specifySteps() {
                given((Given) given);
                
            }};
            
        given.expects("setUp").will(throwException(new VerificationException("")));
        given.expects("cleanUp").once();
            
        scenario.specify();
        
        try {
            scenario.run(world);
        } catch (VerificationException e) {
            // expected
        }
        
        scenario.cleanUp(world);
        verifyMocks();
    }
    
    public void shouldFailIfSpecifiedTwice() throws Exception {
        final Scenario scenario = new MultiStepScenario(){
            public void specifySteps() {}};
            
        Exception exception = runAndCatch(IllegalStateException.class, new Block() {
            public void run() throws Exception {
                scenario.specify();
                scenario.specify();
            }
        });
        
        ensureThat(exception, isNotNull());
    }

}
