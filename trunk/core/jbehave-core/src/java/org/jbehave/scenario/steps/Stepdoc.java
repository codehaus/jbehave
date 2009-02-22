package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;

public class Stepdoc implements Comparable<Stepdoc> {

	private final Class<? extends Annotation> annotation;
	private final String pattern;
	private final List<String> aliasPatterns;
	private final Method method;
	private Integer priority = 0;

	public Stepdoc(Class<? extends Annotation> annotation, String pattern,
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

    /**
     * Method signature without "public void" (etc) prefix
     * @return
     */
    public String getSignature() {
        String methodSignature = method.toString();
        int ix = methodSignature.indexOf(" ");
        ix = methodSignature.indexOf(" ", ix+1);
        return methodSignature.substring(ix+1);
    }

    @Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[Stepdoc pattern=").append(pattern).append(", aliases=")
				.append(aliasPatterns).append(", method=").append(getSignature()).append("]");
		return sb.toString();
	}

	public int compareTo(Stepdoc that) {
        int retVal = this.priority.compareTo(that.priority);
        if (retVal == 0) {
            retVal = this.getPattern().compareTo(that.getPattern());
        }
        return retVal;
	}
}
