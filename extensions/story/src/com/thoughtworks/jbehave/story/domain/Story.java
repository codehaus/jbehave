/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.util.ConvertCase;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Story implements Visitable {
    private final Narrative narrative;
    private final AcceptanceCriteria acceptanceCriteria;

    public Story(Narrative narrative, AcceptanceCriteria criteria) {
        this.narrative = narrative;
        this.acceptanceCriteria = criteria;
    }

    public void addScenario(ScenarioUsingMiniMock scenario) {
        acceptanceCriteria.add(scenario);
    }
    
    public String title() {
        return new ConvertCase(getClass()).toSeparateWords();
    }
    
    public Scenario scenario(String name) {
        for (Iterator i = acceptanceCriteria.iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            if (scenario.getDescription().equals(name)) {
                return scenario;
            }
        }
        throw new RuntimeException(name);
    }
    
    public List scenarios() {
        return acceptanceCriteria.scenarios();
    }
    
    public Narrative narrative() {
        return narrative;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
        narrative.accept(visitor);
        acceptanceCriteria.accept(visitor);
    }
    
    public String toString() {
        return "Story: narrative=" + narrative + ", acceptanceCriteria=" + acceptanceCriteria;
    }
}
