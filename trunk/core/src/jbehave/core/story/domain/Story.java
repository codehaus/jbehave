package jbehave.core.story.domain;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.story.invoker.ScenarioInvoker;
import jbehave.core.story.listener.PlainTextScenarioListener;
import jbehave.core.story.renderer.PlainTextRenderer;
import jbehave.core.story.verifier.ScenarioVerifier;
import jbehave.core.story.visitor.Visitor;

public interface Story {

	String title();

	Narrative narrative();
    
    void run(Visitor scenarioInvoker, Visitor scenarioVerifier);

    void addListener(BehaviourListener listener);

    void narrate(Visitor renderer);

}