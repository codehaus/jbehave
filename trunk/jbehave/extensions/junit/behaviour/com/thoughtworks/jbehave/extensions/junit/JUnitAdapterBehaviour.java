/*
 * Created on 11-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestResult;

import com.thoughtworks.jbehave.core.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JUnitAdapterBehaviour {
    private static final List sequenceOfEvents = new ArrayList();
    
    public void setUp() {
        sequenceOfEvents.clear();
    }
    
    public static class HasSingleMethod {
        public void shouldDoSomething() throws Exception {
        }
    }
    
    public void shouldCountSingleBehaviourMethodAsTest() throws Exception {
        // setup
        JUnitAdapter.setBehaviourClass(HasSingleMethod.class);
        Test suite = JUnitAdapter.suite();
        
        // execute
        int testCaseCount = suite.countTestCases();
        
        // verify
        Verify.equal(1, testCaseCount);
    }
    
    public static class HasTwoMethods {
        public void shouldDoSomething() throws Exception {
        }
        
        public void shouldDoSomethingElse() throws Exception {
        }
        
    }
    
    public void shouldCountMultipleBehaviourMethodsAsTests() throws Exception {
        // setup
        JUnitAdapter.setBehaviourClass(HasTwoMethods.class);
        Test suite = JUnitAdapter.suite();
        // execute
        int testCaseCount = suite.countTestCases();
        
        // verify
        Verify.equal(2, testCaseCount);
    }
    
    private static boolean wasCalled = false;
    
    public static class HasFailingMethod {
        public void shouldDoSomething() throws Exception {
            wasCalled = true;
        }
    }
    
    public void shouldNotExecuteBehaviourMethodsWhileCountingThem() throws Exception {
        // setup
        JUnitAdapter.setBehaviourClass(HasFailingMethod.class);
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
    
    public void shouldExecuteBehaviourMethods() throws Exception {
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
        Verify.that(testResult.wasSuccessful());
        Verify.equal(Arrays.asList(new String[] {
                "startTest", "shouldDoSomething", "endTest",
                "startTest", "shouldDoSomethingElse", "endTest"
        }), sequenceOfEvents);
    }
}
