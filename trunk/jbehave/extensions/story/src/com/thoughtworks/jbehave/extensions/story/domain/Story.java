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

import com.thoughtworks.jbehave.util.CompositeVisitable;
import com.thoughtworks.jbehave.util.ConvertCase;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Story extends CompositeVisitable {
    private final Narrative narrative;
    private final AcceptanceCriteria criteria;

    public Story(Narrative narrative, AcceptanceCriteria criteria) {
        this.narrative = narrative;
        this.criteria = criteria;
        add(narrative);
        add(criteria);
    }

    public void addScenario(ScenarioUsingMiniMock scenario) {
        criteria.add(scenario);
    }
    
    public String getTitle() {
        return new ConvertCase(this).toSeparateWords();
    }
    
    public ScenarioUsingMiniMock scenario(String name) {
        for (Iterator i = criteria.iterator(); i.hasNext();) {
            ScenarioUsingMiniMock scenario = (ScenarioUsingMiniMock) i.next();
            if (scenario.getDescription().equals(name)) {
                return scenario;
            }
        }
        throw new RuntimeException(name);
    }
    
    public List scenarios() {
        return criteria.scenarios();
    }
    
    public Narrative narrative() {
        return narrative;
    }
}
