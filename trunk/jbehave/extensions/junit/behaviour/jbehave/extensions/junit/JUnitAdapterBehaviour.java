/*
 * Created on 11-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.junit;

import java.util.ArrayList;
import java.util.List;

import jbehave.core.responsibility.Verify;
import junit.framework.Test;
import junit.framework.TestResult;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JUnitAdapterBehaviour {
    private static final List sequenceOfEvents = new ArrayList();
    
    public void setUp() {
        sequenceOfEvents.clear();
    }
    
    public static class SomeBehaviour {
        public void shouldDoSomething() throws Exception {
        }
    }
    
    public void shouldCountSingleResponsibilityMethodAsTest() throws Exception {
        // setup
        JUnitAdapter.setBehaviourClass(SomeBehaviour.class);
        Test suite = JUnitAdapter.suite();
        
        // execute
        int testCaseCount = suite.countTestCases();
        
        // verify
        Verify.equal(1, testCaseCount);
    }
    
    public static class SomeBehaviourWithMultipleResponsibilities {
        public void shouldDoSomething() throws Exception {
        }
        
        public void shouldDoSomethingElse() throws Exception {
        }
        
    }
    
    public void shouldCountMultipleResponsibilityMethodsAsTests() throws Exception {
        // setup
        JUnitAdapter.setBehaviourClass(SomeBehaviourWithMultipleResponsibilities.class);
        Test suite = JUnitAdapter.suite();
        // execute
        int testCaseCount = suite.countTestCases();
        
        // verify
        Verify.equal(2, testCaseCount);
    }
    
    private static boolean wasCalled = false;
    
    public static class BehaviourClassWithFailingResponsibility {
        public void shouldDoSomething() throws Exception {
            wasCalled = true;
        }
    }
    
    public void shouldNotExecuteResponsibilityMethodsWhileCountingThem() throws Exception {
        // setup
        JUnitAdapter.setBehaviourClass(BehaviourClassWithFailingResponsibility.class);
        Test suite = JUnitAdapter.suite();
        
        // execute
        suite.countTestCases();
        
        // verify
        Verify.not(wasCalled);
    }
    
    public static class BehaviourClass2 {
        public void shouldDoSomething() throws Exception {
            sequenceOfEvents.add("shouldDoSomething");
        }
        public void shouldDoSomethingElse() throws Exception {
            sequenceOfEvents.add("shouldDoSomethingElse");
        }
    }
    
    public void shouldExecuteResponsibilityMethods() throws Exception {
        // setup
        JUnitAdapter.setBehaviourClass(BehaviourClass2.class);
        Test suite = JUnitAdapter.suite();
        TestResult testResult = new TestResult() {
            public void startTest(Test test) {
                sequenceOfEvents.add("startTest");
            }
            public void endTest(Test test) {
                sequenceOfEvents.add("endTest");
            }
        };
        
        // execute
        suite.run(testResult);
        
        // verify
//        Verify.equal(2, testResult.runCount());
//        Verify.equal(0, testResult.errorCount());
//        Verify.equal(0, testResult.failureCount());
//        Verify.that(testResult.wasSuccessful());
//        Verify.equal(Arrays.asList(new String[] {"startTest", "endTest"}), sequenceOfEvents);
    }
}
