package org.jbehave.scenario.reporters;

import org.jbehave.scenario.definition.Blurb;

/**
 * Allows the runner to report the state of running scenario steps.
 * 
 * @author Elizabeth Keogh
 */
public interface ScenarioReporter {

	void beforeScenario(String title);
	
	void afterScenario();
	
    void successful(String step);

    void pending(String step);

    void notPerformed(String step);

    void failed(String step, Throwable e);

	void beforeStory(Blurb blurb);

	void afterStory();


}
