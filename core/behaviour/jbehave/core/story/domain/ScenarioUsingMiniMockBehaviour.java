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


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioUsingMiniMockBehaviour extends UsingMiniMock {
    public void shouldSetupGivenSetExpectationsInOutcomeMakeEventOccurAndVerifyOutcomeWhenRun() throws Exception {
		
        // given...
        Mock given = mock(Given.class);
		Mock event = mock(Event.class);
        Mock outcome = mock(Outcome.class);
        World world = (World) stub(World.class);
        
        ScenarioUsingMiniMock scenario =
			new ScenarioUsingMiniMock("scenario", "story", (Given)given, (Event)event, (Outcome) outcome);

        // expect...
        given.expects("setUp").with(world);
        outcome.expects("setExpectationIn").with(world).after(given, "setUp");
        event.expects("occurIn").with(world).after(outcome, "setExpectationIn");
        outcome.expects("verify").with(world).after(event, "occurIn");
        
        // when...
        scenario.run(world);
        
        // then...
        verifyMocks();
    }
}
