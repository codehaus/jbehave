/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.invokers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.MethodInvoker;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.invokers.InvokeMethodWithSetUpAndTearDown;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class InvokeMethodWithSetUpAndTearDownBehaviour {
    private MethodInvoker verifier;
    
    public void setUp() {
        verifier = new InvokeMethodWithSetUpAndTearDown();
    }
    
    public static class StoresInvocation {
        public boolean methodWasInvoked = false;
        public void shouldDoSomething() {
            methodWasInvoked = true;
        }
    }
    public void shouldInvokeMethod() throws Throwable {
        // given
        StoresInvocation instance = new StoresInvocation();
        Method method = StoresInvocation.class.getMethod("shouldDoSomething", new Class[0]);
        BehaviourMethod verifiableMethod = new BehaviourMethod(instance, method);
        
        // when
        verifier.invoke(verifiableMethod);
        
        // then
        Verify.that(instance.methodWasInvoked);
    }
    
    public static class HasSetUpAndTearDown {
        public final List whatHappened = new ArrayList();
        
        public void setUp() throws Exception {
            whatHappened.add("setUp");
        }
        public void tearDown() throws Exception {
            whatHappened.add("tearDown");
        }
        public void shouldDoSomething() throws Exception {
            whatHappened.add("shouldDoSomething");
        }
    }
    
    public void shouldInvokeSetUpAndTearDownInTheCorrectSequence() throws Throwable {
        // given
        HasSetUpAndTearDown instance = new HasSetUpAndTearDown();
        Method method = HasSetUpAndTearDown.class.getMethod("shouldDoSomething", new Class[0]);
        BehaviourMethod verifiableMethod = new BehaviourMethod(instance, method);
        
        // expect
        List expected = Arrays.asList(new String[] {
                "setUp", "shouldDoSomething", "tearDown"
        });
        
        // when
        verifier.invoke(verifiableMethod);
        
        // then
        Verify.equal(expected, instance.whatHappened);
    }

    public static class CheckedException extends Exception {}
    
    public static class SetUpThrowsException extends HasSetUpAndTearDown {
        public void setUp() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromSetUp() throws Throwable {
        // given
        Object instance = new SetUpThrowsException();
        Method method = SetUpThrowsException.class.getMethod("shouldDoSomething", new Class[0]);
        BehaviourMethod verifiableMethod = new BehaviourMethod(instance, method);
        
        // when
        Result result = verifier.invoke(verifiableMethod);
        
        // then
        Verify.instanceOf(CheckedException.class, result.cause());
    }
    
    public static class MethodThrowsException extends HasSetUpAndTearDown {
        public void shouldDoSomething() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromMethod() throws Throwable {
        // given
        Object instance = new MethodThrowsException();
        Method method = MethodThrowsException.class.getMethod("shouldDoSomething", new Class[0]);
        BehaviourMethod verifiableMethod = new BehaviourMethod(instance, method);
        
        // when
        Result result = verifier.invoke(verifiableMethod);
        
        // then
        Verify.instanceOf(CheckedException.class, result.cause());
    }
    
    public static class TearDownThrowsException extends HasSetUpAndTearDown {
        public void tearDown() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromTearDown() throws Throwable {
        // given
        Object instance = new TearDownThrowsException();
        Method method = TearDownThrowsException.class.getMethod("shouldDoSomething", new Class[0]);
        BehaviourMethod verifiableMethod = new BehaviourMethod(instance, method);
        
        // when
        Result result = verifier.invoke(verifiableMethod);
        
        // then
        Verify.instanceOf(CheckedException.class, result.cause());
    }
    
    public static class MethodAndTearDownBothThrowException extends HasSetUpAndTearDown {
        public void tearDown() throws Exception {
            throw new Exception("from tearDown");
        }
        public void shouldDoSomething() throws Exception {
            throw new Exception("from method");
        }
    }
    
    public void shouldReportExceptionFromMethodIfMethodAndTearDownBothThrowException() throws Throwable {
        // given
        Object instance = new MethodAndTearDownBothThrowException();
        Method method = MethodAndTearDownBothThrowException.class.getMethod("shouldDoSomething", new Class[0]);
        BehaviourMethod verifiableMethod = new BehaviourMethod(instance, method);
        
        // when
        Result result = verifier.invoke(verifiableMethod);
        
        // then
        Verify.equal("from method", result.cause().getMessage());
    }
}
