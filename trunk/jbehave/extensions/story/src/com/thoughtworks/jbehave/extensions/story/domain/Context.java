/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Arrays;

import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.util.VisitableArrayList;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Context implements Visitable {
    public static final Context NULL = new Context(new Given[0]);
    
    private final VisitableArrayList visitables = new VisitableArrayList();
    
    /** A Scenario and a Given */
    public Context(Scenario scenario, Given[] givens) {
        visitables.add(new GivenScenario(scenario));
        visitables.addAll(Arrays.asList(givens));
    }

    /** Just one given */
    public Context(Given given) {
        visitables.add(given);
    }

    /** A bunch of givens */
    public Context(Given[] givens) {
        visitables.addAll(Arrays.asList(givens));
    }

    public Context(Scenario scenario, Given given) {
        this(scenario, new Given[] {given});
    }
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
        visitables.accept(visitor);
    }
}
