/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Arrays;
import java.util.List;

import com.thoughtworks.jbehave.util.CompositeVisitable;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Outcome extends CompositeVisitable {

    public Outcome(Expectation[] expectations) {
        addAll(Arrays.asList(expectations));
    }
    
    public Outcome(Expectation expectation) {
        this(new Expectation[] {expectation});
    }
    
    public Outcome(Expectation expectation1, Expectation expectation2) {
        this(new Expectation[] {expectation1, expectation2});
    }
    
    public Outcome(Expectation expectation1, Expectation expectation2, Expectation expectation3) {
        this(new Expectation[] {expectation1, expectation2, expectation3});
    }
    
    public List expectations() {
        return components();
    }
}
