/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.listener;

import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.UnimplementedException;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class NULLScenarioListener implements ScenarioListener {

    public void scenarioSucceeded(Scenario scenario) {
    }

    public void scenarioUnimplemented(Scenario scenario, UnimplementedException cause) {
    }

    public void scenarioFailed(Scenario scenario, Exception cause) {
    }

    public void componentUsesMocks(Object component) {
    }
}
