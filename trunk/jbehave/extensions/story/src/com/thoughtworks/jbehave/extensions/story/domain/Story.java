/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;
import com.thoughtworks.jbehave.util.ConvertCase;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Story implements Visitable {
    private final Narrative narrative;
    private final AcceptanceCriteria criteria;

    public Story(Narrative narrative, AcceptanceCriteria criteria) {
        this.narrative = narrative;
        this.criteria = criteria;
    }

    public void addScenario(Scenario scenario) {
        criteria.addScenario(scenario);
    }
    
    public String getTitle() {
        return new ConvertCase(this).toSeparateWords();
    }
    
    public Scenario getScenario(String name) {
        for (Iterator i = criteria.iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            if (scenario.getDescription().equals(name)) {
                return scenario;
            }
        }
        throw new RuntimeException(name);
    }
    
    public List getScenarios() {
        return criteria.getScenarios();
    }
    
    public Narrative getNarrative() {
        return narrative;
    }
    
    public void accept(Visitor visitor) throws Exception {
        visitor.visitStory(this);
        narrative.accept(visitor);
        criteria.accept(visitor);
    }
}
