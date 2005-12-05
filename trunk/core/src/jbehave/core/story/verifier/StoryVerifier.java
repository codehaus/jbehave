/*
 * Created on 18-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.verifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.core.listener.ResultListener;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.Story;
import jbehave.core.story.invoker.ScenarioInvoker;
import jbehave.core.story.result.ScenarioResult;
import jbehave.core.story.visitor.VisitorSupport;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryVerifier extends VisitorSupport {
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

    public void visitScenario(Scenario scenario) {
        ScenarioResult result = verify(scenario, invoke(scenario));
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((ResultListener)i.next()).gotResult(result);
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
