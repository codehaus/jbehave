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
import jbehave.framework.Evaluation;
import jbehave.runner.listener.ListenerSupport;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class TextListener extends ListenerSupport {
    
    private final PrintWriter out;
    private int behavioursRun = 0;
    private final List failures = new ArrayList();
    private final List exceptionsThrown = new ArrayList();

    public TextListener(Writer writer) {
        out = new PrintWriter(writer);
    }

    /**
     * Write out the traditional dot, E or F as each behaviour runs.
     */
    public void afterCriterionEvaluationEnds(Evaluation evaluation) {
        behavioursRun++;
        if (evaluation.failed()) {
            failures.add(evaluation);
        }
        else if (evaluation.exceptionThrown()) {
            exceptionsThrown.add(evaluation);
        }
        out.print(getSymbol(evaluation.getStatus()));
        out.flush();
    }

    private char getSymbol(int status) {
        switch (status) {
            case Evaluation.SUCCESS:          return '.';
            case Evaluation.FAILURE:          return 'F';
            case Evaluation.EXCEPTION_THROWN: return 'E';
            default: throw new BehaviourFrameworkError("Unknown behaviour status: " + status);
        }
    }
    
    public void runEnded(SpecificationRunner runner) {
        out.println(); out.println();
        printFailures();
        printExceptionsThrown();
        printSummaryCounts();
        out.flush();
    }
    
    private void printSummaryCounts() {
        out.print("Criteria evaluated: " + behavioursRun);
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
            Evaluation result = (Evaluation)i.next();
            out.println(count + ") " + result.getName() + " [" + result.getClassName() + "]:");
            result.getTargetException().printStackTrace(out);
            out.println();
        }
    }
}
