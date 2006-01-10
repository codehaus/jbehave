/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import java.util.Iterator;
import java.util.List;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.invoker.ScenarioInvoker;
import jbehave.core.story.renderer.PlainTextRenderer;
import jbehave.core.story.verifier.ScenarioVerifier;
import jbehave.core.story.visitor.Visitable;
import jbehave.core.story.visitor.Visitor;
import jbehave.core.util.ConvertCase;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDrivenStory implements Story {
    private final Narrative narrative;
    private final AcceptanceCriteria acceptanceCriteria;

    public ScenarioDrivenStory(Narrative narrative, AcceptanceCriteria criteria) {
        this.narrative = narrative;
        this.acceptanceCriteria = criteria;
    }

    public void addScenario(ScenarioUsingMiniMock scenario) {
        acceptanceCriteria.addScenario(scenario);
    }
    
    /* (non-Javadoc)
	 * @see com.thoughtworks.jbehave.story.domain.Story#title()
	 */
    /* (non-Javadoc)
	 * @see com.thoughtworks.jbehave.story.domain.Story#title()
	 */
    public String title() {
        return new ConvertCase(getClass()).toSeparateWords();
    }
    
    public Scenario scenario(String name) {
        for (Iterator i = acceptanceCriteria.scenarios().iterator(); i.hasNext();) {
            Scenario scenario = (Scenario) i.next();
            if (scenario.getDescription().equals(name)) {
                return scenario;
            }
        }
        throw new RuntimeException(name);
    }
    
    public List scenarios() {
        return acceptanceCriteria.scenarios();
    }
    
    /* (non-Javadoc)
	 * @see com.thoughtworks.jbehave.story.domain.Story#narrative()
	 */
    public Narrative narrative() {
        return narrative;
    }

    /* (non-Javadoc)
	 * @see com.thoughtworks.jbehave.story.domain.Story#accept(com.thoughtworks.jbehave.story.visitor.Visitor)
	 */
    /* (non-Javadoc)
	 * @see com.thoughtworks.jbehave.story.domain.Story#accept(com.thoughtworks.jbehave.story.visitor.Visitor)
	 */
    public void run(Visitor invoker, Visitor verifier) {
        narrative.accept(invoker);
        narrative.accept(verifier);
        acceptanceCriteria.accept(invoker);
        acceptanceCriteria.accept(verifier);
    }
    
    public String toString() {
        return "Story: narrative=" + narrative + ", acceptanceCriteria=" + acceptanceCriteria;
    }

    public void addListener(BehaviourListener listener) {
        // TODO Auto-generated method stub        
    }

    public void narrate(Visitor renderer) {
        renderer.visitStory(this);
        narrative.accept(renderer);
        acceptanceCriteria.accept(renderer);
    }
}
