/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioUsingMiniMockBehaviour extends UsingMiniMock {
    public void shouldPassItselfAndComponentsToVisitorInCorrectSequence() throws Exception {
        // given...
        Story storyStub = new Story(null, null);
        Context contextStub = new Context(new GivenUsingMiniMock[0]);
        EventUsingMiniMock eventStub = new EventUsingMiniMock() {
            public void occurIn(Environment environment) throws Exception {}
        };
        Outcome outcomeStub = new Outcome(new ExpectationUsingMiniMock[0]);
        
        ScenarioUsingMiniMock scenario = new ScenarioUsingMiniMock("scenario", storyStub, contextStub, eventStub, outcomeStub);
        Mock visitor = mock(Visitor.class);

        // expect...
        visitor.expects("visit").with(scenario).id("1");
        visitor.expects("visit").with(contextStub).after("1").id("2");
        visitor.expects("visit").with(outcomeStub).after("2").id("3");
        visitor.expects("visit").with(eventStub).after("3").id("4");
        
        // when...
        scenario.accept((Visitor)visitor);
        
        // then...
        verifyMocks();
    }
}
