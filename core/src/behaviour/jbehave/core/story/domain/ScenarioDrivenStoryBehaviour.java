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
import jbehave.core.mock.Mock;
import jbehave.core.result.Result;
import jbehave.core.story.result.ScenarioResult;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDrivenStoryBehaviour extends UsingMiniMock {
    public void shouldRunScenariosInOrder() throws Exception {
        World world = (World)stub(World.class);
        // given...
        Narrative narrative = new Narrative("","","");
        Mock scenarioA = mock(Scenario.class);
        Mock scenarioB = mock(Scenario.class);
        ScenarioDrivenStory story = new ScenarioDrivenStory(narrative);
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
        ScenarioDrivenStory story = new ScenarioDrivenStory(narrative);
        
        Mock listener = mock(BehaviourListener.class);
        Mock scenario = mock(Scenario.class);
        ScenarioResult result = new ScenarioResult("scenario", "story", Result.SUCCEEDED);
        
        scenario.expects("run").with(world);
        scenario.expects("getStoryName").will(returnValue("story"));
        scenario.expects("getDescription").will(returnValue("scenario"));
        scenario.expects("containsMocks").will(returnValue(false));
        listener.expects("gotResult").with(eq(result));
        
        story.addScenario((Scenario)scenario);
        story.addListener((BehaviourListener)listener);
        story.run(world);
        
        verifyMocks();
    }
}
