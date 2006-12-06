/*
 * Created on 21-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.domain;

import org.jbehave.core.exception.NestedVerificationException;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Expectation;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.result.Result;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.Scenario;
import org.jbehave.core.story.domain.ScenarioDrivenStory;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.story.renderer.Renderer;
import org.jbehave.core.story.result.ScenarioResult;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioDrivenStoryBehaviour extends UsingMiniMock {
    public void shouldRunScenariosInOrder() throws Exception {
        final World world = (World)stub(World.class);
        // given...
        Narrative narrative = new Narrative("","","");
        AScenario scenarioA = new AScenario();
        AScenario scenarioB = new AScenario();
        AStory story = new AStory(narrative) {
            protected World createWorld() {
                return world;
            }
        };
        story.addScenario((Scenario) scenarioA);
        story.addScenario((Scenario) scenarioB);

        scenarioA.expects("run").with(world);
        scenarioB.expects("run").with(world);
        
        // when...
        story.run();
        
        // then...
        verifyMocks();
    }
    
    public void shouldInformListenersOfScenarioResult() {
        final World world = (World)stub(World.class);
        Narrative narrative = new Narrative("","","");
        AStory story = new AStory(narrative) {
            protected World createWorld() {
                return world;
            }
        };
        
        Mock listener = mock(BehaviourListener.class);
        AScenario scenario = new AScenario();
        ScenarioResult result = new ScenarioResult("a scenario", "a story", Result.SUCCEEDED);
        
        scenario.expects("run").with(world);
        scenario.expects("containsMocks").will(returnValue(false));
        listener.expects("gotResult").with(eq(result));
        
        story.addScenario((Scenario)scenario);
        story.addListener((BehaviourListener)listener);
        story.run();
        
        verifyMocks();
    }
    
    public void shouldInformListenersOfScenarioUsingMocks() {
        final World world = (World) stub(World.class);
        Narrative narrative = new Narrative("","","");
        AStory story = new AStory(narrative) {
            protected World createWorld() {
                return world;
            }
        };
        
        Mock listener = mock(BehaviourListener.class);
        AScenario scenario = new AScenario();
        ScenarioResult result = new ScenarioResult("a scenario", "a story", ScenarioResult.USED_MOCKS);

        scenario.expects("run").with(world);
        scenario.expects("containsMocks").will(returnValue(true));
        listener.expects("gotResult").with(eq(result));
        
        story.addScenario((Scenario)scenario);
        story.addListener((BehaviourListener)listener);
        
        story.run();
        
        verifyMocks();
    }

    public void shouldInformListenersOfScenarioFailure() {
        final World world = (World) stub(World.class);
        Narrative narrative = new Narrative("","","");
        AStory story = new AStory(narrative) {
            protected World createWorld() {
                return world;
            }
        };
        
        Mock listener = mock(BehaviourListener.class);
        AScenario scenario = new AScenario();
        NestedVerificationException nve = new NestedVerificationException(new RuntimeException());
        ScenarioResult result = new ScenarioResult("a scenario", "a story", nve);
        scenario.expects("run").with(world).will(throwException(nve));
        listener.expects("gotResult").with(eq(result));
        
        story.addScenario((Scenario)scenario);
        story.addListener((BehaviourListener)listener);
        
        story.run();
        
        verifyMocks();
    }

    public void shouldCleanUpScenariosEvenIfVerificationFails() {
        final World world = (World) stub(World.class);
        Narrative narrative = new Narrative("","","");
        AStory story = new AStory(narrative) {
            protected World createWorld() {
                return world;
            }
        };
        
        Mock listener = mock(BehaviourListener.class);
        AScenario scenario = new AScenario();
        VerificationException ve = new VerificationException("Thrown by an outcome when an ensureThat fails");
        ScenarioResult result = new ScenarioResult("a scenario", "a story", ve);
        scenario.expects("run").with(world).will(throwException(ve));
        scenario.expects("cleanUp").with(world);
        
        story.addScenario((Scenario)scenario);
        
        try {
            story.run();
        } catch (VerificationException e) {
            // Expected, but AFTER cleanUp.
        }
        
        verifyMocks();
    }
        
    private class AStory extends ScenarioDrivenStory {
        // just used to provide the story name

        public AStory(Narrative narrative) {
            super(narrative);
        }
        public void specify() {
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

        public void cleanUp(World world) {
            delegate.cleanUp(world);
        }

        public void narrateTo(Renderer renderer) {
            delegate.narrateTo(renderer);
        }
        
        public void specify() {
            delegate.specify();
        }
        
    }    
}
