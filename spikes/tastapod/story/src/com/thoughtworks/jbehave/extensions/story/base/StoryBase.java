/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.util.Visitable;
import com.thoughtworks.jbehave.extensions.story.util.Visitor;
import com.thoughtworks.jbehave.util.CaseConverter;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryBase implements Story {

    private final String role;
    private final String feature;
    private final String benefit;

    private final List scenarios = new ArrayList();
    
    public StoryBase(String role, String feature, String benefit) {
        this.role = role;
        this.feature = feature;
        this.benefit = benefit;
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
    }
    
    public String getName() {
        return new CaseConverter().toSeparateWords(this);
    }
    
    public Scenario getScenario(String name) {
        for (Iterator i = scenarios.iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            if (scenario.getDescription().equals(name)) {
                return scenario;
            }
        }
        throw new RuntimeException(name);
    }
    
    public List getScenarios() {
        return scenarios;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getFeature() {
        return feature;
    }
    
    public String getBenefit() {
        return benefit;
    }
    
    public void accept(Visitor visitor) {
        visitor.visitStory(this);
        for (Iterator i = scenarios.iterator(); i.hasNext();) {
            ((Visitable) i.next()).accept(visitor);
        }
    }
}
