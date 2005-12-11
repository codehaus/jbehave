package jbehave.core.story.domain;

import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;
import jbehave.core.story.visitor.VisitorSupport;

public class Events extends CompositeVisitableUsingMiniMock implements Event {

    /** Constructor with a bunch of events */
    public Events(Event[] events) {
		super(events);
    }

    /** Just one event */
    public Events(Event event) {
		super(new Event[] {event});
    }

    public Events(Event event1, Event event2) {
		super(new Event[] {event1, event2});
    }

    public Events(Event event1, Event event2, Event event3) {
		super(new Event[] {event1, event2, event3});
    }
	public void occurIn(final World world) {
		accept(new VisitorSupport() {
			public void visitEvent(Event event) {
				event.occurIn(world);
			}
		});
	}
}
