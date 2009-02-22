package org.jbehave.scenario.steps;

import java.util.List;

public interface Stepdoc2Generator {

	List<Stepdoc2> generate(Class<?> stepsClass);

}
