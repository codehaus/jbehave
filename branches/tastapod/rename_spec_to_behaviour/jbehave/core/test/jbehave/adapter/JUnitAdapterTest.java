/*
 * Created on 15-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.adapter;

import jbehave.extensions.junit.adapter.JUnitAdapter;
import jbehave.framework.Verify;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:joe@jbehave.org">Joe Walnes</a>
 */
public class JUnitAdapterTest extends TestCase {
    
    // behaviour class with 3 behaviours
    public static class BehaviourClassWithTwoBehaviours {
        public void shouldDoThis() {
        }
        
        public void shouldAlsoDoThis() {
        }
    }
    
    public void testShouldReturnCorrectNumberOfBehaviours() throws Exception {
        // setup
        Test adapter = new JUnitAdapter(BehaviourClassWithTwoBehaviours.class);
        
        // verify
        assertEquals(2, adapter.countTestCases());
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
            assertNotNull(startedTest);
            assertNotNull(endedTest);            
        }
        
        
    }
    
    public void testShouldStartAndStopEachTest() throws Exception {
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
    
    public void testShouldRecogniseAssertionFailure() throws Exception {
        // setup
        Test adapter = new JUnitAdapter(FailingBehaviourClass.class);
        TestResult testResult = new TestResult();
        
        // execute
        adapter.run(testResult);
        
        // verify
        assertEquals(1, testResult.failureCount());
        
    }

}
