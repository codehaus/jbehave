/*
 * Created on 25-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.minimock.Constraint;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodVerifierBehaviour extends BehaviourSupport {
    
    public void shouldUseInvokerOnVisitableMethods() throws Exception {
        // given...
        Mock invoker = new Mock(MethodInvoker.class);
        BehaviourClass behaviourClass = new BehaviourClass(HasTwoMethods.class);
        BehaviourMethodVerifier verifier = new BehaviourMethodVerifier((MethodInvoker) invoker.proxy());
        
        // expect...
        invoker.expectsOnce("invoke").with(new MatchesBehaviourMethodName("shouldDoSomething"));
        invoker.expectsOnce("invoke").with(new MatchesBehaviourMethodName("shouldDoSomethingElse"));
        
        // when...
        behaviourClass.accept(verifier);
        
        // verify...
        verifyMocks();
    }

    class MatchesBehaviourMethodName implements Constraint {
        private final String methodName;

        MatchesBehaviourMethodName(String methodName) {
            this.methodName = methodName;
        }

        public boolean matches(Object arg) {
            return arg instanceof BehaviourMethod && ((BehaviourMethod)arg).method().getName().equals(methodName);
        }
    }}
