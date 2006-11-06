package jbehave.core.story.domain;

import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;

public class Events extends CompositeVisitableUsingMiniMock implements Event {

    private final Event[] events;

    /** Constructor with a bunch of events */
    public Events(Event[] events) {
		super(events);
        this.events = events;
    }

    public Events(Event event) {
		this(new Event[] {event});
    }

    public Events(Event event1, Event event2) {
        this(new Event[] {event1, event2});
    }

    public Events(Event event1, Event event2, Event event3) {
        this(new Event[] {event1, event2, event3});
    }
    
	public void occurIn(final World world) {
        for(int i = 0; i < events.length; i++) {
            events[i].occurIn(world);
        }
	}

    public void undoIn(final World world) {
        for(int i = events.length - 1; i > -1; i--) {
            events[i].undoIn(world);
        }
    }
}
