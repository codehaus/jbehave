/*
 * Created on 25-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.base;

import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;


public class MockableScenario extends Scenario {

    public MockableScenario() {
        super("", Story.NULL, Event.NULL, Outcome.NULL);
    }
    
}