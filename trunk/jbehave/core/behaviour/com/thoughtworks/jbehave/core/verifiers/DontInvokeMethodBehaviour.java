/*
 * Created on 01-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verifiers;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Verifiable;
import com.thoughtworks.jbehave.core.Verifier;
import com.thoughtworks.jbehave.core.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class DontInvokeMethodBehaviour {
    public static class FlagsInvocation {
        public boolean methodWasInvoked = false;
        public void shouldDoSomething() {
            methodWasInvoked = true;
        }
    }
    
    public void shouldNotInvokeMethod() throws Throwable {
        // given
        FlagsInvocation instance = new FlagsInvocation();
        Method method = FlagsInvocation.class.getMethod("shouldDoSomething", new Class[0]);
        Verifier verifier = new DontInvokeMethod();
        Verifiable visitableMethod = new BehaviourMethod(instance, method, null);
        
        // when
        verifier.verify(visitableMethod);
        
        // then
        Verify.not(instance.methodWasInvoked);
    }
}
