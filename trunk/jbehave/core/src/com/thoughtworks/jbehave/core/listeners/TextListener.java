/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourClass;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.verify.Result;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListener implements BehaviourListener {
    /** Stores something interesting to report */
    private static class Note {
        public final Result result;
        public final Class behaviourClass;
        public Note(Result result, Class behaviourClass) {
            this.result = result;
            this.behaviourClass = behaviourClass;
        }
    }

    public boolean caresAbout(Behaviour behaviour) {
        return behaviour instanceof BehaviourClass || behaviour instanceof BehaviourMethod;
    }
    
    public static final String SUCCESS = ".";
    public static final String FAILURE = "F";
    public static final String EXCEPTION_THROWN = "E";
    public static final String PENDING = "P";

    private final PrintWriter out;
    private int methodsVerified = 0;
    private final List failures = new ArrayList();
    private final List exceptionsThrown = new ArrayList();
    private final List pending = new ArrayList();
    private final Timer timer;
    private Class outermostBehaviourClass = null;

    public TextListener(Writer writer, Timer timer) {
        out = new PrintWriter(writer);
        this.timer = timer;
        timer.start();
    }

    public TextListener(Writer writer) {
        this(writer, new Timer());
    }
    
    public void behaviourClassVerificationStarting(Class behaviourClass) {
        if (outermostBehaviourClass == null) {
            outermostBehaviourClass = behaviourClass;
        }
    }
    
    public void behaviourClassVerificationEnding(Class behaviourClass) {
        if (behaviourClass.equals(outermostBehaviourClass)) {
            printReport();
        }
    }

    public void printReport() {
        timer.stop();
        out.println();
        printElapsedTime();
        printFailures();
        printExceptionsThrown();
        printPending();
        printSummaryCounts();
        out.flush();
    }

    private void printElapsedTime() {
        out.println("Time: " + timer.elapsedTimeMillis()/1000.0 + "s\n");
    }

    private void printSummaryCounts() {
        out.print("Methods: " + methodsVerified + ".");
        if (failures.size() + exceptionsThrown.size() > 0) {
            out.print(" Failures: " + failures.size() + ", Exceptions: " + exceptionsThrown.size() + ".");
        }
        out.println();
    }
    
    private void printFailures() {
        printErrorList("Failures:", failures);
    }
    
    private void printExceptionsThrown() {
        printErrorList("Exceptions Thrown:", exceptionsThrown);
    }
    
    private void printErrorList(String title, List errorList) {
        if (!errorList.isEmpty()) {
            out.println(title);
            out.println();
            int count = 1;
            for (Iterator i = errorList.iterator(); i.hasNext(); count++) {
                Note note = (Note)i.next();
                printNote(count, note);
                note.result.getCause().printStackTrace(out);
                out.println();
            }
        }
    }
    
    private void printPending() {
        if (!pending.isEmpty()) {
            out.println("Pending: " + pending.size());
            out.println();
            int count = 1;
            for (Iterator i = pending.iterator(); i.hasNext(); count++) {
                Note note = (Note) i.next();
                printNote(count, note);
                out.println("\t" + note.result.getCause().getMessage());
            }
        }
    }
    
    private void printNote(int count, Note note) {
        String behaviourClassName = note.behaviourClass.getName();
        int lastDot = behaviourClassName.lastIndexOf('.');
        String className = behaviourClassName.substring(lastDot + 1);
        int behaviourIndex = className.lastIndexOf("Behaviour");
        if (behaviourIndex > 0) {
            className = className.substring(0, behaviourIndex);
        }
        out.println(count + ") " + className + " " + note.result.getName() + " [" + behaviourClassName + "]:");
    }

    public void behaviourVerificationStarting(Behaviour behaviour) {
    }
    
    public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
        if (behaviour instanceof BehaviourClass) {
            behaviourClassVerificationEnding(((BehaviourClass)behaviour).getClassToVerify());
        }
        else if (behaviour instanceof BehaviourMethod) {
            methodVerificationEnding(result, ((BehaviourMethod)behaviour).getInstance().getClass());
        }
        return result;
    }
    
    /**
     * Write out the traditional dot, E or F as each behaviour runs.
     */
    private Result methodVerificationEnding(Result result, Class currentBehaviourClass) {
        methodsVerified++;
        if (result.failed()) {
            failures.add(new Note(result, currentBehaviourClass));
        }
        else if (result.threwException()) {
            exceptionsThrown.add(new Note(result, currentBehaviourClass));
        }
        else if (result.isPending()) {
            pending.add(new Note(result, currentBehaviourClass));
        }
        out.print(result.getStatus().getSymbol());
//        out.flush();
		return result;
    }
}
