/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.story.listener.ScenarioListener;


/**
 * Adapter to make a {@link Scenario} look like a {@link Given}
 * so it can be used to set up a {@link Environment}
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class GivenScenario extends Given {

    private final Scenario scenario;

    public GivenScenario(Scenario scenario) {
        this.scenario = scenario;
    }
    
    public Scenario getScenario() {
        return scenario;
    }

    public void setUp(Environment environment) throws Exception {
        // TODO recursively apply the givens and the event from the scenario to the current context
    }

    public Context getContext() {
        return scenario.getContext();
    }
    public Event getEvent() {
        return scenario.getEvent();
    }
    public String getDescription() {
        return scenario.getDescription();
    }
    public Outcome getOutcome() {
        return scenario.getOutcome();
    }
    public Story getStory() {
        return scenario.getStory();
    }
    public void setListener(ScenarioListener listener) {
        scenario.setListener(listener);
    }
}
