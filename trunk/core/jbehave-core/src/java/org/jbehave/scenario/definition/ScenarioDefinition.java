package org.jbehave.scenario.definition;

import java.util.Arrays;
import java.util.List;

public class ScenarioDefinition {

    private final List<String> steps;
    private final String title;
	private final Table table;

    public ScenarioDefinition(List<String> steps) {
        this("", steps);
    }

    public ScenarioDefinition(String title, List<String> steps) {
        this(title, new Table(""), steps);
    }

    public ScenarioDefinition(String title, String... steps) {
        this(title, Arrays.asList(steps));
    }
    
    public ScenarioDefinition(String title, Table table, List<String> steps) {
    	this.title = title;
    	this.steps = steps;
		this.table = table;
    }

    public ScenarioDefinition(String title, Table table, String... steps) {
        this(title, table, Arrays.asList(steps));
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getTitle() {
        return title;
    }

    public Table getTable(){
    	return table;
    }
    
}
