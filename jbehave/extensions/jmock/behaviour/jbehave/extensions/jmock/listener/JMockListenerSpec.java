/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.lang.reflect.Method;
import java.util.List;

import jbehave.framework.CriteriaVerification;
import jbehave.framework.CriteriaVerifier;
import jbehave.framework.Verify;
import jbehave.listener.Listener;

import org.jmock.Mock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JMockListenerSpec {
    public static class BehaviourClassWithPrivateMock {
        public boolean verifyWasCalled = false;
        
        private Mock someMock = new Mock(List.class) {
            public void verify() {
                verifyWasCalled = true;
            }
        };
        
        public void shouldDoSomething() {
            someMock.stubs();
        }
    }

    /** pull out the first criteria method in a spec */
    private Method firstCriteria(Class spec) throws Exception {
        Method[] methods = spec.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
      throw new Error("No spec method found in " + spec.getName());
    }

    private CriteriaVerifier getSingleBehaviour(Class behaviourClass) throws Exception {
        return new CriteriaVerifier(firstCriteria(behaviourClass));
    }
    
	public void shouldVerifyPublicMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        // setup
        Listener listener = new JMockListener();
        CriteriaVerifier behaviour = getSingleBehaviour(BehaviourClassWithPrivateMock.class);
        CriteriaVerification behaviourResult = behaviour.verifyCriteria(Listener.NULL);
        
        // execute
        listener.criteriaVerificationEnding(behaviourResult);
        
        // verify
        BehaviourClassWithPrivateMock instance = (BehaviourClassWithPrivateMock)behaviourResult.getSpecInstance();
        Verify.that(instance.verifyWasCalled);
	}
}
