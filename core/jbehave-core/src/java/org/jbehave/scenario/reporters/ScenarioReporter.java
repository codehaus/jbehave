package org.jbehave.scenario.reporters;

import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.Table;

/**
 * Allows the runner to report the state of running scenarios
 * 
 * @author Elizabeth Keogh
 * @author Mauro Talevi
 */
public interface ScenarioReporter {

    void beforeStory(Blurb blurb);

    void afterStory();

    void beforeScenario(String title);
    
    void afterScenario();
    
	void givenScenarios(List<String> givenScenarios);

	void usingTable(Table table);

	void usingTableRow(Map<String, String> tableRow);

    void successful(String step);

    void pending(String step);

    void notPerformed(String step);

    void failed(String step, Throwable e);

}
