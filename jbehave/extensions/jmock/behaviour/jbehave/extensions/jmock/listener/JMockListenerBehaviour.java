/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.util.List;

import jbehave.framework.Behaviour;
import jbehave.framework.BehaviourResult;
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
    
    private Behaviour getSingleBehaviour(Class behaviourClass) {
        return (Behaviour)BehavioursSupport.getBehaviours(behaviourClass).iterator().next();
    }
    
	public void shouldVerifyPublicMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        // setup
        Listener listener = new JMockListener();
        Behaviour behaviour = getSingleBehaviour(BehaviourClassWithPrivateMock.class);
        BehaviourResult behaviourResult = behaviour.run();
        
        // execute
        listener.behaviourEnded(behaviourResult);
        
        // verify
        BehaviourClassWithPrivateMock instance = (BehaviourClassWithPrivateMock)behaviourResult.getExecutedInstance();
        Verify.that(instance.verifyWasCalled);
	}
}
