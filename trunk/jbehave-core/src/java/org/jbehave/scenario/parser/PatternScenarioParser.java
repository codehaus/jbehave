package org.jbehave.scenario.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.Configuration;
import org.jbehave.scenario.ScenarioDefinition;
import org.jbehave.scenario.StoryDefinition;
import org.jbehave.scenario.definition.Blurb;

public class PatternScenarioParser implements ScenarioParser {

    private final Configuration configuration;

    public PatternScenarioParser(Configuration configuration) {
        this.configuration = configuration;
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
            Matcher findingTitle = patternToPullScenarioTitlesIntoGroupOne().matcher(scenario);
            scenarioDefinitions.add(new ScenarioDefinition(findingTitle.find() ? findingTitle.group(1).trim() : "", findSteps(scenario)));
        }
        return scenarioDefinitions;
    }
    
    private List<String> findSteps(String scenarioAsString) {
        Matcher matcher = patternToPullOutSteps().matcher(scenarioAsString);
        List<String> steps = new ArrayList<String>();
        int startAt = 0;
        while (matcher.find(startAt)) {
            steps.add(matcher.group(1));
            startAt = matcher.start(4);
        }

        return steps;
    }

    private Blurb parseBlurbFrom(String wholeStoryAsString) {
        Pattern findStoryBlurb = Pattern.compile("(.*?)(" + configuration.keywords().scenario() + ":).*", Pattern.DOTALL);
        Matcher matcher = findStoryBlurb.matcher(wholeStoryAsString);
        if (matcher.find()) {
            return new Blurb(matcher.group(1).trim());
        } else {
            return Blurb.EMPTY;
        }
    }

    private List<String> splitScenarios(String allScenariosInFile) {
        Pattern scenarioSplitter = patternToPullScenariosIntoGroupFour();
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

    private Pattern patternToPullScenariosIntoGroupFour() {
        return Pattern.compile(".*?((Scenario:) (.|\\s)*?)\\s*(\\Z|Scenario:).*".replace("Scenario", configuration.keywords().scenario()), Pattern.DOTALL);
    }

    private Pattern patternToPullScenarioTitlesIntoGroupOne() {
        String concatenatedKeywords = concatenateWithOr(configuration.keywords().given(), configuration.keywords().when(), configuration.keywords().then(), configuration.keywords().others());
        return Pattern.compile(configuration.keywords().scenario() + ":(.*?)\\s*(" + concatenatedKeywords + ").*");
    }


    private String concatenateWithOr(String given, String when, String then,
            String[] others) {
        StringBuilder builder = new StringBuilder();
        builder.append(given).append("|");
        builder.append(when).append("|");
        builder.append(then).append("|");
        return builder.append(concatenateWithOr(others)).toString();
    }

    private String concatenateWithOr(String... keywords) {
        return concatenateWithOr(new StringBuilder(), keywords);
    }
    
    private String concatenateWithOr(StringBuilder builder,
            String[] keywords) {
        for (String other : keywords) {
            builder.append(other).append("|");
        }
        String result = builder.toString();
        return result.substring(0, result.length() - 1); // chop off the last |
    }

    private Pattern patternToPullOutSteps() {
        String givenWhenThen = concatenateWithOr(configuration.keywords().given(), configuration.keywords().when(), configuration.keywords().then(), configuration.keywords().others());
        return Pattern.compile("((" + givenWhenThen + ") (.|\\s)*?)\\s*(\\Z|" + givenWhenThen + "|" + configuration.keywords().scenario()+ ":)");
    }
}
