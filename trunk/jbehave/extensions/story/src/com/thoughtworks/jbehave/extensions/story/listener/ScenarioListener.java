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
public interface ScenarioListener {
    void scenarioUnimplemented(Scenario scenario, UnimplementedException cause);
    
    void scenarioFailed(Scenario scenario, Exception cause);

    void scenarioSucceeded(Scenario scenario);

    void componentUsesMocks(Object component);
}
