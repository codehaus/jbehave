/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 Dan North
 * 
 * See license.txt for licence details
 */
package jbehave.runner;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.BehaviourFrameworkError;
import jbehave.framework.BehaviourResult;
import jbehave.runner.listener.ListenerSupport;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListener extends ListenerSupport {
    
    private final PrintWriter out;
    private int behavioursRun = 0;
//    private int assertionsFailed = 0;
//    private int exceptionsThrown = 0;
    private final List assertionsFailed = new ArrayList();
    private final List exceptionsThrown = new ArrayList();

    public TextListener(Writer writer) {
        out = new PrintWriter(writer);
    }

    /**
     * Write out the traditional dot, E or F as each behaviour runs.
     */
    public void behaviourEnded(BehaviourResult behaviourResult) {
        behavioursRun++;
        if (behaviourResult.failed()) {
            assertionsFailed.add(behaviourResult);
        }
        else if (behaviourResult.exceptionThrown()) {
            exceptionsThrown.add(behaviourResult);
        }
        out.print(getSymbol(behaviourResult.getStatus()));
        out.flush();
    }

    private char getSymbol(int status) {
        switch (status) {
            case BehaviourResult.SUCCESS:          return '.';
            case BehaviourResult.FAILURE:          return 'F';
            case BehaviourResult.EXCEPTION_THROWN: return 'E';
            default: throw new BehaviourFrameworkError("Unknown behaviour status: " + status);
        }
    }
    
    public void runEnded(BehaviourRunner runner) {
        out.println(); out.println();
        printAssertionsFailed();
        printExceptionsThrown();
        printSummaryCounts();
        out.flush();
    }
    
    private void printSummaryCounts() {
        out.print("Behaviours run: " + behavioursRun);
        if (assertionsFailed.size() + exceptionsThrown.size() > 0) {
            out.print(", Assertion Failures: " + assertionsFailed.size() + ", Exceptions Thrown: " + exceptionsThrown.size());
        }
        out.println();
    }
    
    private void printAssertionsFailed() {
        printErrorList("Assertions Failed:", assertionsFailed);
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
            BehaviourResult result = (BehaviourResult)i.next();
            out.println(count + ") " + result.getName() + " [" + result.getClassName() + "]:");
            result.getTargetException().printStackTrace(out);
            out.println();
        }
    }
}
