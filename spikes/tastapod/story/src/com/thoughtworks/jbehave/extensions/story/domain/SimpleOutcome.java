/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.jbehave.extensions.story.visitor.CompositeVisitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleOutcome extends CompositeVisitable implements Outcome {
    
    public SimpleOutcome(List expectations) {
        visitables.addAll(expectations);
    }
    
    public SimpleOutcome(Expectation expectation) {
        this(Collections.singletonList(expectation));
    }
    
    public SimpleOutcome(Expectation expectation1, Expectation expectation2) {
        this(Arrays.asList(new Expectation[] {expectation1, expectation2}));
    }
    
    public SimpleOutcome(Expectation expectation1, Expectation expectation2, Expectation expectation3) {
        this(Arrays.asList(new Expectation[] {expectation1, expectation2, expectation3}));
    }
    
    public List getExpectations() {
        return visitables;
    }

    protected void visitSelf(Visitor visitor) {
        visitor.visitOutcome(this);
    }
}
