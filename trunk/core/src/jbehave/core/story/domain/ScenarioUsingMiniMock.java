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
    protected final Event event;
    protected final Outcome outcome;
    protected final String name;
    protected final String storyName;
    
    public ScenarioUsingMiniMock(String name, String storyName, Given given, Event event, Outcome outcome) {
        this.name = name;
        this.storyName = storyName;
        this.given = given;
        this.event = event;
        this.outcome = outcome;
    }
    
    public ScenarioUsingMiniMock(String name, String storyName, Event event, Outcome outcome) {
        this(name, storyName, Givens.NULL, event, outcome);
    }
    
    public String getStoryName() {
        return storyName;
    }

    public String getDescription() {
        return name;
    }
    public Given getGiven() {
        return given;
    }
    public Event getEvent() {
        return event;
    }
    public Outcome getOutcome() {
        return outcome;
    }
    
    public void accept(Visitor visitor) {
		visitor.visitScenario(this);
        given.accept(visitor);
        outcome.accept(visitor);
        event.accept(visitor);
    }
}
