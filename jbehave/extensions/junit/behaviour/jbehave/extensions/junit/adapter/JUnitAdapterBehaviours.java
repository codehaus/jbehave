/*
 * Created on 15-Jan-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.extensions.junit.adapter;

import jbehave.framework.Verify;
import junit.framework.Test;
import junit.framework.TestResult;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:joe@jbehave.org">Joe Walnes</a>
 */
public class JUnitAdapterBehaviours {

    // behaviour class with 3 behaviours
    public static class BehaviourClassWithTwoBehaviours {
        public void shouldDoThis() {
        }

        public void shouldAlsoDoThis() {
        }
    }

    public void shouldReturnCorrectNumberOfBehaviours() throws Exception {
        // setup
        Test adapter = new JUnitAdapter(BehaviourClassWithTwoBehaviours.class);

        // verify
        Verify.equal(2, adapter.countTestCases());
    }

    private static class TestResultMock extends TestResult {
        private Test startedTest = null;
        private Test endedTest = null;

        public void startTest(Test test) {
            super.startTest(test);
            this.startedTest = test;
        }

        public void endTest(Test test) {
            super.endTest(test);
            this.endedTest = test;
        }

        public void verify() {
            Verify.notNull(startedTest);
            Verify.notNull(endedTest);
        }


    }

    public void shouldStartAndStopEachTest() throws Exception {
        // setup
        Test adapter = new JUnitAdapter(BehaviourClassWithTwoBehaviours.class);
        TestResultMock testResult = new TestResultMock();

        // execute
        adapter.run(testResult);

        // verify
        testResult.verify();
    }

    public static class FailingBehaviourClass {
        public void shouldFail() {
            Verify.impossible("Aarg");
        }
    }

    public void shouldRecogniseVerificationException() throws Exception {
        // setup
        Test adapter = new JUnitAdapter(FailingBehaviourClass.class);
        TestResult testResult = new TestResult();

        // execute
        adapter.run(testResult);

        // verify
        Verify.equal(1, testResult.errorCount());
    }
    
    public static class ExceptionThrowingBehaviourClass {
         public void shouldThrowException() throws Exception {
            throw new RuntimeException();
        }
    }

    public void shouldRecogniseExceptionThrown() throws Exception {
        // setup
        Test adapter = new JUnitAdapter(ExceptionThrowingBehaviourClass.class);
        TestResult testResult = new TestResult();

        // execute
        adapter.run(testResult);

        // verify
        Verify.equal(1, testResult.errorCount());

    }
}
