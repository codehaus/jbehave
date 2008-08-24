package org.jbehave.scenario.steps;



public interface Step {

    StepResult perform();

    StepResult doNotPerform();
}
