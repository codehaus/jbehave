package jbehave.core;

import java.io.PrintStream;
import java.io.PrintWriter;

import jbehave.core.behaviour.BehaviourClass;
import jbehave.core.behaviour.BehaviourVerifier;
import jbehave.core.listener.PlainTextListener;
import jbehave.core.util.Timer;

/**
 * This is the entry point to run one or more {@link BehaviourClass}.
 * Classes are loaded using the ClassLoader injected, which defaults to the current context
 * ClassLoader.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class Run {
    private boolean succeeded = true;
    private final PrintWriter writer;
    private ClassLoader classLoader;

    public Run(PrintStream out) {
        this(out, Thread.currentThread().getContextClassLoader());
    }
    
    public Run(PrintStream out, ClassLoader classLoader) {
        this.writer = new PrintWriter(out);
        this.classLoader = classLoader;
    }

    public boolean succeeded() {
        return succeeded;
    }

    public void verifyBehaviour(String classNameToVerify) throws ClassNotFoundException {
        verifyBehaviour(classLoader.loadClass(classNameToVerify));
    }
    
    public void verifyBehaviour(Class classToVerify) {
        PlainTextListener textListener = new PlainTextListener(new PrintWriter(writer), new Timer());
        BehaviourVerifier verifier = new BehaviourVerifier(textListener);
        verifier.verifyBehaviour(new BehaviourClass(classToVerify, verifier));
        textListener.printReport();
        succeeded = succeeded && !textListener.hasBehaviourFailures();
    }
    
    public static void main(String[] args) {
        try {
            Run run = new Run(System.out);
            for (int i = 0; i < args.length; i++) {
                run.verifyBehaviour(args[i]);
            }
            System.exit(run.succeeded() ? 0 : 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
