package org.jbehave.scenario.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternStepParser implements StepParser {

    public static final String DEFAULT_PARSE_REGEX = "((Given|When|Then) (.|\\s)*?)\\s*(\\Z|Given|When|Then|Scenario:)";

    private final Pattern pattern;

    public PatternStepParser() {
        this(DEFAULT_PARSE_REGEX);
    }

    public PatternStepParser(String parseRegex) {
        this.pattern = Pattern.compile(parseRegex);
    }

    public List<String> findSteps(String scenarioAsString) {
        Matcher matcher = pattern.matcher(scenarioAsString);
        List<String> steps = new ArrayList<String>();
        int startAt = 0;
        while (matcher.find(startAt)) {
            steps.add(matcher.group(1));
            startAt = matcher.start(4);
        }

        return steps;
    }

}
