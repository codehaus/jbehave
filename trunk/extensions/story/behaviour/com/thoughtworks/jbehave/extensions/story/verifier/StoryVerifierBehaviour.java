/*
 * Created on 16-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.verifier;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.listener.ResultListener;
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
        ScenarioVerifier scenarioVerifier = (ScenarioVerifier)stub(ScenarioVerifier.class);
        StoryVerifier storyVerifier = new StoryVerifier(scenarioInvoker, scenarioVerifier);

        // when...
        storyVerifier.verify(storyMock);
        
        // verify...
        Verify.sameInstance(storyVerifier, arg[0]);
    }
    
    public void shouldPassScenarioToScenarioInvoker() throws Exception {
        // given...
        Mock scenarioInvoker = mock(ScenarioInvoker.class);
        Mock scenarioVerifier = mock(ScenarioVerifier.class);

        ScenarioResult result = new ScenarioResult("result", "Container", ScenarioResult.SUCCEEDED);
        
        StoryVerifier storyVerifier = new StoryVerifier((ScenarioInvoker) scenarioInvoker,
        		(ScenarioVerifier)scenarioVerifier);
        Scenario scenario = (Scenario)stub(Scenario.class);

        // expect...
        scenarioInvoker.expects("invoke").with(scenario).with(scenario).will(returnValue(result));;
        scenarioVerifier.expects("verify").with(scenario);
        
        // when...
        storyVerifier.visit(scenario);
        
        // verify...
        verifyMocks();
    }
    
    public void shouldPassScenarioToScenarioVerifierIfInvokerResultSuccessful() throws Exception {
        // given...
        Mock scenarioVerifier = mock(ScenarioVerifier.class);
        Mock scenarioInvoker = mock(ScenarioInvoker.class);
        
        ScenarioResult result = new ScenarioResult("result", "Container", ScenarioResult.SUCCEEDED);
        
        StoryVerifier storyVerifier = new StoryVerifier((ScenarioInvoker) scenarioInvoker,
        		(ScenarioVerifier)scenarioVerifier);
        Scenario scenario = (Scenario)stub(Scenario.class);

        // expect...
        scenarioInvoker.expects("invoke").with(scenario).will(returnValue(result));
        scenarioVerifier.expects("verify");
        
        // when...
        storyVerifier.visit(scenario);
        
        // verify...
        verifyMocks();
    }
    
    public void shouldPassScenarioToScenarioVerifierIfInvokerResultUsedMocks() throws Exception {
        // given...
        Mock scenarioVerifier = mock(ScenarioVerifier.class);
        Mock scenarioInvoker = mock(ScenarioInvoker.class);
        
        ScenarioResult result = new ScenarioResult("result", "Container", ScenarioResult.USED_MOCKS);
        
        StoryVerifier storyVerifier = new StoryVerifier((ScenarioInvoker) scenarioInvoker,
        		(ScenarioVerifier)scenarioVerifier);
        Scenario scenario = (Scenario)stub(Scenario.class);

        // expect...
        scenarioInvoker.expects("invoke").with(scenario).will(returnValue(result));
        scenarioVerifier.expects("verify");
        
        // when...
        storyVerifier.visit(scenario);
        
        // verify...
        verifyMocks();
    }
    
    public void shouldNotPassScenarioToScenarioVerifierIfInvokerResultFailed() throws Exception {
        // given...
        Mock scenarioVerifier = mock(ScenarioVerifier.class);
        Mock scenarioInvoker = mock(ScenarioInvoker.class);
        
        ScenarioResult result = new ScenarioResult("result", "Container", ScenarioResult.FAILED);
        
        StoryVerifier storyVerifier = new StoryVerifier((ScenarioInvoker) scenarioInvoker,
        		(ScenarioVerifier)scenarioVerifier);
        Scenario scenario = (Scenario)stub(Scenario.class);

        // expect...
        scenarioInvoker.expects("invoke").with(scenario).will(returnValue(result));
        
        // when...
        storyVerifier.visit(scenario);
        
        // verify...
        verifyMocks();
    }    
    
    public void shouldPassResultToListeners() throws Exception {
        // given...
        Mock listener1 = mock(ResultListener.class, "listener1");
        Mock listener2 = mock(ResultListener.class, "listener2");
        Scenario scenarioStub = (Scenario) stub(Scenario.class);
        
        ScenarioResult result = new ScenarioResult("result", "Container", ScenarioResult.SUCCEEDED);
        Mock scenarioInvoker = mock(ScenarioInvoker.class);        
        scenarioInvoker.stubs("invoke").will(returnValue(result));
        Mock scenarioVerifier = mock(ScenarioVerifier.class);
        scenarioVerifier.stubs("verify").will(returnValue(result));
        StoryVerifier storyVerifier = new StoryVerifier((ScenarioInvoker) scenarioInvoker,
        		(ScenarioVerifier)scenarioVerifier);
        storyVerifier.addListener((ResultListener)listener1);
        storyVerifier.addListener((ResultListener)listener2);

        // expect...
        listener1.expects("gotResult").with(result);
        listener2.expects("gotResult").with(result).after(listener1, "gotResult");
        
        // when...
        storyVerifier.visit(scenarioStub);
        
        // verify...
        verifyMocks();
    }
}
