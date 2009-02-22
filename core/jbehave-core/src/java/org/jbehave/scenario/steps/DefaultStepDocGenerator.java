package org.jbehave.scenario.steps;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jbehave.scenario.annotations.Aliases;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;

public class DefaultStepDocGenerator implements StepDocGenerator {

	public List<StepDoc> generate(Class<?> stepsClass) {
		List<StepDoc> stepdocs = new LinkedList<StepDoc>();
		for (Method method : stepsClass.getMethods()) {
			if (method.isAnnotationPresent(Given.class)) {
				stepdocs.add(new StepDoc(Given.class, method.getAnnotation(Given.class).value(), 
						aliases(method), method));
			}
			if (method.isAnnotationPresent(When.class)) {
				stepdocs.add(new StepDoc(When.class, method.getAnnotation(When.class).value(), 
						aliases(method), method));
			}
			if (method.isAnnotationPresent(Then.class)) {
				stepdocs.add(new StepDoc(Then.class, method.getAnnotation(Then.class).value(), 
						aliases(method), method));
			}
		}
		Collections.sort(stepdocs);
		return stepdocs;		
	}

	private String[] aliases(Method method) {
		if (method.isAnnotationPresent(Aliases.class)) {
			return method.getAnnotation(Aliases.class).values();
		}
		return new String[]{};
	}

}
