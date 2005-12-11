/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.ScenarioUsingMiniMock;
import jbehave.core.story.visitor.Visitor;


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
        
        // then...
        verifyMocks();
    }
}
