/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;
import jbehave.core.story.visitor.VisitorSupport;


/**
 * Represents a composite {@link Outcome}.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Outcomes extends CompositeVisitableUsingMiniMock implements Outcome {
	
	public Outcomes(Outcome[] outcomes) {
		super(outcomes);
    }
    
    public Outcomes(Outcome outcome) {
        this(new Outcome[] {outcome});
    }
    
    public Outcomes(Outcome outcome1, Outcome outcome2) {
        this(new Outcome[] {outcome1, outcome2});
    }
    
    public Outcomes(Outcome outcome1, Outcome outcome2, Outcome outcome3) {
        this(new Outcome[] {outcome1, outcome2, outcome3});
    }
    
	/** delegate to components */
	public void setExpectationIn(final World world) {
		accept(new VisitorSupport() {
			public void visitOutcome(Outcome outcome) {
				outcome.setExpectationIn(world);
			}
		});
	}

	/** delegate to components */
	public void verify(final World world) {
		accept(new VisitorSupport() {
			public void visitOutcome(Outcome outcome) {
				outcome.verify(world);
			}
		});
	}
}
