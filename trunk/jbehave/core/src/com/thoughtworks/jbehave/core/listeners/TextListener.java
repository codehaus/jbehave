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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.BehaviourClassListener;
import com.thoughtworks.jbehave.core.ResponsibilityListener;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.core.responsibility.Result;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListener implements BehaviourClassListener, ResponsibilityListener {
    public static final String SUCCESS = ".";
    public static final String FAILURE = "F";
    public static final String EXCEPTION_THROWN = "E";
    public static final String PENDING = "P";

    private final PrintWriter out;
    private int responsibilitiesVerified = 0;
    private final List failures = new ArrayList();
    private final List exceptionsThrown = new ArrayList();
    private final List pending = new ArrayList();
    private final Timer timer;
    private Class outermostBehaviourClass = null;

    public TextListener(Writer writer, Timer timer) {
        out = new PrintWriter(writer);
        this.timer = timer;
    }

    public TextListener(Writer writer) {
        this(writer, new Timer());
    }
    
    public void behaviourClassVerificationStarting(Class behaviourClass) {
        if (outermostBehaviourClass == null) {
            outermostBehaviourClass = behaviourClass;
            timer.start();
        }
    }
    
    public void behaviourClassVerificationEnding(Class behaviourClass) {
        if (behaviourClass.equals(outermostBehaviourClass)) {
            timer.stop();
            out.println();
            printElapsedTime();
            printFailures();
            printExceptionsThrown();
            printPending();
            printSummaryCounts();
            out.flush();
        }
    }

    private void printElapsedTime() {
        out.println("Time: " + timer.elapsedTimeMillis()/1000.0 + "s\n");
    }

    private void printSummaryCounts() {
        out.print("Responsibilities: " + responsibilitiesVerified + ".");
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
                Result verification = (Result)i.next();
                out.println(count + ") " + verification.getName() + " [" + verification.getBehaviourClassName() + "]:");
                verification.getCause().printStackTrace(out);
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
                Result verification = (Result) i.next();
                out.println(count + ")" + verification.getName() + " [" + verification.getBehaviourClassName() + "]:");
                out.println("\t" + verification.getCause().getMessage());
            }
        }
    }

    public void responsibilityVerificationStarting(Method responsibilityMethod) {
    }
    
    /**
     * Write out the traditional dot, E or F as each behaviour runs.
     */
    public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
        responsibilitiesVerified++;
        if (result.failed()) {
            failures.add(result);
        }
        else if (result.threwException()) {
            exceptionsThrown.add(result);
        }
        else if (result.isPending()) {
            pending.add(result);
        }
        out.print(getSymbol(result.getStatus()));
//        out.flush();
		return result;
    }

    private String getSymbol(int status) {
        switch (status) {
            case Result.SUCCESS:          return SUCCESS;
            case Result.FAILURE:          return FAILURE;
            case Result.EXCEPTION_THROWN: return EXCEPTION_THROWN;
            case Result.PENDING:          return PENDING;
            default: throw new JBehaveFrameworkError("Unknown verification status: " + status);
        }
    }
}
