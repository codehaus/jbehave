/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Arrays;

import com.thoughtworks.jbehave.core.visitor.CompositeVisitable;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Context extends CompositeVisitable {
    public static final Context NULL = new Context(new Given[0]);
    
    /** A Scenario and a bunch of givens */
    public Context(Scenario scenario, Given[] givens) {
        add(new GivenScenario(scenario));
        addAll(Arrays.asList(givens));
    }

    /** Just one given */
    public Context(Given given) {
        add(given);
    }

    /** A bunch of givens */
    public Context(Given[] givens) {
        addAll(Arrays.asList(givens));
    }

    /** One scenario and one given */
    public Context(Scenario scenario, Given given) {
        this(scenario, new Given[] {given});
    }
}
