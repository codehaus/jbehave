package com.thoughtworks.jbehave.extensions.jmock;

import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.responsibility.Verify;

/**
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 *
 */
public class UsingJMockBehaviour {
    
    interface BehaviourInterface1 {
        void doStuff();
    }
    
    static class BehaviourClass1 extends UsingJMock {
        public void shouldDoSomething() throws Exception {
            Mock m = new Mock(BehaviourInterface1.class);
            m.expectsOnce("doStuff");
        }
        
        public void shouldCallAMethodThatDoesntExist() throws Exception {
            Mock m = new Mock(BehaviourInterface1.class);
            m.expectsOnce("doOtherStuff");
        }
    }
    
    public void shouldVerifyMocks() throws Exception {
        BehaviourClass1 behaviour = new BehaviourClass1();
        behaviour.shouldDoSomething();
        
        try {
            behaviour.verify();
            Verify.impossible("should of thrown Verification Exception");
        } catch(VerificationException e){
            // ok
        }
        
    }
    
    public void shouldThrowVerificationExceptionWhenExpectationSetOnNonExistantMethod() throws Exception {
        BehaviourClass1 behaviour = new BehaviourClass1();
        try {
            behaviour.shouldCallAMethodThatDoesntExist();
            Verify.impossible("Should of thrown VerificationException");
        }catch (VerificationException ve) {
            // ok
        }
    }
    

}
