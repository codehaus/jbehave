/*
 * Created on 16-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.invoker.ScenarioInvoker;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryVerifierBehaviour extends UsingMiniMock {
    public void shouldPassItselfIntoStoryAsVisitor() throws Exception {
        // given...
        final Object[] arg = new Object[1];
        
        Story storyMock = new Story(null, null) {
            public void accept(Visitor visitor) {
                arg[0] = visitor;
            }
        };
        
        ScenarioInvoker scenarioInvoker = (ScenarioInvoker) stub(ScenarioInvoker.class);
        StoryVerifier storyVerifier = new StoryVerifier(scenarioInvoker);

        // when...
        storyVerifier.verify(storyMock);
        
        // verify...
        Verify.sameInstance(storyVerifier, arg[0]);
    }
    
    public void shouldPassScenarioToScenarioInvoker() throws Exception {
        // given...
        Mock scenarioInvoker = mock(ScenarioInvoker.class);
        StoryVerifier storyVerifier = new StoryVerifier((ScenarioInvoker) scenarioInvoker);
        Scenario scenario = (Scenario)stub(Scenario.class);
        ScenarioResult result = new ScenarioResult("", ScenarioResult.SUCCEEDED);

        // expect...
        scenarioInvoker.expects("invoke").with(scenario).will(returnValue(result));
        
        // when...
//        storyVerifier.visit(scenario);
        
        // verify...
    }
}
