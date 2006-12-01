/*
 * Created on 21-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Expectation;
import jbehave.core.mock.Mock;
import jbehave.core.result.Result;
import jbehave.core.story.renderer.Renderer;
import jbehave.core.story.result.ScenarioResult;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDrivenStoryBehaviour extends UsingMiniMock {
    public void shouldRunScenariosInOrder() throws Exception {
        World world = (World)stub(World.class);
        // given...
        Narrative narrative = new Narrative("","","");
        AScenario scenarioA = new AScenario();
        AScenario scenarioB = new AScenario();
        AStory story = new AStory(narrative);
        story.addScenario((Scenario) scenarioA);
        story.addScenario((Scenario) scenarioB);

        scenarioA.expects("run").with(world);
        scenarioB.expects("run").with(world);
        
        // when...
        story.run(world);
        
        // then...
        verifyMocks();
    }
    
    public void shouldInformListenersOfScenarioResult() {
        World world = (World)stub(World.class);
        Narrative narrative = new Narrative("","","");
        AStory story = new AStory(narrative);
        
        Mock listener = mock(BehaviourListener.class);
        AScenario scenario = new AScenario();
        ScenarioResult result = new ScenarioResult("a scenario", "a story", Result.SUCCEEDED);
        
        scenario.expects("run").with(world);
        scenario.expects("containsMocks").will(returnValue(false));
        listener.expects("gotResult").with(eq(result));
        
        story.addScenario((Scenario)scenario);
        story.addListener((BehaviourListener)listener);
        story.run(world);
        
        verifyMocks();
    }
    
    private class AStory extends ScenarioDrivenStory {
        // just used to provide the story name

        public AStory(Narrative narrative) {
            super(narrative);
        }
    }
    
    private class AScenario implements Scenario {
        // also required to get the right name
        
        Mock mockDelegate = mock(Scenario.class);
        Scenario delegate = (Scenario) mockDelegate;
        
        public Expectation expects(String method) {
            return mockDelegate.expects(method);
        }
        
        public boolean containsMocks() {
            return delegate.containsMocks();
        }

        public void run(World world) {
            delegate.run(world);
        }

        public void tidyUp(World world) {
            delegate.tidyUp(world);
        }

        public void narrateTo(Renderer renderer) {
            delegate.narrateTo(renderer);
        }
        
    }    
}