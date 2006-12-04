package jbehave.core.story.domain;

import jbehave.core.exception.NestedVerificationException;
import jbehave.core.exception.VerificationException;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Expectation;
import jbehave.core.mock.Mock;
import jbehave.core.story.renderer.Renderer;
import jbehave.core.story.result.ScenarioResult;

public class ScenariosBehaviour extends UsingMiniMock {

	public void shouldRunAndTidyUpScenariosInOrder() {
        AScenario scenarioA = new AScenario();
        AScenario scenarioB = new AScenario();
        World world = (World) stub(World.class);
		
		Scenarios scenarios = new Scenarios();
		
		scenarioA.expects("run").with(world);
        scenarioA.expects("tidyUp").with(world);
        scenarioB.expects("run").with(world).after(scenarioA.mockDelegate, "tidyUp");
        scenarioB.expects("tidyUp").with(world).after(scenarioB.mockDelegate, "run");
		
		scenarios.addScenario((Scenario) scenarioA);
		scenarios.addScenario((Scenario) scenarioB);
		scenarios.run(world, AStory.class, new BehaviourListener[0]);
        
        verifyMocks();
	}
    
    public void shouldInformListenersOfScenarioSuccess() {
        AScenario scenario = new AScenario();
        Mock listener = mock(BehaviourListener.class);
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario)scenario);
        
        scenario.expects("run").with(world);
        scenario.expects("containsMocks").will(returnValue(false));
        listener.expects("gotResult").with(eq(new ScenarioResult("a scenario", "a story", ScenarioResult.SUCCEEDED)));
        
        scenarios.run(world, AStory.class, new BehaviourListener[] {(BehaviourListener) listener});
        
        verifyMocks();
    }
    
    public void shouldInformListenersOfScenarioUsingMocks() {
        AScenario scenario = new AScenario();
        Mock listener = mock(BehaviourListener.class);
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario)scenario);
        
        scenario.expects("run").with(world);
        scenario.expects("containsMocks").will(returnValue(true));
        listener.expects("gotResult").with(eq(new ScenarioResult("a scenario", "a story", ScenarioResult.USED_MOCKS)));
        
        scenarios.run(world, AStory.class, new BehaviourListener[] {(BehaviourListener) listener});
        
        verifyMocks();
    }
    
    public void shouldInformListenersOfScenarioFailure() {
        AScenario scenario = new AScenario();
        Mock listener = mock(BehaviourListener.class);
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario)scenario);
        
        NestedVerificationException nve = new NestedVerificationException(new RuntimeException());
        scenario.expects("run").with(world).will(throwException(nve));
        listener.expects("gotResult").with(eq(new ScenarioResult("a scenario", "a story", nve)));
        
        scenarios.run(world, AStory.class, new BehaviourListener[] {(BehaviourListener) listener});
        
        verifyMocks();
    }
    
    public void shouldTidyUpScenariosEvenIfVerificationFails() {
        AScenario scenario = new AScenario();
        World world = (World) stub(World.class);
        
        Scenarios scenarios = new Scenarios();
        scenarios.addScenario((Scenario) scenario);
        
        scenario.expects("run").with(world).will(throwException(new VerificationException("Thrown by an outcome when an ensureThat fails")));
        scenario.expects("tidyUp").with(world);

        try {
            scenarios.run(world, AStory.class, new BehaviourListener[0]);
        } catch (VerificationException e) {
            // Expected, but AFTER tidyUp.
        }
        
        verifyMocks();
    }
    
    private class AStory {
        // just used to provide the story name
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

        public void cleanUp(World world) {
            delegate.cleanUp(world);
        }

        public void narrateTo(Renderer renderer) {
            delegate.narrateTo(renderer);
        }
        
        public void assemble() {
            delegate.assemble();
        }
    }
}
