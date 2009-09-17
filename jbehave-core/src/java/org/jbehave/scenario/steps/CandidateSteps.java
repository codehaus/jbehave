package org.jbehave.scenario.steps;

import java.util.Collection;

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

    /**
     * Return all steps to run before scenario
     * 
     * @return The list of steps 
     */
    Collection<? extends Step> runBeforeScenario();

    /**
     * Return all steps to run after scenario
     * 
     * @return The list of steps 
     */
    Collection<? extends Step> runAfterScenario();

}
