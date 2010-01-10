package org.jbehave.scenario.definition;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

public class StoryDefinition {

    private final Blurb blurb;
    private final List<ScenarioDefinition> scenarioDefinitions;
    private String name = "Story";
    private String path = "";

    public StoryDefinition(ScenarioDefinition... scenarioDefinitions) {
        this(asList(scenarioDefinitions));
    }

    public StoryDefinition(List<ScenarioDefinition> scenarioDefinitions) {
        this(Blurb.EMPTY, scenarioDefinitions);
    }

    public StoryDefinition(Blurb blurb, ScenarioDefinition... scenarioDefinitions) {
        this(blurb, asList(scenarioDefinitions));
    }

    public StoryDefinition(Blurb blurb, List<ScenarioDefinition> scenarioDefinitions) {
        this(blurb, "", scenarioDefinitions);
    }

    public StoryDefinition(Blurb blurb, String path, List<ScenarioDefinition> scenarioDefinitions) {
        this.blurb = blurb;
        this.path = path;
        this.scenarioDefinitions = scenarioDefinitions;
    }

    public Blurb getBlurb() {
        return blurb;
    }

    public List<ScenarioDefinition> getScenarios() {
        return unmodifiableList(scenarioDefinitions);
    }

    public String getName() {
        return name;
    }

    public void namedAs(String name) {
        this.name = name;
    }

    public String getPath() {
        return (path != null ? path : "");
    }
}
