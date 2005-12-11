/*
 * Created on 24-Dec-2004
 *
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.visitor;

import java.util.ArrayList;
import java.util.List;

import jbehave.core.UsingMocks;
import jbehave.core.exception.NestedVerificationException;
import jbehave.core.listener.ResultListener;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.World;
import jbehave.core.story.result.ScenarioResult;


/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public abstract class AbstractScenarioVisitor extends VisitorSupport {
	protected final String storyName;
	protected final World world;
	protected boolean usedMocks = false;
	protected final List listeners = new ArrayList();
	
	public AbstractScenarioVisitor(String storyName, World world) {
		this.storyName = storyName;
        this.world = world;
	}

	protected ScenarioResult giveSelfToScenario(Scenario scenario) {
		Throwable cause = null;
		try {
			scenario.accept(this);
		}

		catch (NestedVerificationException e) {
			cause = e.getCause();
		}

        final ScenarioResult result;
        if (cause == null && usedMocks) {
            result = new ScenarioResult(scenario.getDescription(), storyName, ScenarioResult.USED_MOCKS);
        }
        else {
            result = new ScenarioResult(scenario.getDescription(), storyName, cause);
        }
        return result;
	}

    protected void checkForMocks(UsingMocks component) {
        if (component.containsMocks()) {
            usedMocks = true;
        }
    }

    public void addListener(ResultListener listener) {
		throw new UnsupportedOperationException("currently unused");
    }
}
