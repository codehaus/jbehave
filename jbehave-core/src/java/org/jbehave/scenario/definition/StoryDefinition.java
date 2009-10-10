package org.jbehave.scenario.definition;

import static java.util.Arrays.asList;

import java.util.Collections;
import java.util.List;

public class StoryDefinition {

    private final Blurb blurb;
    private final List<ScenarioDefinition> scenarioDefinitions;

    public StoryDefinition(Blurb blurb, List<ScenarioDefinition> scenarioDefinitions) {
        this.blurb = blurb;
        this.scenarioDefinitions = scenarioDefinitions;
    }

    public StoryDefinition(List<ScenarioDefinition> scenarioDefinitions) {
        this(Blurb.EMPTY, scenarioDefinitions);
    }
    
    public StoryDefinition(Blurb blurb, ScenarioDefinition... scenarioDefinitions) {
        this(blurb, asList(scenarioDefinitions));
    }
    
    public StoryDefinition(ScenarioDefinition... scenarioDefinitions) {
        this(asList(scenarioDefinitions));
    }

    public Blurb getBlurb() {
        return blurb;
    }

    public List<ScenarioDefinition> getScenarios() {
        return Collections.unmodifiableList(scenarioDefinitions);
    }
}
