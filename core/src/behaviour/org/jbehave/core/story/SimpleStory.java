package org.jbehave.core.story;

import org.jbehave.core.minimock.story.domain.EventUsingMiniMock;
import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import org.jbehave.core.minimock.story.domain.OutcomeUsingMiniMock;
import org.jbehave.core.story.domain.MultiStepScenario;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.ScenarioDrivenStory;
import org.jbehave.core.story.domain.World;

public class SimpleStory extends ScenarioDrivenStory {

    public static String ROLE = "behaviour analyst";

    public static String FEATURE = "to see the behaviour of PlainTextRenderer";

    public static String BENEFIT = "I can be sure that it works";

    public static String FIRST_SCENARIO_NAME = "PlainTextRenderer works";

    public static String SECOND_SCENARIO_NAME = "PlainTextRenderer still works";

    public SimpleStory() {
        super(new Narrative(ROLE, FEATURE, BENEFIT));
    }
    
    public void specify() {
        addScenario(new PlainTextRendererWorks());
        addScenario(new PlainTextRendererStillWorks());
    }
    
    public static class PlainTextRendererWorks extends MultiStepScenario {
        public void specify() {
            given(new EverythingCompiles());
            when(new ICrossMyFingers());
            then(new PlainTextRendererShouldWork());
        }
    }
    
    public static class PlainTextRendererStillWorks extends MultiStepScenario {
        public void specify() {
             given(new PlainTextRendererWorks());
             given(new FirstScenarioRanWithoutFallingOver());
             when(new IDoNothing());
             then(new PlainTextRendererShouldStillWork());
             then(new BehaviourClassShouldNotFail());
        }
    }

    public static class EverythingCompiles extends GivenUsingMiniMock {
        public void setUp(World world) {
        }
    }

    public static class FirstScenarioRanWithoutFallingOver extends GivenUsingMiniMock {
        public void setUp(World world) {
        }
    }

    public static class ICrossMyFingers extends EventUsingMiniMock {
        public void occurIn(World world) {
        }
    }

    public static class IDoNothing extends EventUsingMiniMock {
        public void occurIn(World world) {
        }
    }

    public static class PlainTextRendererShouldWork extends OutcomeUsingMiniMock {
        public void verify(World world) {
        }
    }

    public static class PlainTextRendererShouldStillWork extends OutcomeUsingMiniMock {
        public void verify(World world) {
        }
    }

    public static class BehaviourClassShouldNotFail extends OutcomeUsingMiniMock {
        public void verify(World world) {
        }
    }
}