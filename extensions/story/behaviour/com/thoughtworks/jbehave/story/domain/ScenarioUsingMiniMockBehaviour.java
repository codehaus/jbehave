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
    public void shouldPassItselfAndComponentsToVisitorInCorrectSequence() throws Exception {
		Verify.pending("Clean this up");
		
        // given...
        Story storyStub = new Story(null, null);
        Givens contextStub = new Givens(new GivenUsingMiniMock[0]);
        EventUsingMiniMock eventStub = new EventUsingMiniMock() {
            public void occurIn(World world) throws Exception {}
        };
        Outcome outcomeStub = (Outcome)stub(Outcome.class);
        
        ScenarioUsingMiniMock scenario = new ScenarioUsingMiniMock("scenario", storyStub, contextStub, eventStub, outcomeStub);
        Mock visitor = mock(Visitor.class);

        // expect...
        visitor.expects("visitScenario").with(scenario);
        visitor.expects("visitContext").with(contextStub).after("visitScenario");
        visitor.expects("visitOutcome").with(outcomeStub).after("visitContext");
        visitor.expects("visitEvent").with(eventStub).after("visitOutcome");
        
        // when...
        scenario.accept((Visitor)visitor);
    }
}
