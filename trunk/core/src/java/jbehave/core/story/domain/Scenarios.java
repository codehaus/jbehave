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
import jbehave.core.util.CamelCaseConverter;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Scenarios implements Renderable {
	private final List scenarios = new ArrayList();

	public List scenarios() {
        return scenarios;
    }

	public void run(World world, Class storyClass, BehaviourListener[] listeners) {
        for (Iterator i = scenarios.iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            informListeners(listeners, runScenario(world, storyClass, scenario));
        }
	}

    private void informListeners(BehaviourListener[] listeners, ScenarioResult result) {
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].gotResult(result);
        }
    }

    private ScenarioResult runScenario(World world, Class storyClass, Scenario scenario) {
        ScenarioResult result;
        String storyDescription = new CamelCaseConverter(storyClass).toPhrase();
        String description = new CamelCaseConverter(scenario).toPhrase();
        
        try {                
            scenario.run(world);
            result = new ScenarioResult(description, storyDescription, 
                    scenario.containsMocks() ? ScenarioResult.USED_MOCKS : ScenarioResult.SUCCEEDED);
        } catch (NestedVerificationException nve) {
            result = new ScenarioResult(description, storyDescription, nve);
        } finally {
            scenario.cleanUp(world);
        }
        
        return result;
    }
    
    public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}

    public void narrateTo(Renderer renderer) {
        for ( Iterator i = scenarios.iterator(); i.hasNext(); ){
            ((Scenario) i.next()).narrateTo(renderer);
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
