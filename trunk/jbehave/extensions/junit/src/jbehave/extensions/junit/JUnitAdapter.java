/*
 * Created on 11-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.junit;

import jbehave.framework.BehaviourClassVerifier;
import jbehave.framework.Listener;
import jbehave.framework.NotifyingResponsibilityVerifier;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.listeners.ListenerSupport;
import junit.framework.Test;
import junit.framework.TestResult;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JUnitAdapter implements Test {

    private Class behaviourClass = null;

    public int countTestCases() {
        final int count[] = new int[1];
        Listener testCaseCounter = new ListenerSupport() {
            public void responsibilityVerificationStarting(ResponsibilityVerifier verifier, Object behaviourClassInstance) {
                System.out.println("Verifying " + verifier);
                count[0]++;
            }
        };
        final BehaviourClassVerifier verifier =
            new BehaviourClassVerifier(behaviourClass, new NotifyingResponsibilityVerifier());
        verifier.verifyBehaviourClass(testCaseCounter);
       return count[0];
    }

    public void run(TestResult arg0) {
    }

    public void setBehaviourClass(Class behaviourClass) {
        this.behaviourClass = behaviourClass;
        
    }

}
