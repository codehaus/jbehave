/*
 * Created on 16-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.story.invoker;

import jbehave.story.domain.Scenario;
import jbehave.story.result.ScenarioResult;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface ScenarioInvoker {
    ScenarioResult invoke(Scenario scenario);
}
