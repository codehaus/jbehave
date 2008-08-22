package org.jbehave.scenario.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.ScenarioDefinition;
import org.jbehave.scenario.StoryDefinition;
import org.jbehave.scenario.definition.Blurb;

public class PatternScenarioParser implements ScenarioParser {

    private static final String PATTERN_TO_PULL_SCENARIOS_INTO_GROUP_4 = ".*?((Scenario:) (.|\\s)*?)\\s*(\\Z|Scenario:).*";
	private static final String PATTERN_TO_PULL_STORY_BLURB_INTO_GROUP_1 = "(.*?)(Scenario:|Given).*";
	private static final String PATTERN_TO_PULL_SCENARIO_TITLE_INTO_GROUP_1 = "Scenario:(.*?)\\s*(Given|When|Then).*";
    public static final String PATTERN_TO_PULL_OUT_STEPS = "((Given|When|Then) (.|\\s)*?)\\s*(\\Z|Given|When|Then|Scenario:)";

    private final Pattern pattern;

    public PatternScenarioParser() {
        this(PATTERN_TO_PULL_OUT_STEPS);
    }

    public PatternScenarioParser(String parseRegex) {
        this.pattern = Pattern.compile(parseRegex);
    }

    public StoryDefinition defineStoryFrom(String wholeStoryAsString) {
    	 
    	Blurb blurb = parseBlurbFrom(wholeStoryAsString);
    	List<ScenarioDefinition> scenarioDefinitions = parseScenariosFrom(wholeStoryAsString);
        return new StoryDefinition(blurb, scenarioDefinitions);
    }

	private List<ScenarioDefinition> parseScenariosFrom(String wholeStoryAsString) {
		List<ScenarioDefinition> scenarioDefinitions = new ArrayList<ScenarioDefinition>();

    	List<String> scenarios = splitScenarios(wholeStoryAsString);
    	for (String scenario : scenarios) {
    		Matcher findingTitle = Pattern.compile(PATTERN_TO_PULL_SCENARIO_TITLE_INTO_GROUP_1, Pattern.DOTALL).matcher(scenario);
			scenarioDefinitions.add(new ScenarioDefinition(findingTitle.find() ? findingTitle.group(1).trim() : "", findSteps(scenario)));
		}
		return scenarioDefinitions;
	}

    private List<String> findSteps(String scenarioAsString) {
        Matcher matcher = pattern.matcher(scenarioAsString);
        List<String> steps = new ArrayList<String>();
        int startAt = 0;
        while (matcher.find(startAt)) {
            steps.add(matcher.group(1));
            startAt = matcher.start(4);
        }

        return steps;
	}

	private Blurb parseBlurbFrom(String wholeStoryAsString) {
		Pattern findStoryBlurb = Pattern.compile(PATTERN_TO_PULL_STORY_BLURB_INTO_GROUP_1, Pattern.DOTALL);
		Matcher matcher = findStoryBlurb.matcher(wholeStoryAsString);
		if (matcher.find()) {
			return new Blurb(matcher.group(1).trim());
		} else {
			return Blurb.EMPTY;
		}
	}



	private List<String> splitScenarios(String allScenariosInFile) {
    	Pattern scenarioSplitter = Pattern.compile(PATTERN_TO_PULL_SCENARIOS_INTO_GROUP_4, Pattern.DOTALL);
    	Matcher matcher = scenarioSplitter.matcher(allScenariosInFile);
    	int startAt = 0;
    	List<String> scenarios = new ArrayList<String>();
    	if (matcher.matches()) {
			while(matcher.find(startAt)) {
				scenarios.add(matcher.group(1));
				startAt = matcher.start(4);
			}
    	} else {
    		String loneScenario = allScenariosInFile;
			scenarios.add(loneScenario);
    	}
    	return scenarios;
    }

}
