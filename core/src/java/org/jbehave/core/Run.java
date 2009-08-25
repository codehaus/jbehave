package org.jbehave.core;

import java.io.PrintStream;

/**
 * @deprecated poorly named, use BehaviourRunner instead
 */
public class Run extends BehaviourRunner {

    public Run(PrintStream out) {
        super(out);
    }

    public Run(PrintStream out, ClassLoader classLoader) {
        super(out, classLoader);
    }
    
    public static void main(String[] args) {
        BehaviourRunner.main(args);
    }
}
