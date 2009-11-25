package org.jbehave.scenario.reporters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.reporters.ScenarioReporter;

/**
 * Reporter which collects other {@link ScenarioReporter}s and delegates all invocations to the collected reporters.
 * 
 * @author Mirko FriedenHagen
 */
public class CollectingScenarioReporter implements ScenarioReporter {

    private final Collection<ScenarioReporter> reporters;

    /**
     * Initializes this reporter with a collection of reporters.
     * 
     * @param scenarioReporters
     *            the reporters to iterate over.
     */
    public CollectingScenarioReporter(final Collection<ScenarioReporter> scenarioReporters) {
        this.reporters = scenarioReporters;
    }

    /**
     * Initializes this reporter with an array of reporters.
     * 
     * @param scenarioReporters
     *            the reporters to iterate over.
     */
    public CollectingScenarioReporter(final ScenarioReporter... scenarioReporters) {
        this(Arrays.asList(scenarioReporters));
    }

    public void afterScenario() {
        for (ScenarioReporter reporter : reporters) {
            reporter.afterScenario();
        }
    }

    public void afterStory() {
        for (ScenarioReporter reporter : reporters) {
            reporter.afterStory();
        }
    }

    public void beforeScenario(String title) {
        for (ScenarioReporter reporter : reporters) {
            reporter.beforeScenario(title);
        }
    }

    public void beforeStory(StoryDefinition story) {
        for (ScenarioReporter reporter : reporters) {
            reporter.beforeStory(story);
        }
    }
    
    public void beforeStory(Blurb blurb) {
        for (ScenarioReporter reporter : reporters) {
            reporter.beforeStory(blurb);
        }
    }

    public void examplesTable(ExamplesTable table) {
        for (ScenarioReporter reporter : reporters) {
            reporter.examplesTable(table);
        }
    }

    public void examplesTableRow(Map<String, String> tableRow) {
        for (ScenarioReporter reporter : reporters) {
            reporter.examplesTableRow(tableRow);
        }
    }

    public void failed(String step, Throwable e) {
        for (ScenarioReporter reporter : reporters) {
            reporter.failed(step, e);
        }
    }

    public void givenScenarios(List<String> givenScenarios) {
        for (ScenarioReporter reporter : reporters) {
            reporter.givenScenarios(givenScenarios);
        }
    }

    public void notPerformed(String step) {
        for (ScenarioReporter reporter : reporters) {
            reporter.notPerformed(step);
        }
    }

    public void pending(String step) {
        for (ScenarioReporter reporter : reporters) {
            reporter.pending(step);
        }
    }

    public void successful(String step) {
        for (ScenarioReporter reporter : reporters) {
            reporter.successful(step);
        }
    }
    
    public Collection<ScenarioReporter> getScenarioReporters() {
        return reporters;
    }
}
