package org.jbehave.scenario.steps;

import static java.text.MessageFormat.format;

public class ConsoleStepMonitor implements StepMonitor {

    public void stepMatchesPattern(String step, boolean matches, String pattern) {
        System.out.println(format("Step ''{0}'' {1} pattern ''{2}''", step, (matches?"matches":"does not match"), pattern));
    }

}
