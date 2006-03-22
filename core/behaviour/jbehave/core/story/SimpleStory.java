package jbehave.core.story;

import jbehave.core.story.domain.EventUsingMiniMock;
import jbehave.core.story.domain.GivenScenario;
import jbehave.core.story.domain.GivenUsingMiniMock;
import jbehave.core.story.domain.Givens;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.OutcomeUsingMiniMock;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.ScenarioDrivenStory;
import jbehave.core.story.domain.ScenarioUsingMiniMock;
import jbehave.core.story.domain.World;

public class SimpleStory extends ScenarioDrivenStory {

    public static String ROLE = "behaviour analyst";

    public static String FEATURE = "to see the behaviour of PlainTextRenderer";

    public static String BENEFIT = "I can be sure that it works";

    public static String FIRST_SCENARIO_NAME = "PlainTextRenderer works";

    public static String SECOND_SCENARIO_NAME = "PlainTextRenderer still works";

    public SimpleStory() {
        super(new Narrative(ROLE, FEATURE, BENEFIT));

        ScenarioUsingMiniMock firstScenario = new ScenarioUsingMiniMock(
                FIRST_SCENARIO_NAME, getClass().getName(),
                new EverythingCompiles(),
                new ICrossMyFingers(),
                new PlainTextRendererShouldWork());
        addScenario(firstScenario);

        ScenarioUsingMiniMock secondScenario = new ScenarioUsingMiniMock(
                SECOND_SCENARIO_NAME, getClass().getName(),
                new Givens(
                        new GivenScenario(firstScenario),
                        new FirstScenarioRanWithoutFallingOver()
                ),
                new IDoNothing(),
                new Outcomes(
                        new PlainTextRendererShouldStillWork(),
                        new BehaviourClassShouldNotFail())
                );
        addScenario(secondScenario);
    }

    public static class EverythingCompiles extends GivenUsingMiniMock {
        public void setUp(World world) {
        }
    }

    public static class FirstScenarioRanWithoutFallingOver extends
            GivenUsingMiniMock {
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

    public static class PlainTextRendererShouldWork extends
            OutcomeUsingMiniMock {
        public void setExpectationIn(World world) {
        }

        public void verify(World world) {
        }
    }

    public static class PlainTextRendererShouldStillWork extends
            OutcomeUsingMiniMock {
        public void setExpectationIn(World world) {
        }

        public void verify(World world) {
        }
    }

    public static class BehaviourClassShouldNotFail extends
            OutcomeUsingMiniMock {
        public void setExpectationIn(World world) {
        }

        public void verify(World world) {
        }
    }
}