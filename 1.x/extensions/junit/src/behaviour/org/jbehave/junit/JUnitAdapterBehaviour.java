/*
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.junit;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestResult;

import org.jbehave.core.Ensure;
import org.jbehave.core.behaviour.Behaviours;
import org.jbehave.core.exception.JBehaveFrameworkError;
import org.jbehave.core.mock.UsingMatchers;
import org.jbehave.junit.JUnitAdapter.BehavioursAdapter;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class JUnitAdapterBehaviour extends UsingMatchers {
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
        JUnitAdapter.setBehaviours(new BehavioursAdapter(HasSingleMethod.class));
        Test suite = JUnitAdapter.suite();
        
        // execute
        int testCaseCount = suite.countTestCases();
        
        // verify
        ensureThat(testCaseCount, eq(1));
    }
    
    public static class HasTwoMethods {
        public void shouldDoSomething() throws Exception {
        }
        
        public void shouldDoSomethingElse() throws Exception {
        }
    }
    
    public void shouldCountMultipleBehaviourMethodsAsTests() throws Exception {
        // setup
        JUnitAdapter.setBehaviours(new BehavioursAdapter(HasTwoMethods.class));
        Test suite = JUnitAdapter.suite();
        // execute
        int testCaseCount = suite.countTestCases();
        
        // verify
        ensureThat(testCaseCount, eq(2));
    }
    
    public static class HasFailingMethod {
        public void shouldDoSomething() throws Exception {
            Ensure.impossible("should not be invoked");
        }
    }
    
    public void shouldCountMultipleBehaviourMethodsAsTestsRecursively() {
        // setup
        JUnitAdapter.setBehaviours (new BehavioursWithOneBehavioursClass());
        Test suite = JUnitAdapter.suite();
        // execute
        int testCaseCount = suite.countTestCases();
       
        // verify
        ensureThat(testCaseCount, eq(3));
    }
   
    public static class BehavioursWithOneBehavioursClass implements Behaviours {
        public Class[] getBehaviours() {   
            return new Class[] {
                new AnotherBehavioursWithOneBehavioursClass().getClass()
            };
        }
    }

    public static class AnotherBehavioursWithOneBehavioursClass implements Behaviours {
        public Class[] getBehaviours() {   
            return new Class[] {
                new BehavioursWithTwoBehaviourClasses().getClass()
            };
        }
    }
   
    public static class BehavioursWithTwoBehaviourClasses implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[] {
                HasTwoMethods.class,
                HasSingleMethod.class
            };
        }
    }    

    public void shouldNotExecuteBehaviourMethodsWhileCountingThem() throws Exception {
        // setup
        JUnitAdapter.setBehaviours(new BehavioursAdapter(HasFailingMethod.class));
        Test suite = JUnitAdapter.suite();
        
        // execute
        suite.countTestCases();
    }
    
    public static class SomeBehaviourClass {
        public void shouldDoSomething() throws Exception {
            sequenceOfEvents.add("shouldDoSomething");
        }
        public void shouldDoSomethingElse() throws Exception {
            sequenceOfEvents.add("shouldDoSomethingElse");
        }
    }
    
    public void shouldExecuteBehavioursInjected() throws Exception {
        // setup
        JUnitAdapter.setBehaviours(new BehavioursAdapter(SomeBehaviourClass.class));
        Test suite = JUnitAdapter.suite();
        
        assertEventsAreInvoked(suite);
    }


    public void shouldExecuteBehavioursLoadedFromBehavioursClassProperty() throws Exception {
        // setup
        JUnitAdapter.setBehaviours(null);
        JUnitAdapter.setClassLoader(new BehavioursClassLoader("behavioursClass="+SomeBehaviours.class.getName()));
        Test suite = JUnitAdapter.suite();

        assertEventsAreInvoked(suite);
    }    

    public void shouldExecuteBehavioursLoadedFromBehaviourClassProperty() throws Exception {
        // setup
        JUnitAdapter.setBehaviours(null);
        JUnitAdapter.setClassLoader(new BehavioursClassLoader("behaviourClass="+SomeBehaviourClass.class.getName()));
        Test suite = JUnitAdapter.suite();

        assertEventsAreInvoked(suite);
    }    

    public void shouldThrowErrorIfBehavioursAreNotFound() throws Exception {
        // setup
        JUnitAdapter.setBehaviours(null);
        JUnitAdapter.setClassLoader(new BehavioursClassLoader(""));
        try {
            JUnitAdapter.suite();
        } catch ( JBehaveFrameworkError e) {
            // expected
        }        
    }    

    
    private void assertEventsAreInvoked(Test suite) {
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
        List expectedSequenceOfEvents = Arrays.asList(new String[] {
                "startTest", "shouldDoSomething", "endTest",
                "startTest", "shouldDoSomethingElse", "endTest"
        });
        Ensure.that(testResult.wasSuccessful());
        for ( Iterator i = expectedSequenceOfEvents.iterator(); i.hasNext(); ){
            String event = (String)i.next();
            ensureThat(event, isContainedIn(sequenceOfEvents));            
        }
    }

    
    public static class SomeBehaviours implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[]{SomeBehaviourClass.class};
        }        
    }
    
    static class BehavioursClassLoader extends ClassLoader {
        private String property;
        
        public BehavioursClassLoader(String property) {
            this.property = property;
        }

        public InputStream getResourceAsStream(String name){
            return new ReaderInputStream(new StringReader(property));
        }
    }
    
    static class ReaderInputStream extends InputStream {
        private Reader reader;

        public ReaderInputStream(Reader isr) {
            this.reader = isr;
        }

        public int read() throws IOException {
            return reader.read();
        }
    }

}
