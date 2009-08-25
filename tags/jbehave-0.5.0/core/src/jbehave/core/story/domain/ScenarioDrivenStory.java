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

import jbehave.core.story.visitor.Visitable;
import jbehave.core.story.visitor.Visitor;
import jbehave.core.util.ConvertCase;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDrivenStory implements Visitable, Story {
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
    public void accept(Visitor visitor) {
        visitor.visitStory(this);
        narrative.accept(visitor);
        acceptanceCriteria.accept(visitor);
    }
    
    public String toString() {
        return "Story: narrative=" + narrative + ", acceptanceCriteria=" + acceptanceCriteria;
    }
}
