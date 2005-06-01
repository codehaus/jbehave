/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.Visitor;

/**
 * Represents an outcome of a scenario
 * 
 * This class is stateful - see {@link #accept(Visitor)} for details.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class OutcomeUsingMiniMock extends UsingMiniMock implements Outcome {
    public abstract void setExpectationIn(World world);
    public abstract void verify(World world);
	public void accept(Visitor visitor) {
		visitor.visitOutcome(this);
	}
}
