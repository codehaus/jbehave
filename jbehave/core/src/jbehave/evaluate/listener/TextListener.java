/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.evaluate.listener;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.BehaviourFrameworkError;
import jbehave.evaluate.Evaluator;
import jbehave.framework.CriteriaVerification;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListener extends ListenerSupport {
    
    private final PrintWriter out;
    private int behavioursRun = 0;
    private final List failures = new ArrayList();
    private final List exceptionsThrown = new ArrayList();
    private final Timer timer;

    public TextListener(Writer writer, Timer timer) {
        out = new PrintWriter(writer);
        this.timer = timer;
    }

    public TextListener(Writer writer) {
        this(writer, new Timer());
    }
    
    public void runStarted(Evaluator runner) {
        timer.start();
    }

    /**
     * Write out the traditional dot, E or F as each behaviour runs.
     */
    public void afterCriterionEvaluationEnds(CriteriaVerification behaviourResult) {
        behavioursRun++;
        if (behaviourResult.failed()) {
            failures.add(behaviourResult);
        }
        else if (behaviourResult.exceptionThrown()) {
            exceptionsThrown.add(behaviourResult);
        }
        out.print(getSymbol(behaviourResult.getStatus()));
        out.flush();
    }

    private char getSymbol(int status) {
        switch (status) {
            case CriteriaVerification.SUCCESS:          return '.';
            case CriteriaVerification.FAILURE: return 'F';
            case CriteriaVerification.EXCEPTION_THROWN: return 'E';
            default: throw new BehaviourFrameworkError("Unknown behaviour status: " + status);
        }
    }
     
    public void runEnded(Evaluator runner) {
        timer.stop();
        out.println();
        printElapsedTime();
        printFailures();
        printExceptionsThrown();
        printSummaryCounts();
        out.flush();
    }
    
    private void printElapsedTime() {
        out.println("Time: " + timer.getElapsedTime()/1000.0 + "s\n");
    }

    private void printSummaryCounts() {
        out.print("Behaviours run: " + behavioursRun);
        if (failures.size() + exceptionsThrown.size() > 0) {
            out.print(", Failures: " + failures.size() + ", Exceptions Thrown: " + exceptionsThrown.size());
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
            CriteriaVerification result = (CriteriaVerification)i.next();
            out.println(count + ") " + result.getName() + " [" + result.getClassName() + "]:");
            result.getTargetException().printStackTrace(out);
            out.println();
        }
    }
}
