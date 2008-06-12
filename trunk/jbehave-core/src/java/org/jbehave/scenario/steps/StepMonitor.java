package org.jbehave.scenario.steps;

/**
 * Interface to monitor step events
 * 
 * @author Mauro Talevi
 */
public interface StepMonitor {

    void stepMatchesPattern(String string, boolean matches, String pattern);

}
