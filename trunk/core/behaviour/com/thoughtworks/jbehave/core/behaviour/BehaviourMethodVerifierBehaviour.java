/*
 * Created on 25-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.behaviour;

import com.thoughtworks.jbehave.core.invoker.MethodInvoker;
import com.thoughtworks.jbehave.core.minimock.Mock;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodVerifierBehaviour extends BehaviourSupport {
    
    public void shouldUseInvokerOnBehaviourMethods() throws Exception {
        // given...
        Mock invoker = mock(MethodInvoker.class);
        BehaviourMethodVerifier verifier = new BehaviourMethodVerifier((MethodInvoker) invoker);
        BehaviourClass behaviourClass = new BehaviourClass(HasTwoMethods.class);
            
        // expect...
        invoker.expects("invoke").with(matchesBehaviourMethodName("shouldDoSomething"));
        invoker.expects("invoke").with(matchesBehaviourMethodName("shouldDoSomethingElse"));
        
        // when...
        behaviourClass.accept(verifier);
        
        // verify...
        verifyMocks();
    }
}
