/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.junit.listener;

import java.lang.reflect.Method;

import jbehave.extensions.junit.JUnitMethodAdapter;
import jbehave.listeners.ListenerSupport;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TestSuitePopulaterListener extends ListenerSupport {
    private final TestSuite[] suiteRef;
    private TestSuite currentSuite = null;

    public TestSuitePopulaterListener(TestSuite[] suiteRef) {
        this.suiteRef = suiteRef;
    }

    public void behaviourClassVerificationStarting(Class behaviourClass) {
        currentSuite = new TestSuite(behaviourClass.getName());
        if (suiteRef[0] == null) {
            suiteRef[0] = currentSuite;
        }
        else {
            suiteRef[0].addTest(currentSuite);
        }
    }
    
    public void responsibilityVerificationStarting(Method responsibilityMethod) {
        currentSuite.addTest(new JUnitMethodAdapter(responsibilityMethod));
    }
}
