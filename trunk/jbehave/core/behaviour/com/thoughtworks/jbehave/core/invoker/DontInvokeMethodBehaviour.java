/*
 * Created on 01-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.invoker;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.behaviour.BehaviourMethod;
import com.thoughtworks.jbehave.core.invoker.DontInvokeMethod;
import com.thoughtworks.jbehave.core.invoker.MethodInvoker;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class DontInvokeMethodBehaviour {
    public static class FailsIfMethodInvoked {
        public void shouldDoSomething() {
            Verify.impossible("Should not be invoked");
        }
    }
    
    public void shouldNotInvokeMethod() throws Throwable {
        // given
        FailsIfMethodInvoked instance = new FailsIfMethodInvoked();
        Method method = FailsIfMethodInvoked.class.getMethod("shouldDoSomething", new Class[0]);
        MethodInvoker invoker = new DontInvokeMethod();
        BehaviourMethod behaviourMethod = new BehaviourMethod(instance, method);
        
        // when
        invoker.invoke(behaviourMethod);
    }
}
