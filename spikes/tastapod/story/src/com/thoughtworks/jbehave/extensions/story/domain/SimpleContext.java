/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Collections;
import java.util.List;

import com.thoughtworks.jbehave.extensions.story.visitor.CompositeVisitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleContext extends CompositeVisitable implements Context {

    /** A Scenario and a Given */
    public SimpleContext(Scenario scenario, List givens) {
        visitables.add(new GivenScenario(scenario));
        visitables.addAll(givens);
    }

    /** Just one given */
    public SimpleContext(Given given) {
        super(Collections.singletonList(given));
    }

    /** A bunch of givens */
    public SimpleContext(List givens) {
        super(givens);
    }

    public SimpleContext(Scenario scenario, Given given) {
        this(scenario, Collections.singletonList(given));
    }

    public List getGivens() {
        return visitables;
    }

    protected void visitSelf(Visitor visitor) {
        visitor.visitContext(this);
    }
}
