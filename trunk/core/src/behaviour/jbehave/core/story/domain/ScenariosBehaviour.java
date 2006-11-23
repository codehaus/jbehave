package jbehave.core.story.domain;

import jbehave.core.exception.NestedVerificationException;
import jbehave.core.exception.VerificationException;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.result.ScenarioResult;

public class ScenariosBehaviour extends UsingMiniMock {

	public void shouldRunAndTidyUpScenariosInOrder() {
		Mock scenarioA = mock(Scenario.class);
		Mock scenarioB = mock(Scenario.class);
        World world = (World) stub(World.class);
		
		Scenarios scenarios = new Scenarios();
		
		scenarioA.expects("run").with(world);
        scenarioA.expects("tidyUp").with(world);
        scenarioB.expects("run").with(world).after(scenarioA, "tidyUp");
        scenarioB.expects("tidyUp").with(world).after(scenarioB, "run");
		
		scenarios.addScenario((Scenario) scenarioA);
		scenarios.addScenario((Scenario) scenarioB);
		scenarios.run(world, new BehaviourListener[0]);
        
        verifyMocks();
	}
    
    public void shouldInformListenersOfScenarioSuccess() {
        Mock scenario = mock(Scenario.class);
        Mock listener = mock(BehaviourListener.class);
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario)scenario);
        
        scenario.expects("run").with(world);
        scenario.expects("getStoryName").will(returnValue("storyName"));
        scenario.expects("getDescription").will(returnValue("scenarioName"));        
        scenario.expects("containsMocks").will(returnValue(false));
        listener.expects("gotResult").with(eq(new ScenarioResult("scenarioName", "storyName", ScenarioResult.SUCCEEDED)));
        
        scenarios.run(world, new BehaviourListener[] {(BehaviourListener) listener});
        
        verifyMocks();
    }
    
    public void shouldInformListenersOfScenarioUsingMocks() {
        Mock scenario = mock(Scenario.class);
        Mock listener = mock(BehaviourListener.class);
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario)scenario);
        
        scenario.expects("run").with(world);
        scenario.expects("getStoryName").will(returnValue("storyName"));
        scenario.expects("getDescription").will(returnValue("scenarioName"));        
        scenario.expects("containsMocks").will(returnValue(true));
        listener.expects("gotResult").with(eq(new ScenarioResult("scenarioName", "storyName", ScenarioResult.USED_MOCKS)));
        
        scenarios.run(world, new BehaviourListener[] {(BehaviourListener) listener});
        
        verifyMocks();
    }
    
    public void shouldInformListenersOfScenarioFailure() {
        Mock scenario = mock(Scenario.class);
        Mock listener = mock(BehaviourListener.class);
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario)scenario);
        
        NestedVerificationException nve = new NestedVerificationException(new RuntimeException());
        scenario.expects("run").with(world).will(throwException(nve));
        scenario.expects("getStoryName").will(returnValue("storyName"));
        scenario.expects("getDescription").will(returnValue("scenarioName"));
        listener.expects("gotResult").with(eq(new ScenarioResult("scenarioName", "storyName", nve)));
        
        scenarios.run(world, new BehaviourListener[] {(BehaviourListener) listener});
        
        verifyMocks();
    }
    
    public void shouldTidyUpScenariosEvenIfVerificationFails() {
        Mock scenario = mock(Scenario.class);
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario) scenario);
        
        scenario.expects("run").with(world).will(throwException(new VerificationException("Thrown by an outcome when an ensureThat fails")));
        scenario.expects("tidyUp").with(world);

        try {
            scenarios.run(world, new BehaviourListener[0]);
        } catch (VerificationException e) {
            // Expected, but AFTER tidyUp.
        }
        
        verifyMocks();
    }    
}
