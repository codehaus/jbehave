/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.story.invoker.VisitingScenarioInvoker;



/**
 * Adapter to make a {@link Scenario} look like a {@link GivenUsingMiniMock}
 * so it can be used to set up a {@link Environment}
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class GivenScenario extends GivenUsingMiniMock {

    private final Scenario scenario;

    public GivenScenario(Scenario scenario) {
        this.scenario = scenario;
    }
    
    public Scenario getScenario() {
        return scenario;
    }

    public void setUp(Environment environment) throws Exception {
        scenario.accept(new VisitingScenarioInvoker(scenario.getStory().title(), 
        		environment));
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
}
