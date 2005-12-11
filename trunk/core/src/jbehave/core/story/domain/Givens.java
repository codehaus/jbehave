/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;
import jbehave.core.story.visitor.VisitorSupport;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Givens extends CompositeVisitableUsingMiniMock implements Given {
    public static final Givens NULL = new Givens(new Given[0]);
    
    /** Constructor with a bunch of givens */
    public Givens(Given[] givens) {
		super(givens);
    }

    /** Just one given */
    public Givens(Given given) {
		super(new Given[] {given});
    }

    public Givens(Given given1, Given given2) {
		super(new Given[] {given1, given2});
    }

    public Givens(Given given1, Given given2, Given given3) {
		super(new Given[] {given1, given2, given3});
    }

	public void setUp(final World world) {
		accept(new VisitorSupport() {
			public void visitGiven(Given given) {
				given.setUp(world);
			}
		});
	}
}
