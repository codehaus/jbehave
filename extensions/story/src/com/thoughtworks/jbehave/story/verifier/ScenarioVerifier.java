/*
 * Created on 08-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.verifier;

import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.result.ScenarioResult;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface ScenarioVerifier {
    ScenarioResult verify(Scenario scenario);
    void addListener(ResultListener listener);
}
