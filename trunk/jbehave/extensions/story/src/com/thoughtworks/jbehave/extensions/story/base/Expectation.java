/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;


/**
 * Represents an expectation on a scenario
 * 
 * This class is stateful - see {@link #accept(Visitor)} for details.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class Expectation implements Visitable {
    public abstract void setExpectationIn(Environment environment) throws Exception;
    public abstract void verify(Environment environment) throws Exception;

    private boolean alreadyAcceptedVisitor = false;

    /**
     * Passes itself into a {@link Visitor}
     * 
     * The first time this method is called, the object passes itself into
     * the {@link Visitor#visitExpectationBeforeTheEvent(Expectation)} method.<br/>
     * <br/>
     * The second and subsequent times, it passes itself into the
     * {@link Visitor#visitExpectationAfterTheEvent(Expectation)} method.<br/>
     * <br/>
     * It is up to you to ensure that you visit the {@link Event} in the meantime!
     */
    public void accept(Visitor visitor) throws Exception {
        if (!alreadyAcceptedVisitor) {
            visitor.visitExpectationBeforeTheEvent(this);
            alreadyAcceptedVisitor = true;
        }
        else {
            visitor.visitExpectationAfterTheEvent(this);
        }
        
    }
}
