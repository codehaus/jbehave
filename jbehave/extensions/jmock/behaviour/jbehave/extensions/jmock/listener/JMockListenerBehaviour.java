/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.util.List;

import jbehave.framework.Criterion;
import jbehave.framework.Evaluation;
import jbehave.framework.BehavioursSupport;
import jbehave.framework.Verify;
import jbehave.runner.listener.Listener;

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
    
    private Criterion getSingleBehaviour(Class behaviourClass) {
        return (Criterion)BehavioursSupport.getCriteria(behaviourClass).iterator().next();
    }
    
	public void shouldVerifyPublicMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        // setup
        Listener listener = new JMockListener();
        Criterion behaviour = getSingleBehaviour(BehaviourClassWithPrivateMock.class);
        Evaluation behaviourResult = behaviour.evaluate();
        
        // execute
        listener.afterCriterionEvaluationEnds(behaviourResult);
        
        // verify
        BehaviourClassWithPrivateMock instance = (BehaviourClassWithPrivateMock)behaviourResult.getEvaluatedInstance();
        Verify.that(instance.verifyWasCalled);
	}
}
