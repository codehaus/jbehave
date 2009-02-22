package org.jbehave.scenario.reporters;

import java.util.List;

import org.jbehave.scenario.steps.Stepdoc2;

/**
 * Generates reports of generated StepDocs
 * 
 * @author Mauro Talevi
 */
public interface Stepdoc2Reporter {

	void report(List<Stepdoc2> stepdocs);

}
