/*
 * Created on 15-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.junit.adapter;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerificationResult;
import jbehave.framework.CriteriaExtractor;
import jbehave.framework.VerificationException;
import jbehave.verify.Evaluator;
import jbehave.verify.listener.ListenerSupport;
import junit.framework.Test;
import junit.framework.TestResult;

/**
 * Allows behaviours to be run in any JUnit runner
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:joe@jbehave.org">Joe Walnes</a>
 */
public class JUnitAdapter implements Test {

    private final class JUnitListener extends ListenerSupport {
        private final TestResult result;
        private final Test adapter;
        private JUnitListener(TestResult result, Test adapter) {
            this.result = result;
            this.adapter = adapter;
        }
        public void runStarted(Evaluator runner) {
        }
        public void behaviourEvaluationStarted(Class behaviourClass) {
            setBehaviourClassName(behaviourClass.getName());
            result.startTest(adapter);
        }
        public void beforeCriterionEvaluationStarts(CriteriaVerifier behaviour) {
        }
        public void afterCriterionEvaluationEnds(CriteriaVerificationResult behaviourResult) {
            if (behaviourResult.failed()) {
                VerificationException e = (VerificationException)behaviourResult.getTargetException();
                result.addError(adapter, e);
            }
            else if (behaviourResult.exceptionThrown()) {
                result.addError(adapter, behaviourResult.getTargetException());
            }
            result.endTest(adapter);                 
        }
    }

    private final Class behaviours;
    private String currentBehaviourName;
    
    public JUnitAdapter(Class behaviours) {
        this(behaviours, 1);
    }
    
    public JUnitAdapter(Class behaviours, int depth) {
        this.behaviours = behaviours;
        currentBehaviourName = behaviours.getName();
    }

    public int countTestCases() {
        return CriteriaExtractor.getCriteria(behaviours).size();
    }

    private void setBehaviourClassName(String behaviourClassName) {
        currentBehaviourName = behaviourClassName;
    }

    public void run(final TestResult result) {        
        Evaluator currentRunner = new Evaluator();
        currentRunner.addBehaviourClass(behaviours);
        
        currentRunner.registerListener(
                new JUnitListener(result, this)
        );
        currentRunner.evaluateCriteria();
    }

    public String toString() {
        return currentBehaviourName;
    }
}
