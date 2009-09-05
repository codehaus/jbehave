package org.jbehave.scenario.steps;

import java.lang.reflect.Type;

/**
 * Interface to monitor step events
 * 
 * @author Mauro Talevi
 */
public interface StepMonitor {

    void stepMatchesPattern(String step, boolean matches, String pattern);

    void convertedValueOfType(String value, Type type, Object converted, Class<?> converterClass);

    void performing(String step);

	void usingAnnotatedName(String name, int index);

	void usingParameterName(String name, int index);

	void usingTableAnnotatedName(String name, int index);

	void usingTableParameterName(String name, int index);

	void usingNaturalOrder(int index);

	void foundArg(String arg, int index);
}
