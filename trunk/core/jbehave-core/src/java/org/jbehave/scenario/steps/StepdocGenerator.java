package org.jbehave.scenario.steps;

import java.util.List;

/**
 *  Generates a list of {@link Stepdoc}s from the annotations in a given {@link Steps} class.
 */
public interface StepdocGenerator {

	List<Stepdoc> generate(Class<?> stepsClass);

}
