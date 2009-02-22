package org.jbehave.scenario.reporters;

import java.util.List;

import org.jbehave.scenario.steps.StepDoc;

/**
 * Generates reports of generated StepDocs
 * 
 * @author Mauro Talevi
 */
public interface StepDocReporter {

	void report(List<StepDoc> stepdocs);

}
