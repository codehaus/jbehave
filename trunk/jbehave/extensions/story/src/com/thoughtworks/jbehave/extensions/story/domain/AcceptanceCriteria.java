/*
 * Created on 28-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.util.VisitableArrayList;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AcceptanceCriteria implements Visitable {
    private final VisitableArrayList scenarios = new VisitableArrayList();
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
        scenarios.accept(visitor);
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
    }

    public Iterator iterator() {
        return scenarios.iterator();
    }

    public List getScenarios() {
        return scenarios;
    }
}
