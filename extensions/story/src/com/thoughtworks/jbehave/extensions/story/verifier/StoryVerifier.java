/*
 * Created on 18-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.invoker.ScenarioInvoker;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryVerifier implements Visitor {
    private final ScenarioInvoker scenarioInvoker;

    public StoryVerifier(ScenarioInvoker scenarioInvoker) {
        this.scenarioInvoker = scenarioInvoker;
    }

    public void verify(Story story) {
        story.accept(this);
    }

    public void visit(Visitable visitable) {
        if (visitable instanceof Scenario) {
            scenarioInvoker.invoke((Scenario) visitable);
        }
    }
}
