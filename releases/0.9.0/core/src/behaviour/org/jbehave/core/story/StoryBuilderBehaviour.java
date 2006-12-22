package org.jbehave.core.story;

import org.jbehave.core.mock.UsingMatchers;
import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.story.domain.Story;

public class StoryBuilderBehaviour extends UsingMatchers {

    public void shouldBuildStoryFromStoryDetails() throws Exception {
        // given
        StoryDetails storyDetails = new StoryDetails("Joe drinks vodka", "", "", "");
        ScenarioDetails expectedScenario1 = new ScenarioDetails();
        expectedScenario1.name = "Happy path";
        expectedScenario1.context.givens.add("Joe is thirsty");
        expectedScenario1.event.name = "Joe asks for a Smirnov";
        expectedScenario1.outcome.outcomes.add("bartender serves Joe");
        expectedScenario1.outcome.outcomes.add("Joe is happy");
        storyDetails.addScenario(expectedScenario1);
        ScenarioDetails expectedScenario2 = new ScenarioDetails();
        expectedScenario2.name = "Unhappy path";
        expectedScenario2.context.givens.add("Joe is thirsty");
        expectedScenario2.event.name = "Joe asks for an Absolut";
        expectedScenario2.outcome.outcomes.add("bartender denies Joe");
        expectedScenario2.outcome.outcomes.add("Joe is unhappy");
        storyDetails.addScenario(expectedScenario2);

        // when
        Story story = new StoryBuilder(storyDetails, "jbehave.core.story.stories").story();
        
        // then
        ensureThat(story, isNotNull());
    }
    
}
