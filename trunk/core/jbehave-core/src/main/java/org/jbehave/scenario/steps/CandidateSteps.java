package org.jbehave.scenario.steps;

import java.util.List;

/**
 * Represents the list of candidate steps that can be performed
 */
public interface CandidateSteps {

    /**
     * Return all the candidate steps that can be performed by the implementing class
     * 
     * @return The list of candidate steps
     */
    CandidateStep[] getSteps();

    /**
     * Return all the candidate steps that can be performed by the given class
     * 
     * @return The list of candidate steps
     */
    CandidateStep[] getSteps(Class<?> stepsClass);

    /**
     * Return all the executable steps to run before each story
     * 
     * @return The list of steps 
     */
    List<Step> runBeforeStory();

    /**
     * Return all the executable steps to run after each story
     * 
     * @return The list of steps 
     */
    List<Step> runAfterStory();

    /**
     * Return all the executable steps to run before each scenario
     * 
     * @return The list of steps 
     */
    List<Step> runBeforeScenario();

    /**
     * Return all the executable steps to run after each scenario
     * 
     * @return The list of steps 
     */
    List<Step> runAfterScenario();


}
