/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.visitor.Visitor;



/**
 * An expected sequence of events.
 * 
 * A <tt>Scenario</tt> is defined by the sentence:<br/>
 * <br/>
 * <b>Given X, When Y, Then Z</b>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioUsingMiniMock extends UsingMiniMock implements Scenario {
    protected final Given given;
    protected final Step step;
    protected final String name;
    protected final String storyName;
    
    public ScenarioUsingMiniMock(String name, String storyName, Event event, Outcome outcome) {
        this(name, storyName, Givens.NULL, event, outcome);
    }
    
    public ScenarioUsingMiniMock(String name, String storyName, Given given, Event event, Outcome outcome) {
        this(name, storyName, given, new EventOutcomeStep(event, outcome));
    }
    
    public ScenarioUsingMiniMock(String name, String storyName, Given given, Event eventA, Outcome outcomeA, Event eventB, Outcome outcomeB) {
        this(name, storyName, given, new Steps(new EventOutcomeStep(eventA, outcomeA), new EventOutcomeStep(eventB, outcomeB)));
    }
    
    public ScenarioUsingMiniMock(String name, String storyName, Given given, Event eventA, Outcome outcomeA, Event eventB, Outcome outcomeB, Event eventC, Outcome outcomeC) {
        this(name, storyName, given, new Steps(new EventOutcomeStep(eventA, outcomeA), new EventOutcomeStep(eventB, outcomeB), new EventOutcomeStep(eventC, outcomeC)));
    }
    
    public ScenarioUsingMiniMock(String name, String storyName, Given given, Step step) {
        this.name = name;
        this.storyName = storyName;
        this.given = given;
        this.step = step;
    }
    
    public String getStoryName() {
        return storyName;
    }

    public String getDescription() {
        return name;
    }
    
    public void run(World world) {
        given.setUp(world);
        step.perform(world);
    }

    public void tidyUp(World world) {
        step.undoIn(world);
        given.tidyUp(world);
    }   

    public void accept(Visitor visitor) {
        visitor.visitScenario(this);
        given.accept(visitor);
        step.accept(visitor);
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
        buffer.append(name);
        buffer.append(", storyName=");
        buffer.append(storyName);
        buffer.append(",\ngiven=");
        buffer.append(given);
        buffer.append(",\nstep=");
        buffer.append(step);
        buffer.append("]");
        return buffer.toString();
    }
}
