/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock.listener;

import java.lang.reflect.Method;
import java.util.List;

import org.jmock.core.matcher.TestFailureMatcher;

import junit.framework.AssertionFailedError;

import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.verify.Result;
import com.thoughtworks.jbehave.core.verify.Verify;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListenerBehaviour {
	private MethodListener listener;

	public void setUp() {
		listener = new JMockListener();
	}

    /** pull out the first behaviour method in a spec */
    private Method firstMethod(Class behaviourClass) throws Exception {
        Method[] methods = behaviourClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                return method;
            }
        }
      throw new Error("No behaviour method found in " + behaviourClass.getName());
    }

    public static class BehaviourClass1 extends UsingJMock {
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

    public void shouldVerifyPublicMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        // setup
		BehaviourClass1 instance = new BehaviourClass1();
        Result result = new Result("shouldDoSomething", instance.getClass().getName());

        // execute
        listener.methodVerificationEnding(result, instance);
        
        // verify
        Verify.that(instance.verifyWasCalled);
	}

    public static class BehaviourClass2 extends UsingJMock {
        public boolean verifyWasCalled = false;

        private Mock someMock = new Mock(List.class) {
            public void verify() {
                throw new VerificationException("blah was not invoked");
            }
        };

        public void shouldDoSomething() {
            someMock.stubs();
        }
    }

	public void shouldCreateNewVerificationWhenMethodSucceedsButVerifyFails() throws Exception {
		// setup
        BehaviourClass2 instance = new BehaviourClass2();
        Result result = new Result("SomeBehaviourClass", "shouldDoSomething");

        // execute
        Result verifyMockResult = listener.methodVerificationEnding(result, instance);

		// verify
		Verify.notNull(verifyMockResult);
		Verify.not(verifyMockResult == result);
	}

	interface Foo {
		String someMethod();
	}

	public static class BehaviourClass3 extends UsingJMock {

		public void shouldUseAMockWhoseExpectationWillFail() throws Exception {
	        Mock foo = new Mock(Foo.class);
			foo.expects(once()).method("someMethod").withNoArguments()
                .will(returnValue("hello"));
		}
	}

	public void shouldVerifyMocks() throws Exception {
        // setup
        final Method method = firstMethod(BehaviourClass3.class);
        listener.methodVerificationStarting(method);
        final BehaviourClass3 instance = new BehaviourClass3();
        instance.shouldUseAMockWhoseExpectationWillFail();
        
		// execute
		Result result =
            listener.methodVerificationEnding(
                    new Result(BehaviourClass3.class.getName(), "shouldUseAMockWhoseExpectationWillFail"),
                    instance);
        
		// verify
		Verify.that("should fail JMock verification", result.failed());
	}
    
    public void shouldWrapAssertionFailedErrorWithVerificationException() throws Exception {
        // given...
        Result assertionFailed = new Result("SomeClass", "someMethod", new AssertionFailedError());
        
        // when...
        Result result = listener.methodVerificationEnding(assertionFailed, null);
        
        // verify...
        Verify.instanceOf(VerificationException.class, result.getCause());
    }
    
    public void shouldNotVerifyMocksIfMethodFailed() throws Exception {
        // given...
        RuntimeException cause = new VerificationException("oops");
        Result methodFailed = new Result("SomeClass", "someMethod", cause);
        org.jmock.cglib.Mock instance = new org.jmock.cglib.Mock(UsingJMock.class);

        // expect...
        TestFailureMatcher never = new TestFailureMatcher("expect not called");
        instance.expects(never).method("verify");
        
        // when...
        listener.methodVerificationEnding(methodFailed, instance.proxy());
        
        // verify...
        instance.verify();
    }
    
    public void shouldNotVerifyMocksIfMethodThrewException() throws Exception {
        // given...
        RuntimeException cause = new RuntimeException("oops");
        Result methodFailed = new Result("SomeClass", "someMethod", cause);
        org.jmock.cglib.Mock instance = new org.jmock.cglib.Mock(UsingJMock.class);

        // expect...
        TestFailureMatcher never = new TestFailureMatcher("expect not called");
        instance.expects(never).method("verify");
        
        // when...
        listener.methodVerificationEnding(methodFailed, instance.proxy());
        
        // verify...
        instance.verify();
    }
}
