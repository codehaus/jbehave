/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.story.visitor.CompositeVisitableUsingMiniMock;


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
    
    public List outcomes() {
        return visitables;
    }

	public void setExpectationIn(final World world) {
		for (Iterator i = visitables.iterator(); i.hasNext();) {
			((Outcome) i.next()).setExpectationIn(world);
		}
	}

	public void verify(World world) {
		for (Iterator i = visitables.iterator(); i.hasNext();) {
			((Outcome) i.next()).verify(world);
		}
	}
}
