/*
 * Created on 18-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.verifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.invoker.ScenarioInvoker;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryVerifier implements Visitor {
    private final List listeners = new ArrayList();
    private final ScenarioInvoker scenarioInvoker;
    private final ScenarioVerifier scenarioVerifier;

    public StoryVerifier(ScenarioInvoker scenarioInvoker, ScenarioVerifier scenarioVerifier) {
        this.scenarioInvoker = scenarioInvoker;
        this.scenarioVerifier = scenarioVerifier;
    }

    public void verify(Story story) {
        story.accept(this);
    }

    public void visit(Visitable visitable) {
        if (visitable instanceof Scenario) {
            ScenarioResult result = verify((Scenario)visitable, 
            		invoke((Scenario)visitable));
            for (Iterator i = listeners.iterator(); i.hasNext();) {
                ((ResultListener)i.next()).gotResult(result);
            }
        }
    }

	private ScenarioResult invoke(Scenario scenario) {
		return scenarioInvoker.invoke(scenario);
	}

	private ScenarioResult verify(Scenario scenario, ScenarioResult result) {
		if (result.succeeded() || result.usedMocks()) {
			result = scenarioVerifier.verify((Scenario)scenario);
		}
		return result;
	}
	
	public void addListener(ResultListener listener) {
        listeners.add(listener);
    }
}
