package org.jbehave.scenario.steps;

import java.util.List;

public interface StepDocGenerator {

	List<StepDoc> generate(Class<?> stepsClass);

}
