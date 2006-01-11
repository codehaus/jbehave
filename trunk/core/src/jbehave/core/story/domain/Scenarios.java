/*
 * Created on 28-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import java.util.ArrayList;
import java.util.List;

import jbehave.core.exception.NestedVerificationException;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.result.ScenarioResult;
import jbehave.core.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Scenarios {
	private final ArrayList scenarios = new ArrayList();

	public List scenarios() {
        return scenarios;
    }

	public void run(World world, BehaviourListener[] listeners) {
        for (int i = 0; i < scenarios.size(); i++) {
            informListeners(listeners, runScenario(world, (Scenario) scenarios.get(i)));
        }
	}

    private void informListeners(BehaviourListener[] listeners, ScenarioResult result) {
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].gotResult(result);
        }
    }

    private ScenarioResult runScenario(World world, Scenario scenario) {
        try {                
            scenario.run(world);
        } catch (NestedVerificationException nve) {
            return new ScenarioResult(scenario.getDescription(), scenario.getStoryName(), nve);
        }
        return new ScenarioResult(scenario.getDescription(), scenario.getStoryName(), 
                scenario.containsMocks() ? ScenarioResult.USED_MOCKS : ScenarioResult.SUCCEEDED);
    }
    
    public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}

    public void accept(Visitor visitor) {
        for (int i = 0; i < scenarios.size(); i++) {
            ((Scenario) scenarios.get(i)).accept(visitor);
        }
    }
}
