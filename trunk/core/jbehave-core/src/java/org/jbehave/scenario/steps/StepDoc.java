package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.util.List;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;

public class StepDoc implements Comparable<StepDoc> {

	private final Class<? extends Annotation> annotation;
	private final String pattern;
	private final String[] aliasPatterns;
	private Integer priority = 0;

	public StepDoc(Class<? extends Annotation> annotation, String pattern,
			String[] aliasPatterns) {
		this.annotation = annotation;
		this.pattern = pattern;
		this.aliasPatterns = aliasPatterns;
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
		return asList(aliasPatterns);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[StepDoc pattern=").append(pattern).append(", aliases=")
				.append(asList(aliasPatterns)).append("]");
		return sb.toString();
	}

	public int compareTo(StepDoc that) {
		return this.priority.compareTo(that.priority);
	}
}
