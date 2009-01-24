package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.Arrays;
import java.util.List;

import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.definition.StoryDefinition;
import org.junit.Test;


public class PatternScenarioParserBehaviour {

    private static final String NL = System.getProperty("line.separator");

    @Test
    public void shouldExtractGivensWhensAndThensFromSimpleScenarios() {
        ScenarioParser parser = new PatternScenarioParser(new PropertyBasedConfiguration());
        StoryDefinition story = parser.defineStoryFrom(
                "Given a scenario" + NL + 
                "When I parse it" + NL + 
                "Then I should get steps");
        
        List<String> steps = story.getScenarios().get(0).getSteps();
        ensureThat(steps.get(0), equalTo("Given a scenario"));
        ensureThat(steps.get(1), equalTo("When I parse it"));
        ensureThat(steps.get(2), equalTo("Then I should get steps"));
    }
    
    @Test
    public void shouldExtractGivensWhensAndThensFromSimpleScenariosContainingKeywordsAsPartOfTheContent() {
        ScenarioParser parser = new PatternScenarioParser(new PropertyBasedConfiguration());
        StoryDefinition story = parser.defineStoryFrom(
                "Given a scenario Givenly" + NL + 
                "When I parse it to Whenever" + NL +
                "And I parse it to Anderson" + NL +
                "Then I should get steps Thenact");
        
        List<String> steps = story.getScenarios().get(0).getSteps();
        ensureThat(steps.get(0), equalTo("Given a scenario Givenly"));
        ensureThat(steps.get(1), equalTo("When I parse it to Whenever"));
        ensureThat(steps.get(2), equalTo("And I parse it to Anderson"));
        ensureThat(steps.get(3), equalTo("Then I should get steps Thenact"));
    }    
    
    @Test
    public void shouldExtractGivensWhensAndThensFromMultilineScenarios() {
        ScenarioParser parser = new PatternScenarioParser(new PropertyBasedConfiguration());
        StoryDefinition story = parser.defineStoryFrom(
                "Given a scenario" + NL +
                "with this line" + NL +
                "When I parse it" + NL +
                "with another line" + NL + NL +
                "Then I should get steps" + NL +
                "without worrying about lines" + NL +
                "or extra white space between or after steps" + NL + NL);

        List<String> steps = story.getScenarios().get(0).getSteps();
        
        ensureThat(steps.get(0), equalTo("Given a scenario" + NL +
                "with this line" ));
        ensureThat(steps.get(1), equalTo("When I parse it" + NL +
                "with another line"));
        ensureThat(steps.get(2), equalTo("Then I should get steps" + NL +
                "without worrying about lines" + NL +
                "or extra white space between or after steps"));
    }
    
    @Test
    public void canParseMultipleScenariosFromOnePieceOfText() {
        String wholeStory = 
            "Scenario: the first scenario " + NL + NL +
            "Given my scenario" + NL + NL +
            "Scenario: the second scenario" + NL + NL +
            "Given my second scenario";
        PatternScenarioParser parser = new PatternScenarioParser(new PropertyBasedConfiguration());
        StoryDefinition story = parser.defineStoryFrom(wholeStory);
        
        ensureThat(story.getScenarios().get(0).getTitle(), equalTo("the first scenario"));
        ensureThat(story.getScenarios().get(0).getSteps(), equalTo(Arrays.asList(new String[]{"Given my scenario"})));
        ensureThat(story.getScenarios().get(1).getTitle(), equalTo("the second scenario"));
        ensureThat(story.getScenarios().get(1).getSteps(), equalTo(Arrays.asList(new String[]{"Given my second scenario"})));
    }
    
    @Test
    public void canLoadFullStories() {
        String wholeStory = 
            "Story: I can output narratives" + NL + NL +
            
            "As a developer" + NL +
            "I want to see the narrative for my story when a scenario in that story breaks" + NL +
            "So that I can see what we're not delivering" + NL + NL +
            
            "Scenario: A pending scenario" + NL + NL +
            "Given a step that's pending" + NL +
            "When I run the scenario" + NL +
            "Then I should see this in the output" + NL +

            "Scenario: A passing scenario" + NL +
    
            "Given I'm not reporting passing scenarios" + NL +
            "When I run the scenario" + NL +
            "Then this should not be in the output" + NL +
    
            "Scenario: A failing scenario" + NL +
    
            "Given a step that fails" + NL +
            "When I run the scenario" + NL +
            "Then I should see this in the output" + NL +
            "And I should see this in the output" + NL;
        
        StoryDefinition story = new PatternScenarioParser(new PropertyBasedConfiguration()).defineStoryFrom(wholeStory);
        
        ensureThat(story.getBlurb().asString(), equalTo("Story: I can output narratives" + NL + NL +
                    "As a developer" + NL +
                    "I want to see the narrative for my story when a scenario in that story breaks" + NL +
                    "So that I can see what we're not delivering"));
        
        ensureThat(story.getScenarios().get(0).getTitle(), equalTo("A pending scenario"));
        ensureThat(story.getScenarios().get(0).getSteps(), equalTo(Arrays.asList(new String[]{
                "Given a step that's pending",
                "When I run the scenario",
                "Then I should see this in the output"
        })));
        
        ensureThat(story.getScenarios().get(1).getTitle(), equalTo("A passing scenario"));
        ensureThat(story.getScenarios().get(1).getSteps(), equalTo(Arrays.asList(new String[]{
                "Given I'm not reporting passing scenarios",
                "When I run the scenario",
                "Then this should not be in the output"
        })));
        
        ensureThat(story.getScenarios().get(2).getTitle(), equalTo("A failing scenario"));
        ensureThat(story.getScenarios().get(2).getSteps(), equalTo(Arrays.asList(new String[]{
                "Given a step that fails",
                "When I run the scenario",
                "Then I should see this in the output",
                "And I should see this in the output"
        })));
    }
    
}
