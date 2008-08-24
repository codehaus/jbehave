package org.jbehave.scenario.steps;

import java.lang.reflect.Type;

/**
 * Interface to monitor step events
 * 
 * @author Mauro Talevi
 */
public interface StepMonitor {

    void stepMatchesPattern(String string, boolean matches, String pattern);

    void convertingValueOfType(String value, Type type);

}
