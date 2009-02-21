package org.jbehave.scenario.steps;

import java.io.PrintStream;

public class SilentStepMonitor extends PrintStreamStepMonitor {

    protected void print(PrintStream output, String message) {
        // print nothing
    }

}
