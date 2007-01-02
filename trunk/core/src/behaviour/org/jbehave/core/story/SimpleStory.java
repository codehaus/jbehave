package org.jbehave.core.story;

import org.jbehave.core.minimock.story.domain.EventUsingMiniMock;
import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import org.jbehave.core.minimock.story.domain.OutcomeUsingMiniMock;
import org.jbehave.core.story.domain.MultiStepScenario;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.ScenarioDrivenStory;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.util.CamelCaseConverter;

public class SimpleStory extends ScenarioDrivenStory {


    private static final String NL = System.getProperty("line.separator");
    
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
    
    public static String expectedDescription() {
        StringBuffer expectedResult = new StringBuffer();
        expectedResult.append("Story: ").append(textOf(new SimpleStory())).append(NL + NL);
        expectedResult.append("As a ").append(SimpleStory.ROLE).append(NL);
        expectedResult.append("I want ").append(SimpleStory.FEATURE).append(NL);
        expectedResult.append("So that ").append(SimpleStory.BENEFIT).append(NL + NL);
        expectedResult.append("Scenario: ").append(textOf(new SimpleStory.PlainTextRendererWorks())).append(NL + NL);
        expectedResult.append("Given ").append(textOf(new SimpleStory.EverythingCompiles())).append(NL);
        expectedResult.append("When ").append(textOf(new SimpleStory.ICrossMyFingers())).append(NL);
        expectedResult.append("Then ").append(textOf(new SimpleStory.PlainTextRendererShouldWork())).append(NL + NL);
        
        expectedResult.append("Scenario: ").append(textOf(new SimpleStory.PlainTextRendererStillWorks())).append(NL + NL);
        expectedResult.append("Given ").append(textOf(new SimpleStory.PlainTextRendererWorks())).append(NL);;
        expectedResult.append("and ").append(textOf(new SimpleStory.FirstScenarioRanWithoutFallingOver())).append(NL);
        expectedResult.append("When ").append(textOf(new SimpleStory.IDoNothing())).append(NL);
        expectedResult.append("Then ").append(textOf(new SimpleStory.PlainTextRendererShouldStillWork())).append(NL);
        expectedResult.append("and ").append(textOf(new SimpleStory.BehaviourClassShouldNotFail())).append(NL);
        
        return expectedResult.toString();
    }
    
    
    private static String textOf(Object obj) {
        return new CamelCaseConverter(obj).toPhrase();
    }
}