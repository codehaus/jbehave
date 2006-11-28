/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.renderer.Renderer;
import jbehave.core.util.CamelCaseConverter;

/**
 * <p>A Scenario describes a series of events, run in a particular
 * context, and for which certain outcomes are expected.</p>
 * 
 * <p>This class allows a scenario to be implemented with
 * instances of particular contexts (Givens), events and outcomes.
 * Each {@link Given}, {@link Event} or {@link Outcome} should be 
 * represented by a different class, which can be reused in different
 * scenarios.</p>
 * 
 * <p>A scenario's elements can be used to describe it thus:<ul>
 * <li>Given <a context></li>
 * <li>When <an event happens></li>
 * <li>Then <an outcome should occur></li>.
 * </ul></p>
 * 
 * <p>eg:<ul>
 * <li>Given that I already have two crosses in opposite corners</li>
 * <li>When I put a cross in the middle</li>
 * <li>Then I should win the game.</li>
 * </ul></p>
 * 
 * <p>Sometimes a scenario may only finish after several events and
 * the outcomes that result from them. A {@link Step}, as implemented
 * by {@link EventOutcomeStep}, allows these events and outcomes to
 * be chained.</p>
 * 
 * <p>Each scenario runs as follows:<ul>
 * <li>Set up the context</li>
 * <li>For each step, or for an outcome and event if there isn't a step</li>
 * <ul><li>Set up the outcome expectations</li>
 * <li>Make the event happen</li>
 * <li>Verify that the outcome occurred.</li></ul></ul>
 * 
 * <p>Multiple contexts, events and outcomes can be bound together
 * using {@link Givens}, {@link Events} and {@link Outcomes}, so that
 * they aggregate to form a single component.</p>
 * 
 * <p>A number of utility constructors are provided to make construction
 * of a ScenarioUsingMiniMock easier. The annotation methods <code>given</code>,
 * <code>when</code> and <code>then</code> can be used to make the Scenario
 * code easier to read, and to create aggregates simply.</p>
 * 
 * <p>ScenarioUsingMiniMock also gives you easy access to JBehave's Minimock
 * framework. This allows you to mock out the bits of the system that you
 * haven't written yet. The Scenario will report the use of mocks, but
 * will not consider them a failure.</p>
 *
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:liz@thoughtworks.com">Elizabeth Keogh</a>
 */
public class ScenarioUsingMiniMock extends UsingMiniMock implements Scenario {
    protected final Given given;
    protected final Step step;
    
    public ScenarioUsingMiniMock(Event event, Outcome outcome) {
        this(Givens.NULL, event, outcome);
    }
    
    public ScenarioUsingMiniMock(Given given, Event event, Outcome outcome) {
        this(given, new EventOutcomeStep(event, outcome));
    }
    
    public ScenarioUsingMiniMock(Given given, Event eventA, Outcome outcomeA, Event eventB, Outcome outcomeB) {
        this(given, new Steps(new EventOutcomeStep(eventA, outcomeA), new EventOutcomeStep(eventB, outcomeB)));
    }
    
    public ScenarioUsingMiniMock(Given given, Event eventA, Outcome outcomeA, Event eventB, Outcome outcomeB, Event eventC, Outcome outcomeC) {
        this(given, new Steps(new EventOutcomeStep(eventA, outcomeA), new EventOutcomeStep(eventB, outcomeB), new EventOutcomeStep(eventC, outcomeC)));
    }
    
    public ScenarioUsingMiniMock(Given given, Step step) {
        this.given = given;
        this.step = step;
    }
    
    public void run(World world) {
        given.setUp(world);
        step.perform(world);
    }

    public void tidyUp(World world) {
        step.undoIn(world);
        given.tidyUp(world);
    }   

    public void narrateTo(Renderer renderer) {
        renderer.renderScenario(this);
        given.narrateTo(renderer);
        step.narrateTo(renderer);
    }

	protected static Given given(Given given) {
		return given;
	}
	
	protected static Given given(Given given1, Given given2) {
		return new Givens(given1, given2);
	}
	
	protected static Given given(Given given1, Given given2, Given given3) {
		return new Givens(given1, given2, given3);
	}
	
	protected static Given given(Given[] givens) {
		return new Givens(givens);
	}	

	protected static Event when(Event event) {
		return event;
	}

	protected static Event when(Event event1, Event event2) {
		return new Events(event1, event2);
	}	
	
	protected static Event when(Event event1, Event event2, Event event3) {
		return new Events(event1, event2, event3);
	}	
	
	protected static Event when(Event[] events) {
		return new Events(events);
	}	

	protected static Outcome then(Outcome outcome) {
		return outcome;
	}

	protected static Outcome then(Outcome outcome1, Outcome outcome2) {
		return new Outcomes(outcome1, outcome2);
	}

	protected static Outcome then(Outcome outcome1, Outcome outcome2, Outcome outcome3) {
		return new Outcomes(outcome1, outcome2, outcome3);
	}

	protected static Outcome then(Outcome[] outcomes) {
		return new Outcomes(outcomes);
	}
	
	protected static Step step(Step step) {
		return step;
	}
	
	protected static Step step(Step step1, Step step2) {
		return new Steps(step1, step2);
	}

	protected static Step step(Step step1, Step step2, Step step3) {
		return new Steps(step1, step2, step3);
	}
	
	protected static Steps step(Step[] steps) {
		return new Steps(steps);
	}

	protected static Step step(Event event, Outcome outcome) {
		return new EventOutcomeStep(event, outcome);
	}
	
	protected static Given given(Scenario scenario) {
		return new GivenScenario(scenario);
	}

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Scenario name=");
        buffer.append(new CamelCaseConverter(this).toPhrase());
        buffer.append(",\ngiven=");
        buffer.append(given);
        buffer.append(",\nstep=");
        buffer.append(step);
        buffer.append("]");
        return buffer.toString();
    }
}
