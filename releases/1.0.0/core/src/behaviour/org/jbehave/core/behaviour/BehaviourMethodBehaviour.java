/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.behaviour;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.exception.JBehaveFrameworkError;
import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.result.Result;
import org.jbehave.core.result.Result.Type;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodBehaviour extends UsingMiniMock {
	
	public static class StoresInvocation {
        public boolean methodWasInvoked = false;
        public void shouldDoSomething() {
            methodWasInvoked = true;
        }
    }
    
    private BehaviourMethod createBehaviourMethod(Object instance, String methodName) {
        try {
            Method method = instance.getClass().getMethod(methodName, null);
            return new BehaviourMethod(instance, method);
        } catch (Exception e) {
            throw new JBehaveFrameworkError("No method " + methodName + " on class " + instance.getClass().getName());
        }
    }
    
    public void shouldContainExactlyOneBehaviour() throws Exception {
        // given...
        Behaviour behaviour = createBehaviourMethod(new Object(), "toString");

        // then...
        ensureThat(behaviour.countBehaviours(), eq(1));
    }
	
    public void shouldVerifyByInvokingMethod() throws Throwable {
        // given
		StoresInvocation instance = new StoresInvocation();
        BehaviourListener listener = (BehaviourListener) stub(BehaviourListener.class);
        BehaviourMethod behaviourMethod = createBehaviourMethod(instance, "shouldDoSomething");
        
        // when
        behaviourMethod.verifyTo(listener);
        
        // then
        ensureThat(instance.methodWasInvoked);
    }
    
    public static class HasSuccessfulMethod {
        public void shouldWork() {
            // succeeds
        }
    }
    
    private Matcher resultOfType(final Type type) {
        return new Matcher() {
            public boolean matches(Object arg) {
                return ((Result)arg).status() == type;
            }
        };
    }
    
    public void shouldTellListenerWhenVerifySucceeds() throws Exception {
        // given...
        Object instance = new HasSuccessfulMethod();
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = createBehaviourMethod(instance, "shouldWork");
        
        // expect...
        listener.expects("gotResult").with(resultOfType(Result.SUCCEEDED));
        
        // when...
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then...
        verifyMocks();
    }

    public static class HasSetUpAndTearDown {
        public List whatHappened = new ArrayList();
        
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
        BehaviourListener listener = (BehaviourListener) stub(BehaviourListener.class);
        Behaviour behaviour = createBehaviourMethod(instance, "shouldDoSomething");
        
        // expect
        List expected = Arrays.asList(new String[] {
                "setUp", "shouldDoSomething", "tearDown"
        });
        
        // when
        behaviour.verifyTo(listener);
        
        // then
        ensureThat(instance.whatHappened, eq(expected));
    }

    public static class CheckedException extends Exception {
		private static final long serialVersionUID = 1L;
	}

    private CustomMatcher resultContainingCheckedException() {
        return new CustomMatcher("result containing a CheckedException") {
            public boolean matches(Object arg) {
                return isA(CheckedException.class).matches(((Result)arg).cause());
            }
        };
    }
    
    public static class MethodThrowsException {
        public void shouldDoSomething() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromMethod() throws Throwable {
        // given
        Behaviour behaviour = createBehaviourMethod(new MethodThrowsException(), "shouldDoSomething");
        Mock listener = mock(BehaviourListener.class);
        
        // expect
        listener.expects("gotResult").with(resultContainingCheckedException());
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    public static class SetUpThrowsException extends HasSetUpAndTearDown {
        public void setUp() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromSetUp() throws Throwable {
        // given
        Behaviour behaviour = createBehaviourMethod(new SetUpThrowsException(), "shouldDoSomething");
        Mock listener = mock(BehaviourListener.class);
        
        // expect
        listener.expects("gotResult").with(resultContainingCheckedException());
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    public static class TearDownThrowsException extends HasSetUpAndTearDown {
        public void tearDown() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromTearDown() throws Throwable {
        // given
        Behaviour behaviour = createBehaviourMethod(new TearDownThrowsException(), "shouldDoSomething");
        Mock listener = mock(BehaviourListener.class);
        
        // expect
        listener.expects("gotResult").with(resultContainingCheckedException());
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    public static class MethodAndTearDownBothThrowException extends HasSetUpAndTearDown {
        public void tearDown() throws Exception {
            throw new Exception("from tearDown");
        }
        public void shouldDoSomething() throws Exception {
            throw new Exception("from method");
        }
    }
    
    private Matcher resultContainingExceptionMessage(final String message) {
        return new Matcher() {
            public boolean matches(Object arg) {
                return message.equals(((Result)arg).cause().getMessage());
            }
            public String toString() {
                return "result containing CheckedException with message=" + message;
            }
        };
    }
    
    public void shouldReportExceptionFromMethodIfMethodAndTearDownBothThrowException() throws Throwable {
        // given
        Behaviour behaviour = createBehaviourMethod(new MethodAndTearDownBothThrowException(), "shouldDoSomething");
        Mock listener = mock(BehaviourListener.class);
        
        // expect
        listener.expects("gotResult").with(resultContainingExceptionMessage("from method"));
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }    
}
