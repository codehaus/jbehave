package org.jbehave.scenario;

/**
 * Allows to report the state of running scenario steps.
 * 
 * @author Elizabeth Keogh
 */
public interface ScenarioReporter {

    void successful(String step);

    void pending(String step);

    void notPerformed(String step);

    void failed(String step, Throwable e);

}
