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

import jbehave.core.exception.NestedVerificationException;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.renderer.Renderer;
import jbehave.core.story.result.ScenarioResult;
import jbehave.core.util.CamelCaseConverter;

/**
 * <p>A ScenarioDrivenStory is an executable description of a value 
 * that the customer wants, with {@link Scenario}s to illustrate 
 * the Story and verify that it has been delivered.</p>
 * 
 * <p>A Story is the top level of JBehave's Story framework;
 * the scenarios are the equivalent of acceptance tests. A Story 
 * can be executed using the {@link jbehave.core.story.StoryRunner}, 
 * and documented using a {@link jbehave.core.story.StoryPrinter}.</p>
 * 
 * <p>To write a ScenarioDrivenStory, provide a {@link Narrative},
 * and add some {@link Scenario}s. Simple!</p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:liz@thoughtworks.com">Elizabeth Keogh</a>
 */
public class ScenarioDrivenStory implements Story {
    private final Narrative narrative;
    private final List scenarios;
    private final List listeners;

    public ScenarioDrivenStory(Narrative narrative) {
        this.narrative = narrative;
        this.scenarios = new ArrayList();
        this.listeners = new ArrayList();
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
    }

    public List scenarios() {
        return scenarios;
    }
    
    public Narrative narrative() {
        return narrative;
    }

    public void run(World world) {
        for (Iterator i = scenarios.iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            informListeners(runScenario(world, this.getClass(), scenario));
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
        
    private void informListeners(ScenarioResult result) {
        for ( Iterator i = listeners.iterator(); i.hasNext(); ){
            ((BehaviourListener) i.next()).gotResult(result);
        }
    }
    
    public void addListener(BehaviourListener listener) {
        listeners.add(listener);       
    }

    public void narrateTo(Renderer renderer) {
        renderer.renderStory(this);
        narrative.narrateTo(renderer);
        for ( Iterator i = scenarios.iterator(); i.hasNext(); ){
            ((Scenario) i.next()).narrateTo(renderer);
        }
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
