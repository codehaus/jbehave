package org.jbehave.scenario.steps;

import static java.text.MessageFormat.format;

import java.io.PrintStream;
import java.lang.reflect.Type;

public class PrintStreamStepMonitor implements StepMonitor {
    private final PrintStream output;

    public PrintStreamStepMonitor() {
        this(System.out);
    }

    public PrintStreamStepMonitor(PrintStream output) {
        this.output = output;
    }

    public void stepMatchesPattern(String step, boolean matches, String pattern) {
        String message = format("Step ''{0}'' {1} pattern ''{2}''", step, (matches ? "matches" : "does not match"),
                pattern);        
        print(output, message);
    }

    public void convertingValueOfType(String value, Type type) {
        String message = format("Converting value ''{0}'' of type ''{1}''", value, type);
        print(output,message);
    }
    
    protected void print(PrintStream output, String message) {
        output.println(message);
    }

}
