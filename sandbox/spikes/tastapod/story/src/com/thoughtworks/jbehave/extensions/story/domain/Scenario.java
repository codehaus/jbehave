/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.story.listener.ScenarioListener;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Scenario extends Visitable {
    String getDescription();
    Story getStory();
    Context getContext();
    Event getEvent();
    Outcome getOutcome();
    void setListener(ScenarioListener listener);
}