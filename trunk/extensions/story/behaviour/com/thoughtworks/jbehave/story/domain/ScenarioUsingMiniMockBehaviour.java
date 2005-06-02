/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioUsingMiniMockBehaviour extends UsingMiniMock {
    public void shouldTellComponentsToAcceptVisitorInCorrectSequence() throws Exception {
		
        // given...
        Mock given = mock(Given.class);
		Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        Mock visitor = mock(Visitor.class);
        
        ScenarioUsingMiniMock scenario =
			new ScenarioUsingMiniMock("scenario", "story", (Given)given, (Event)event, (Outcome) outcome);

        // expect...
        visitor.expects("visitScenario").with(scenario);
        given.expects("accept").with(visitor).after(visitor, "visitScenario");
        outcome.expects("accept").with(visitor).after(given, "accept");
        event.expects("accept").with(visitor).after(outcome, "accept");
        
        // when...
        scenario.accept((Visitor)visitor);
    }
}
