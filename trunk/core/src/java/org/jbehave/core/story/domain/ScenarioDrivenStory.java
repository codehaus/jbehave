/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.story.renderer.Renderer;
import org.jbehave.core.story.result.ScenarioResult;
import org.jbehave.core.util.CamelCaseConverter;


/**
 * <p>A ScenarioDrivenStory is an executable description of a value 
 * that the customer wants, with {@link Scenario}s to illustrate 
 * the Story and verify that it has been delivered.</p>
 * 
 * <p>A Story is the top level of JBehave's Story framework;
 * the scenarios are the equivalent of acceptance tests. A Story 
 * can be executed using the {@link org.jbehave.core.story.StoryRunner}, 
 * and documented using a {@link org.jbehave.core.story.StoryPrinter}.</p>
 * 
 * <p>To write a ScenarioDrivenStory, provide a {@link Narrative},
 * and add some {@link Scenario}s. Simple!</p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:liz@thoughtworks.com">Elizabeth Keogh</a>
 */
public abstract class ScenarioDrivenStory implements Story {
    private final Narrative narrative;
    private final List scenarios;
    private final List listeners;

    public ScenarioDrivenStory(Narrative narrative) {
        this.narrative = narrative;
        this.scenarios = new ArrayList();
        this.listeners = new ArrayList();
    }

    public void addScenario(Scenario scenario) {
        scenario.specify();
        scenarios.add(scenario);
    }

    public List scenarios() {
        return scenarios;
    }
    
    public Narrative narrative() {
        return narrative;
    }
    
    public void run() {
        for (Iterator i = scenarios.iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            informListeners(runScenario(createWorld(), this.getClass(), scenario));
        }
    }

    /**
     * Creates a new world to be passed into each scenario
     * 
     * The default is a {@link HashMapWorld}. Override this to supply your own world
     * 
     * @return a new world
     */
    protected World createWorld() {
        return new HashMapWorld();
    }

    private ScenarioResult runScenario(World world, Class storyClass, Scenario scenario) {
        ScenarioResult result;
        String storyDescription = new CamelCaseConverter(storyClass).toPhrase();
        String description = new CamelCaseConverter(scenario).toPhrase();
        
        try {                
            scenario.run(world);
            result = new ScenarioResult(description, storyDescription, 
                    scenario.containsMocks() ? ScenarioResult.USED_MOCKS : ScenarioResult.SUCCEEDED);
        } catch (VerificationException ve) {
            result = new ScenarioResult(description, storyDescription, ve);
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
