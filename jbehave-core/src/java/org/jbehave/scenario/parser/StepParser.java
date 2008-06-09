package org.jbehave.scenario.parser;

import java.util.List;

public interface StepParser {

    List<String> findSteps(String scenarioAsString);

}
