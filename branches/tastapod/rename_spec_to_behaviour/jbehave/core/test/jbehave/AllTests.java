/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;
import jbehave.adapter.JUnitAdapterTest;
import jbehave.framework.BehaviourResultTest;
import jbehave.framework.BehaviourTest;
import jbehave.framework.BehavioursSupportTest;
import jbehave.runner.BehaviourRunnerTest;
import jbehave.runner.listener.TextListenerTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(BehaviourTest.class);
        suite.addTestSuite(BehaviourResultTest.class);
        suite.addTestSuite(BehavioursSupportTest.class);
        suite.addTestSuite(BehaviourRunnerTest.class);
        suite.addTestSuite(TextListenerTest.class);
        suite.addTestSuite(JUnitAdapterTest.class);
        return suite;
    }
}
