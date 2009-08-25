/*
 * Created on 16-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.verifier;

import jbehave.core.listener.ResultListener;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.Story;
import jbehave.core.story.invoker.ScenarioInvoker;
import jbehave.core.story.result.ScenarioResult;
import jbehave.core.story.verifier.ScenarioVerifier;
import jbehave.core.story.verifier.StoryVerifier;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryVerifierBehaviour extends UsingMiniMock {
    public void shouldPassItselfIntoStoryAsVisitor() throws Exception {
        // given...
        Mock storyMock = mock(Story.class);
        
        ScenarioInvoker scenarioInvoker = (ScenarioInvoker) stub(ScenarioInvoker.class);
        ScenarioVerifier scenarioVerifier = (ScenarioVerifier)stub(ScenarioVerifier.class);
        StoryVerifier storyVerifier = new StoryVerifier(scenarioInvoker, scenarioVerifier);

        storyMock.expects("accept").once().with(eq(storyVerifier));        
        
        // when...
        storyVerifier.verify((Story) storyMock);
        
        // verify...
//        ensureThat(arg[0], sameInstanceAs(storyVerifier));
        verify();
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
        scenarioInvoker.expects("invoke").with(scenario).with(scenario).will(returnValue(result));
        scenarioVerifier.expects("verify").with(scenario);
        
        // when...
        storyVerifier.visitScenario(scenario);
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
        storyVerifier.visitScenario(scenario);
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
        storyVerifier.visitScenario(scenario);
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
        storyVerifier.visitScenario(scenario);
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
        storyVerifier.visitScenario(scenarioStub);
    }
}
