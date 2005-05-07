/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.story.visitor.VisitableUsingMiniMock;


/**
 * Represents an expectation on a scenario
 * 
 * This class is stateful - see {@link #accept(Visitor)} for details.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class ExpectationUsingMiniMock extends VisitableUsingMiniMock implements Expectation {
    public abstract void setExpectationIn(Environment environment);
    public abstract void verify(Environment environment);
}
