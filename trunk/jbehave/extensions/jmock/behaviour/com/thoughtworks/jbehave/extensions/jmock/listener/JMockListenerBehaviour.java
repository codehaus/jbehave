/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock.listener;

import java.util.List;

import junit.framework.AssertionFailedError;

import org.jmock.core.matcher.TestFailureMatcher;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.verify.Result;
import com.thoughtworks.jbehave.core.verify.Verify;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListenerBehaviour {
	private BehaviourListener listener;

	public void setUp() {
		listener = new JMockListener();
	}

    public static class HasMockField extends UsingJMock {
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

    public void shouldVerifyPrivateMockFieldsWhenBehaviourMethodSucceeds() throws Exception {
        // setup
		HasMockField instance = new HasMockField();
        Result result = new Result("name", Result.SUCCEEDED);
        Behaviour behaviour = new BehaviourMethod(null, null, instance);
        listener.behaviourVerificationStarting(behaviour);

        // execute
        listener.behaviourVerificationEnding(result, behaviour);
        
        // verify
        Verify.that(instance.verifyWasCalled);
	}

    public static class HasFailingMock extends UsingJMock {
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
        HasFailingMock instance = new HasFailingMock();
        Result result = new Result("shouldDoSomething", Result.SUCCEEDED);
        Behaviour behaviour = new BehaviourMethod(null, null, instance);

        // execute
        Result verifyMockResult = listener.behaviourVerificationEnding(result, behaviour);

		// verify
		Verify.notNull(verifyMockResult);
		Verify.not(verifyMockResult == result);
	}

	interface Foo {
		String someMethod();
	}

	public static class HasFailingExpectation extends UsingJMock {

		public void shouldUseAMockWhoseExpectationWillFail() throws Exception {
	        Mock foo = new Mock(Foo.class);
			foo.expects(once()).method("someMethod").withNoArguments()
                .will(returnValue("hello"));
		}
	}

	public void shouldVerifyMocks() throws Exception {
        // setup
        final HasFailingExpectation instance = new HasFailingExpectation();
        BehaviourMethod behaviour = new BehaviourMethod(null, null, instance);
        listener.behaviourVerificationStarting(behaviour);
        instance.shouldUseAMockWhoseExpectationWillFail();
        
		// execute
		Result result =
            listener.behaviourVerificationEnding(
                    new Result("shouldUseAMockWhoseExpectationWillFail", Result.SUCCEEDED), behaviour);
        
		// verify
		Verify.that("should fail JMock verification", result.failed());
	}
    
    public static class UsesJMock extends UsingJMock {
    }
    
    public void shouldWrapAssertionFailedErrorWithVerificationExceptionWhenUsingJMock() throws Exception {
        // given...
        Result assertionFailed = new Result("someMethod", new AssertionFailedError());
        Behaviour behaviour = new BehaviourMethod(null, null, new UsesJMock());
        listener.behaviourVerificationStarting(behaviour);
        
        // when...
        Result result = listener.behaviourVerificationEnding(assertionFailed, behaviour);
        
        // verify...
        Verify.instanceOf(VerificationException.class, result.getCause());
    }
    
    public void shouldNotVerifyMocksIfMethodFailed() throws Exception {
        // given...
        org.jmock.cglib.Mock instance = new org.jmock.cglib.Mock(UsingJMock.class);
        Behaviour behaviour = new BehaviourMethod(null, null, instance);
        Result methodFailed = new Result("someMethod", Result.FAILED);
        

        // expect...
        TestFailureMatcher never = new TestFailureMatcher("expect not called");
        instance.expects(never).method("verify");
        
        // when...
        listener.behaviourVerificationEnding(methodFailed, behaviour);
        
        // verify...
        instance.verify();
    }
    
    public void shouldNotVerifyMocksIfMethodThrewException() throws Exception {
        // given...
        org.jmock.cglib.Mock instance = new org.jmock.cglib.Mock(UsingJMock.class);
        Behaviour behaviour = new BehaviourMethod(null, null, instance);
        Result methodFailed = new Result("someMethod", Result.THREW_EXCEPTION);

        // expect...
        TestFailureMatcher never = new TestFailureMatcher("expect not called");
        instance.expects(never).method("verify");
        
        // when...
        listener.behaviourVerificationEnding(methodFailed, behaviour);
        
        // verify...
        instance.verify();
    }
}
