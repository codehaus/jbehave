/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.behaviour;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodBehaviour extends UsingMiniMock {
	
    public void shouldDispatchItselfToVisitor() throws Exception {
        // given...
        Mock visitor = mock(Visitor.class);
        BehaviourMethod behaviourMethod = new BehaviourMethod(null, (Method)null);
        
        // expect...
        visitor.expects("visitBehaviourMethod").with(behaviourMethod);
        
        // when...
        behaviourMethod.accept((Visitor) visitor);
        
        // then...
        verifyMocks();
    }

	private Method method(String methodName, Object instance) throws NoSuchMethodException {
		return instance.getClass().getMethod(methodName, new Class[0]);
	}
    
	private BehaviourMethod behaviourMethod(Object instance, String methodName) throws Exception {
		return new BehaviourMethod(instance, method(methodName, instance));
	}

	private BehaviourMethod behaviourMethod(Class type, String methodName) throws Exception {
		Object instance = type.newInstance();
		return behaviourMethod(instance, methodName);
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
        BehaviourMethod behaviourMethod = new BehaviourMethod(instance, "shouldDoSomething");
        
        // when
        behaviourMethod.invoke();
        
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
        BehaviourMethod behaviourMethod = new BehaviourMethod(instance, "shouldDoSomething");
        
        // expect
        List expected = Arrays.asList(new String[] {
                "setUp", "shouldDoSomething", "tearDown"
        });
        
        // when
        behaviourMethod.invoke();
        
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
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new SetUpThrowsException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
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
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new MethodThrowsException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
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
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new TearDownThrowsException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
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
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new MethodAndTearDownBothThrowException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        Verify.equal("from method", result.cause().getMessage());
    }
}
