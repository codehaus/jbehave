/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.visitor.Visitable;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Scenario extends Visitable {
    Story getStory();

    String getDescription();

    Context getContext();

    Event getEvent();

    Outcome getOutcome();
}