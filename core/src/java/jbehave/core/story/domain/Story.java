package jbehave.core.story.domain;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.visitor.Visitor;

public interface Story {

	String title();

	Narrative narrative();
    
    void run(World world);

    void addListener(BehaviourListener listener);

    void accept(Visitor visitor);

}