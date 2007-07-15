package org.jbehave.core.story.codegen.parser;

import java.io.StringReader;

import org.jbehave.core.mock.UsingMatchers;
import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;



public class TextStoryParserBehaviour extends UsingMatchers {

	private StoryParser storyParser = new TextStoryParser();

    public void shouldBuildStoryDetailsWithTitle() throws Exception {
		// given
		String text = "Title: Joe drinks vodka\n";
		StoryDetails story = new StoryDetails("Joe drinks vodka", "", "", "", "");
		// when
		StoryDetails result = storyParser.parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(story));
	}
	
	public void shouldBuildStoryDetailsWithTitleAndPackage() throws Exception {
        // given
        String text = "Title: Joe drinks vodka\n"
                     + "Package: com.stories\n";
        StoryDetails story = new StoryDetails("Joe drinks vodka", "com.stories", "", "", "");
        // when
        StoryDetails result = storyParser.parseStory(new StringReader(text));
        // then
        ensureThat(result, eq(story));
    }
    	
	public void shouldBuildStoryDetailsWithTitleAndRole() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "As a drinker\n";
		StoryDetails story = new StoryDetails(
				"Joe drinks vodka",
				"", "drinker", "", "");
		// when
		StoryDetails result = storyParser.parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(story));
	}
	
	public void shouldBuildStoryDetailsWithTitleAndRoleAndFeature() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "As a drinker\n"
			+ "I want a glass of vodka\n";
		StoryDetails story = new StoryDetails(
				"Joe drinks vodka",
				"", "drinker", "a glass of vodka", "");
		// when
		StoryDetails result = storyParser.parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(story));
	}
	
	public void shouldBuildStoryDetailsWithTitleAndFullNarrative() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "As a drinker\n"
			+ "I want a glass of vodka\n"
			+ "So that I feel tipsy\n";
		StoryDetails story = new StoryDetails(
				"Joe drinks vodka",
				"", "drinker", "a glass of vodka", "I feel tipsy");
		// when
		StoryDetails result = storyParser.parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(story));
	}
	
	public void shouldBuildStoryDetailsWithEmptyScenario() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "Scenario: Happy path\n";
		ScenarioDetails scenario = new ScenarioDetails();
		scenario.name = "Happy path";
		StoryDetails story = new StoryDetails("Joe drinks vodka", "", "", "", "");
		story.addScenario(scenario);

		// when
		StoryDetails result = storyParser.parseStory(new StringReader(text));
		
		// then
		ensureThat(result, eq(story));
	}

    public void shouldBuildStoryDetailsWithFullScenario() throws Exception {
        // given
        String text =
            "Title: Joe drinks vodka\n"
            + "Package: org.jbehave.stories\n"
            + "Scenario: Happy path\n"
            + "Given a bar downtown\n"
            + "Given a thirsty Joe\n"
            + "When Joe asks for a Smirnov\n"
            + "Then bartender serves Joe\n"
            + "Then Joe is happy\n"
            + "Scenario: Unhappy path\n"
            + "Given a pub uptown\n"
            + "Given an equally thirsty Joe\n"
            + "When Joe asks for an Absolut\n"
            + "Then bartender tells Joe it is sold out\n"
            + "Then Joe is unhappy\n";
        StoryDetails story = new StoryDetails("Joe drinks vodka", "org.jbehave.stories", "", "", "");
        ScenarioDetails scenario1 = new ScenarioDetails();
        scenario1.name = "Happy path";
        scenario1.context.givens.add("a bar downtown");
        scenario1.context.givens.add("a thirsty Joe");
        scenario1.event.name = "Joe asks for a Smirnov";
        scenario1.outcome.outcomes.add("bartender serves Joe");
        scenario1.outcome.outcomes.add("Joe is happy");
        story.addScenario(scenario1);
        ScenarioDetails scenario2 = new ScenarioDetails();
        scenario2.name = "Unhappy path";
        scenario2.context.givens.add("a pub uptown");
        scenario2.context.givens.add("an equally thirsty Joe");
        scenario2.event.name = "Joe asks for an Absolut";
        scenario2.outcome.outcomes.add("bartender tells Joe it is sold out");
        scenario2.outcome.outcomes.add("Joe is unhappy");
        story.addScenario(scenario2);

        // when
        StoryDetails result = storyParser.parseStory(new StringReader(text));
        // then
        ensureThat(result, eq(story));
    }
    
    public void shouldBuildStoryWithComments() throws Exception {
        // given
        String text = "# This is a comment line\n" // comment
            + "#\n"                                 // empty comment 
            + "Title: Joe drinks vodka\n";
        StoryDetails story = new StoryDetails("Joe drinks vodka", "", "", "", "");
        // when
        StoryDetails result = storyParser.parseStory(new StringReader(text));
        // then
        ensureThat(result, eq(story));
    }

}
