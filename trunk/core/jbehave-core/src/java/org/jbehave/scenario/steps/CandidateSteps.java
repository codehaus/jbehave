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

    Collection<? extends Step> runBeforeScenario();

    Collection<? extends Step> runAfterScenario();

}
