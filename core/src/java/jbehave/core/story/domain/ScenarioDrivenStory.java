/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.renderer.Renderer;
import jbehave.core.util.ConvertCase;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDrivenStory implements Story {
    private final Narrative narrative;
    private final Scenarios scenarios;
    private final ArrayList listeners;

    public ScenarioDrivenStory(Narrative narrative) {
        this.narrative = narrative;
        this.scenarios = new Scenarios();
        listeners = new ArrayList();
    }

    public void addScenario(Scenario scenario) {
        scenarios.addScenario(scenario);
    }
    
    public String title() {
        return new ConvertCase(getClass()).toSeparateWords();
    }
    
    public Scenario scenario(String name) {
        for (Iterator i = scenarios.scenarios().iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            if (scenario.getDescription().equals(name)) {
                return scenario;
            }
        }
        throw new RuntimeException(name);
    }
    
    public List scenarios() {
        return scenarios.scenarios();
    }
    
    public Narrative narrative() {
        return narrative;
    }

    public void run(World world) {
        scenarios.run(world, (BehaviourListener[]) listeners.toArray(new BehaviourListener[listeners.size()]));
    }
    
    public void addListener(BehaviourListener listener) {
        listeners.add(listener);       
    }

    public void narrateTo(Renderer renderer) {
        renderer.renderStory(this);
        narrative.narrateTo(renderer);
        scenarios.narrateTo(renderer);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Story narrative=");
        buffer.append(narrative);
        buffer.append(",\nscenarios=");
        buffer.append(scenarios);
        buffer.append("]");
        return buffer.toString();
    }

}
