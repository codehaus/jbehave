/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.util.VisitableUsingMiniMock;


/**
 * An expected sequence of events
 * 
 * A <tt>Scenario</tt> is defined by the sentence:<br/>
 * <br/>
 * <b>Given X, When Y, Then Z</b>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioUsingMiniMock extends VisitableUsingMiniMock implements Scenario {
    protected final Context context;
    protected final Event event;
    protected final Outcome outcome;
    protected final String name;
    protected final Story story;
    
    public ScenarioUsingMiniMock(String name, Story story, Context context, Event event, Outcome outcome) {
        this.name = name;
        this.story = story;
        this.context = context;
        this.event = event;
        this.outcome = outcome;
    }
    
    public ScenarioUsingMiniMock(String name, Story story, Event event, Outcome outcome) {
        this(name, story, Context.NULL, event, outcome);
    }
    
    public Story getStory() {
        return story;
    }

    public String getDescription() {
        return name;
    }
    public Context getContext() {
        return context;
    }
    public Event getEvent() {
        return event;
    }
    public Outcome getOutcome() {
        return outcome;
    }
    
    public void accept(Visitor visitor) {
        super.accept(visitor);
        context.accept(visitor);
        outcome.accept(visitor);
        event.accept(visitor);
        outcome.accept(visitor);
    }
}
