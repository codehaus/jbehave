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

import com.thoughtworks.jbehave.core.BehaviourMethodResult;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.ResultListener;
import com.thoughtworks.jbehave.util.Timer;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListener implements ResultListener {
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

    public TextListener(Writer writer, Timer timer) {
        out = new PrintWriter(writer);
        this.timer = timer;
        timer.start();
    }
    
    public TextListener(Writer writer) {
        this(writer, new Timer());
    }
    
    public void gotResult(Result result) {
        methodsVerified++;
        if (result.failed()) {
            failures.add(result);
        }
        else if (result.threwException()) {
            exceptionsThrown.add(result);
        }
        else if (result.isPending()) {
            pending.add(result);
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
                BehaviourMethodResult result = (BehaviourMethodResult) i.next();
                printResult(count, result);
                result.cause().printStackTrace(out);
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
                BehaviourMethodResult result =  (BehaviourMethodResult) i.next();
                printResult(count, result);
                out.println("\t" + result.cause().getMessage());
            }
        }
    }
    
    private void printResult(int count, BehaviourMethodResult result) {
        String fullName = result.behaviourMethod().instance().getClass().getName();
        String shortName = fullName.substring(fullName.lastIndexOf('.') + 1);
        shortName = fullName.substring(fullName.lastIndexOf('$') + 1);
        if (shortName.endsWith("Behaviour")) {
            shortName = shortName.substring(0, shortName.length() - 9);
        }
        out.println(count + ") " + shortName + " " + result.name() + " [" + fullName + "]:");
    }
}
