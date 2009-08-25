package org.jbehave.core.story.domain;

import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.story.renderer.Renderable;

/**
 * <p>Delivery of a Story creates a benefit for a particular customer.</p>
 * 
 * <p>A Story is the top level of JBehave's Story framework;
 * the scenarios are the equivalent of acceptance tests. A Story 
 * can be executed using the {@link org.jbehave.core.story.StoryRunner}, and documented 
 * using a {@link org.jbehave.core.story.StoryPrinter}.</p>
 * 
 * <p>We recommend that you extend {@link ScenarioDrivenStory} to
 * implement a Story. But you don't have to.</p>
 * 
 * @see ScenarioDrivenStory
 * @see org.jbehave.core.story.StoryRunner
 * @see org.jbehave.core.story.StoryPrinter
 */
public interface Story extends Renderable {

	Narrative narrative();
	
	void specify();
    
    void run();

    void addListener(BehaviourListener listener);

}