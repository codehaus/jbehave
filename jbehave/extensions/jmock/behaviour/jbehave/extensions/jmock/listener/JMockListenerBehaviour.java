/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.util.List;

import jbehave.evaluate.listener.Listener;
import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerificationResult;
import jbehave.framework.CriteriaExtractor;
import jbehave.framework.Verify;

import org.jmock.Mock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JMockListenerBehaviour {
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
    
    private CriteriaVerifier getSingleBehaviour(Class behaviourClass) {
        return (CriteriaVerifier)CriteriaExtractor.getCriteria(behaviourClass).iterator().next();
    }
    
	public void shouldVerifyPublicMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        // setup
        Listener listener = new JMockListener();
        CriteriaVerifier behaviour = getSingleBehaviour(BehaviourClassWithPrivateMock.class);
        CriteriaVerificationResult behaviourResult = behaviour.verify();
        
        // execute
        listener.afterCriterionEvaluationEnds(behaviourResult);
        
        // verify
        BehaviourClassWithPrivateMock instance = (BehaviourClassWithPrivateMock)behaviourResult.getBehaviourInstance();
        Verify.that(instance.verifyWasCalled);
	}
}
