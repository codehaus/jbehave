package jbehave.core.story.codegen.parser;

import java.io.StringReader;

import jbehave.core.mock.UsingConstraints;
import jbehave.core.story.codegen.domain.ScenarioDetails;
import jbehave.core.story.codegen.domain.StoryDetails;
import jbehave.core.story.codegen.parser.TextStoryParser;


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
}
