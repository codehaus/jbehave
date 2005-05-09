/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import java.util.Arrays;

import com.thoughtworks.jbehave.story.visitor.Visitable;
import com.thoughtworks.jbehave.story.visitor.VisitableArrayList;
import com.thoughtworks.jbehave.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Context implements Visitable {
    public static final Context NULL = new Context(new Given[0]);
    
	private final VisitableArrayList givens = new VisitableArrayList();
	
    /** A Scenario and a bunch of givens */
    public Context(Scenario scenario, Given[] givens) {
        this.givens.add(new GivenScenario(scenario));
        this.givens.addAll(Arrays.asList(givens));
    }

    /** Just one given */
    public Context(Given given) {
        this.givens.add(given);
    }

    /** A bunch of givens */
    public Context(Given[] givens) {
        this.givens.addAll(Arrays.asList(givens));
    }

    /** One scenario and one given */
    public Context(Scenario scenario, Given given) {
        this(scenario, new Given[] {given});
    }

	public void accept(Visitor visitor) {
		visitor.visitContext(this);
		givens.accept(visitor);
	}
}
