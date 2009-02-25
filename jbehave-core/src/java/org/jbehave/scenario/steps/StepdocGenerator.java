package org.jbehave.scenario.steps;

import java.util.List;

public interface StepdocGenerator {

	List<Stepdoc> generate(Class<?> stepsClass);

}
