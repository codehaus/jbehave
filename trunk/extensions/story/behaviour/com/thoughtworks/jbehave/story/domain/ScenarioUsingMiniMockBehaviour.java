/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Context;
import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.domain.EventUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.ExpectationUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.GivenUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Outcomes;
import com.thoughtworks.jbehave.story.domain.ScenarioUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Story;
import com.thoughtworks.jbehave.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioUsingMiniMockBehaviour extends UsingMiniMock {
    public void shouldPassItselfAndComponentsToVisitorInCorrectSequence() throws Exception {
        // given...
        Story storyStub = new Story(null, null);
        Context contextStub = new Context(new GivenUsingMiniMock[0]);
        EventUsingMiniMock eventStub = new EventUsingMiniMock() {
            public void occurIn(World world) throws Exception {}
        };
        Outcomes outcomesStub = new Outcomes(new ExpectationUsingMiniMock[0]);
        
        ScenarioUsingMiniMock scenario = new ScenarioUsingMiniMock("scenario", storyStub, contextStub, eventStub, outcomesStub);
        Mock visitor = mock(Visitor.class);

        // expect...
        visitor.expects("visitScenario").with(scenario);
        visitor.expects("visitContext").with(contextStub).after("visitScenario");
        visitor.expects("visitOutcome").with(outcomesStub).after("visitContext");
        visitor.expects("visitEvent").with(eventStub).after("visitOutcome");
        
        // when...
        scenario.accept((Visitor)visitor);
    }
}
