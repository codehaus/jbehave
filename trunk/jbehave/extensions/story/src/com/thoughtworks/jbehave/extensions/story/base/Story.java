/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Narrative;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.VisitableArrayList;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;
import com.thoughtworks.jbehave.util.ConvertCase;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Story implements Visitable {

    public static final Story NULL = new Story("", "", "");
    
    private final VisitableArrayList criteria = new VisitableArrayList();
    private final Narrative narrative;

    public Story(String role, String feature, String benefit) {
        this.narrative = new Narrative(role, feature, benefit);
    }

    public void addScenario(Scenario scenario) {
        criteria.add(scenario);
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
        return criteria;
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
