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
}
