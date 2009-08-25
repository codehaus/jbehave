package org.jbehave.core.story;

import org.jbehave.core.mock.UsingMatchers;
import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.story.domain.Story;

public class StoryBuilderBehaviour extends UsingMatchers {

    public void shouldBuildStoryFromStoryDetails() throws Exception {
        // given
        StoryDetails storyDetails = new StoryDetails("Joe drinks vodka", "org.jbehave.core.story.stories", "", "", "");
        ScenarioDetails scenario1 = new ScenarioDetails();
        scenario1.name = "Happy path";
        scenario1.context.givens.add("Joe is thirsty");
        scenario1.event.name = "Joe asks for a Smirnov";
        scenario1.outcome.outcomes.add("bartender serves Joe");
        scenario1.outcome.outcomes.add("Joe is happy");
        storyDetails.addScenario(scenario1);
        ScenarioDetails scenario2 = new ScenarioDetails();
        scenario2.name = "Unhappy path";
        scenario2.context.givens.add("Joe is thirsty");
        scenario2.event.name = "Joe asks for an Absolut";
        scenario2.outcome.outcomes.add("bartender denies Joe");
        scenario2.outcome.outcomes.add("Joe is unhappy");
        storyDetails.addScenario(scenario2);

        // when
        Story story = new StoryBuilder(storyDetails).story();
        
        // then
        ensureThat(story, isNotNull());
    }
    
}
