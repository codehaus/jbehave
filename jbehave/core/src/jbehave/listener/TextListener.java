/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.listener;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.framework.CriteriaVerification;
import jbehave.framework.exception.BehaviourFrameworkError;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListener extends NullListener {

    private final PrintWriter out;
    private int criteriaVerified = 0;
    private final List failures = new ArrayList();
    private final List exceptionsThrown = new ArrayList();
    private final Timer timer;
    private Class outermostSpec = null;

    public TextListener(Writer writer, Timer timer) {
        out = new PrintWriter(writer);
        this.timer = timer;
    }

    public TextListener(Writer writer) {
        this(writer, new Timer());
    }
    
    public void specVerificationStarting(Class spec) {
        if (outermostSpec == null) {
            outermostSpec = spec;
            timer.start();
        }
    }
    
    /**
     * Write out the traditional dot, E or F as each behaviour runs.
     */
    public void criteriaVerificationEnding(CriteriaVerification verification) {
        criteriaVerified++;
        if (verification.failed()) {
            failures.add(verification);
        }
        else if (verification.threwException()) {
            exceptionsThrown.add(verification);
        }
        out.print(getSymbol(verification.getStatus()));
        out.flush();
    }

    private char getSymbol(int status) {
        switch (status) {
            case CriteriaVerification.SUCCESS:          return '.';
            case CriteriaVerification.FAILURE:          return 'F';
            case CriteriaVerification.EXCEPTION_THROWN: return 'E';
            default: throw new BehaviourFrameworkError("Unknown verification status: " + status);
        }
    }
    
    public void specVerificationEnding(Class spec) {
        if (spec.equals(outermostSpec)) {
            timer.stop();
            out.println();
            printElapsedTime();
            printFailures();
            printExceptionsThrown();
            printSummaryCounts();
            out.flush();
        }
    }
    
    private void printElapsedTime() {
        out.println("Time: " + timer.elapsedTimeMillis()/1000.0 + "s\n");
    }

    private void printSummaryCounts() {
        out.print("Criteria: " + criteriaVerified + ".");
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
        if (errorList.isEmpty()) return;
        out.println(title);
        out.println();
        int count = 1;
        for (Iterator i = errorList.iterator(); i.hasNext();) {
            CriteriaVerification verification = (CriteriaVerification)i.next();
            out.println(count + ") " + verification.getName() + " [" + verification.getSpecName() + "]:");
            verification.getTargetException().printStackTrace(out);
            out.println();
        }
    }
}
