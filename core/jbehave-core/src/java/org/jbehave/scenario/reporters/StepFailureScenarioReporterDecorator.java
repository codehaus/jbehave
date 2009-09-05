package org.jbehave.scenario.reporters;

import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.Table;
import org.jbehave.scenario.errors.StepFailure;

/**
 * <p>
 * When a step fails, the {@link Throwable} that caused the failure is wrapped
 * in a {@link StepFailure} together with the step during which the failure
 * occurred. If such a failure occurs it will throw the {@link StepFailure}
 * after the story is finished.
 * </p>
 * 
 * @see StepFailure
 */
public class StepFailureScenarioReporterDecorator implements ScenarioReporter {

	private final ScenarioReporter delegate;
	private StepFailure failure;

	public StepFailureScenarioReporterDecorator(ScenarioReporter delegate) {
		this.delegate = delegate;
	}

	public void afterScenario() {
		delegate.afterScenario();
	}

	public void afterStory() {
		delegate.afterStory();
		if (failure != null) {
			throw failure;
		}
	}

	public void beforeScenario(String title) {
		delegate.beforeScenario(title);
	}

	public void beforeStory(Blurb blurb) {
		failure = null;
		delegate.beforeStory(blurb);
	}

	public void failed(String step, Throwable cause) {
		failure = new StepFailure(step, cause);
		delegate.failed(step, failure);
	}

	public void notPerformed(String step) {
		delegate.notPerformed(step);
	}

	public void pending(String step) {
		delegate.pending(step);
	}

	public void successful(String step) {
		delegate.successful(step);
	}

	public void givenScenarios(List<String> givenScenarios) {
		delegate.givenScenarios(givenScenarios);		
	}

	public void usingTable(Table table) {
		delegate.usingTable(table);
	}

	public void usingTableRow(Map<String, String> tableRow) {
		delegate.usingTableRow(tableRow);
	}

}
