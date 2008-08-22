package org.jbehave.scenario.steps;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.parser.StepPatternBuilder;

public class Steps {

    private final StepPatternBuilder patternBuilder;
    private final StepMonitor monitor;
    private final String[] startingWords;

    public Steps() {
        this(new PrefixCapturingPatternBuilder(), new SilentStepMonitor(), "Given", "When", "Then", "And");
    }

    public Steps(StepPatternBuilder patternBuilder, StepMonitor monitor, String... startingWords) {
        this.patternBuilder = patternBuilder;
        this.monitor = monitor;
        this.startingWords = startingWords;
    }

    /**
     * Returns all the steps that can be performed by this class.
     */
    public CandidateStep[] getSteps() {
        ArrayList<CandidateStep> steps = new ArrayList<CandidateStep>();
        for (Method method : this.getClass().getMethods()) {
            if (method.isAnnotationPresent(Given.class)) {
                createCandidateStep(steps, method, method.getAnnotation(Given.class).value());
            }
            if (method.isAnnotationPresent(When.class)) {
                createCandidateStep(steps, method, method.getAnnotation(When.class).value());
            }
            if (method.isAnnotationPresent(Then.class)) {
                createCandidateStep(steps, method, method.getAnnotation(Then.class).value());
            }
        }
        return steps.toArray(new CandidateStep[steps.size()]);
    }

    private void createCandidateStep(ArrayList<CandidateStep> steps, Method method, String stepAsString) {
        steps.add(new CandidateStep(stepAsString, method, this, patternBuilder, monitor, startingWords));
    }
}
