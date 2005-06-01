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
public class Givens implements Given {
    public static final Givens NULL = new Givens(new Given[0]);
    
	private final VisitableArrayList givens = new VisitableArrayList();
	
    /** A Scenario and a bunch of givens */
    public Givens(Scenario scenario, Given[] givens) {
        this.givens.add(new GivenScenario(scenario));
        this.givens.addAll(Arrays.asList(givens));
    }

    /** Just one given */
    public Givens(Given given) {
        this.givens.add(given);
    }

    /** A bunch of givens */
    public Givens(Given[] givens) {
        this.givens.addAll(Arrays.asList(givens));
    }

    /** One scenario and one given */
    public Givens(Scenario scenario, Given given) {
        this(scenario, new Given[] {given});
    }

	public void accept(Visitor visitor) {
		givens.accept(visitor);
	}

	public void setUp(World world) throws Exception {
		// TODO implement setUp
		throw new UnsupportedOperationException("TODO");
	}

	public boolean containsMocks() {
		// TODO implement containsMocks
		throw new UnsupportedOperationException("TODO");
	}

	public void verifyMocks() {
		// TODO implement verifyMocks
		throw new UnsupportedOperationException("TODO");
	}
}
