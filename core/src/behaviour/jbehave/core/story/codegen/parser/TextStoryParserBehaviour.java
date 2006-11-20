package jbehave.core.story.codegen.parser;

import java.io.StringReader;

import jbehave.core.mock.UsingConstraints;
import jbehave.core.story.codegen.domain.ScenarioDetails;
import jbehave.core.story.codegen.domain.StoryDetails;


public class TextStoryParserBehaviour extends UsingConstraints {

	public void shouldBuildStoryDetailsWithTitle() throws Exception {
		// given
		String text = "Title: Joe drinks vodka\n";
		StoryDetails expectedStory = new StoryDetails("Joe drinks vodka", "", "", "");
		// when
		StoryDetails result = new TextStoryParser().parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(expectedStory));
	}
	
	public void shouldBuildStoryDetailsWithTitleAndRole() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "As a drinker\n";
		StoryDetails expectedStory = new StoryDetails(
				"Joe drinks vodka",
				"drinker", "", "");
		// when
		StoryDetails result = new TextStoryParser().parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(expectedStory));
	}
	
	public void shouldBuildStoryDetailsWithTitleAndRoleAndFeature() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "As a drinker\n"
			+ "I want a glass of vodka\n";
		StoryDetails expectedStory = new StoryDetails(
				"Joe drinks vodka",
				"drinker", "a glass of vodka", "");
		// when
		StoryDetails result = new TextStoryParser().parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(expectedStory));
	}
	
	public void shouldBuildStoryDetailsWithTitleAndFullNarrative() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "As a drinker\n"
			+ "I want a glass of vodka\n"
			+ "So that I feel tipsy\n";
		StoryDetails expectedStory = new StoryDetails(
				"Joe drinks vodka",
				"drinker", "a glass of vodka", "I feel tipsy");
		// when
		StoryDetails result = new TextStoryParser().parseStory(new StringReader(text));
		// then
		ensureThat(result, eq(expectedStory));
	}
	
	public void shouldBuildStoryDetailsWithEmptyScenario() throws Exception {
		// given
		String text =
			"Title: Joe drinks vodka\n"
			+ "Scenario: Happy path\n";
		ScenarioDetails expectedScenario = new ScenarioDetails();
		expectedScenario.name = "Happy path";
		StoryDetails expectedStory = new StoryDetails("Joe drinks vodka", "", "", "");
		expectedStory.addScenario(expectedScenario);

		// when
		StoryDetails result = new TextStoryParser().parseStory(new StringReader(text));
		
		// then
		ensureThat(result, eq(expectedStory));
	}


    public void shouldBuildStoryDetailsWithFullScenario() throws Exception {
        // given
        String text =
            "Title: Joe drinks vodka\n"
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
        StoryDetails expectedStory = new StoryDetails("Joe drinks vodka", "", "", "");
        ScenarioDetails expectedScenario1 = new ScenarioDetails();
        expectedScenario1.name = "Happy path";
        expectedScenario1.context.givens.add("a bar downtown");
        expectedScenario1.context.givens.add("a thirsty Joe");
        expectedScenario1.event.name = "Joe asks for a Smirnov";
        expectedScenario1.outcome.outcomes.add("bartender serves Joe");
        expectedScenario1.outcome.outcomes.add("Joe is happy");
        expectedStory.addScenario(expectedScenario1);
        ScenarioDetails expectedScenario2 = new ScenarioDetails();
        expectedScenario2.name = "Unhappy path";
        expectedScenario2.context.givens.add("a pub uptown");
        expectedScenario2.context.givens.add("an equally thirsty Joe");
        expectedScenario2.event.name = "Joe asks for an Absolut";
        expectedScenario2.outcome.outcomes.add("bartender tells Joe it is sold out");
        expectedScenario2.outcome.outcomes.add("Joe is unhappy");
        expectedStory.addScenario(expectedScenario2);

        // when
        StoryDetails result = new TextStoryParser().parseStory(new StringReader(text));
        
        // then
        ensureThat(result, eq(expectedStory));
    }
    
}
