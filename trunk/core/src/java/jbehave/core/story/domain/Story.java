package jbehave.core.story.domain;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.renderer.Renderable;

public interface Story extends Renderable {

	String title();

	Narrative narrative();
    
    void run(World world);

    void addListener(BehaviourListener listener);

}