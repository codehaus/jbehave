package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;

public class StepDoc implements Comparable<StepDoc> {

	private final Class<? extends Annotation> annotation;
	private final String pattern;
	private final List<String> aliasPatterns;
	private final Method method;
	private Integer priority = 0;

	public StepDoc(Class<? extends Annotation> annotation, String pattern,
			String[] aliasPatterns, Method method) {
		this.annotation = annotation;
		this.pattern = pattern;
		this.aliasPatterns = asList(aliasPatterns);
		this.method = method;
		assignPriority();
	}

	private void assignPriority() {
		if (annotation.equals(Given.class)) {
			priority = 1;
		} else if (annotation.equals(When.class)) {
			priority = 2;
		} else if (annotation.equals(Then.class)) {
			priority = 3;
		}

	}

	public Class<? extends Annotation> getAnnotation() {
		return annotation;
	}

	public String getPattern() {
		return pattern;
	}

	public List<String> getAliasPatterns() {
		return aliasPatterns;
	}
	
	public Method getMethod() {
		return method;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[StepDoc pattern=").append(pattern).append(", aliases=")
				.append(aliasPatterns).append(", method=").append(method).append("]");
		return sb.toString();
	}

	public int compareTo(StepDoc that) {
		return this.priority.compareTo(that.priority);
	}
}
