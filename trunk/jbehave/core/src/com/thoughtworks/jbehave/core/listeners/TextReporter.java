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

import com.thoughtworks.jbehave.core.BehaviourClass;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.util.Timer;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextReporter implements Visitor {
    /** Stores something interesting to report */
    private static class Note {
        public final Result result;
        public final BehaviourClass behaviourClass;
        public Note(Result result, BehaviourClass behaviourClass) {
            this.result = result;
            this.behaviourClass = behaviourClass;
        }
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
    private Visitable outermost = null;
    private BehaviourClass currentClass = null;

    public TextReporter(Writer writer, Timer timer) {
        out = new PrintWriter(writer);
        this.timer = timer;
        timer.start();
    }

    public TextReporter(Writer writer) {
        this(writer, new Timer());
    }

    public void before(Visitable visitable) {
        if (outermost == null) {
            outermost = visitable;
        }
        
        if (visitable instanceof BehaviourClass) {
            currentClass = (BehaviourClass) visitable;
        }
    }

    public void after(Visitable visitable) {
        if (visitable == outermost) {
            printReport();
        }
    }

    public void gotResult(Result result) {
        methodsVerified++;
        if (result.failed()) {
            failures.add(new Note(result, currentClass));
        }
        else if (result.threwException()) {
            exceptionsThrown.add(new Note(result, currentClass));
        }
        else if (result.isPending()) {
            pending.add(new Note(result, currentClass));
        }
        out.print(result.status().symbol());
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
                note.result.cause().printStackTrace(out);
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
                out.println("\t" + note.result.cause().getMessage());
            }
        }
    }
    
    private void printNote(int count, Note note) {
        String fullName = note.behaviourClass.classToVerify().getName();
        String shortName = fullName.substring(fullName.lastIndexOf('.') + 1);
        shortName = fullName.substring(fullName.lastIndexOf('$') + 1);
        if (shortName.endsWith("Behaviour")) {
            shortName = shortName.substring(0, shortName.length() - 9);
        }
        out.println(count + ") " + shortName + " " + note.result.name() + " [" + fullName + "]:");
    }
}
