package org.jbehave.scenario.steps;

/**
 * Represents the list of candidate steps that can be performed
 */
public interface CandidateSteps {

    /**
     * Return all the steps that can be performed by the implementing class
     * 
     * @return The list of candidate steps
     */
    CandidateStep[] getSteps();

    /**
     * Return all the steps that can be performed by the given class
     * 
     * @return The list of candidate steps
     */
    CandidateStep[] getSteps(Class<?> stepsClass);

}
