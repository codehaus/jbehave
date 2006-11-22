/*
 * Created on 28-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.core.exception.NestedVerificationException;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.renderer.Renderable;
import jbehave.core.story.renderer.Renderer;
import jbehave.core.story.result.ScenarioResult;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Scenarios implements Renderable {
	private final List scenarios = new ArrayList();

	public List scenarios() {
        return scenarios;
    }

	public void run(World world, BehaviourListener[] listeners) {
        for (int i = 0; i < scenarios.size(); i++) {
            Scenario scenario = (Scenario) scenarios.get(i);
            informListeners(listeners, runScenario(world, scenario));
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
        scenario.tidyUp(world);
        
        return new ScenarioResult(scenario.getDescription(), scenario.getStoryName(), 
                scenario.containsMocks() ? ScenarioResult.USED_MOCKS : ScenarioResult.SUCCEEDED);
    }
    
    public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}

    public void narrateTo(Renderer renderer) {
        for (int i = 0; i < scenarios.size(); i++) {
            ((Scenario) scenarios.get(i)).narrateTo(renderer);
        }
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("\n");
        for ( Iterator i = scenarios.iterator(); i.hasNext(); ){
            Scenario scenario = (Scenario)i.next();
            buffer.append(scenario);
            buffer.append("\n");
        }
        buffer.append("]");
        return buffer.toString();
    }

}
