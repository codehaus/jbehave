/*
 * Created on 11-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.junit;

import jbehave.framework.Verify;
import junit.framework.Test;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JUnitAdapterBehaviour {
    public static class SomeBehaviour {
        public void shouldDoSomething() throws Exception {
            
        }
    }
    
    public void shouldCountSingleResponsibilityMethodAsTest() throws Exception {
        // setup
        JUnitAdapter adapter = new JUnitAdapter();
        adapter.setBehaviourClass(SomeBehaviour.class);
        Test test = adapter;
        
        // execute
        int testCaseCount = test.countTestCases();
        
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
        JUnitAdapter adapter = new JUnitAdapter();
        adapter.setBehaviourClass(SomeBehaviourWithMultipleResponsibilities.class);
        Test test = adapter;
        
        // execute
        int testCaseCount = test.countTestCases();
        
        // verify
        Verify.equal(2, testCaseCount);
    }
    
    public static class BehaviourClassWithFailingResponsibility {
        public void shouldDoSomething() throws Exception {
            Verify.impossible("shouldn't be executed");
        }
    }
    
    public void shouldNotExecuteResponsibilityMethodsWhileCountingThem() throws Exception {
        // setup
        JUnitAdapter adapter = new JUnitAdapter();
        adapter.setBehaviourClass(BehaviourClassWithFailingResponsibility.class);
        Test test = adapter;
        
        // execute
        test.countTestCases();
        
        // verify
    }
}
