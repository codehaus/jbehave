package com.thoughtworks.jbehave.extensions.jmock;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.AssertionFailedError;

import org.jmock.builder.MatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.DynamicMock;
import org.jmock.core.stub.DefaultResultStub;

import com.thoughtworks.jbehave.core.exception.VerificationException;

/**
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 *
 */
public abstract class UsingJMock extends JMockSugar{
    
    private Set mocks = new HashSet();
     
    // TODO This needs a Behaviour class as this is a responsibility!
    public void verify() {
        for (Iterator i = mocks.iterator(); i.hasNext();) {
            Mock mock = null;
            try {
                mock = (Mock) i.next();
                mock.verify();
            } catch (AssertionFailedError e) {
                VerificationException fakedException = new VerificationException(e.getMessage());
                mock.fakeExceptionStackTrace(fakedException);
                throw fakedException;
            }
        }
    }
    
    private void addMock(Mock m) {
        mocks.add(m);
    }
    /**
     * Interceptor class for creating instances of {@link org.jmock.Mock}.
     *
     * On construction, capture a stack trace of the point of construction, and
     * add the new instance to a {@link Set} of <tt>Mock</tt> instances to be
     * verified later.
     */
    protected class Mock extends org.jmock.Mock {
        private final Exception exceptionFromCreationPoint;

        public Mock(Class mockedType) {
            super(mockedType);
            exceptionFromCreationPoint = new Exception();
            addMock(this);
        }

        public Mock(Class mockedType, String name) {
            super(mockedType, name);
            exceptionFromCreationPoint = new Exception();
            addMock(this);
        }

        public Mock(DynamicMock coreMock) {
            super(coreMock);
            exceptionFromCreationPoint = new Exception();
            addMock(this);
        }

        /**
         * Set up a fake stack trace pointing back to the point where the
         * mock was constructed, rather than the obscure part of the framework
         * that actually does the mocking.
         */
        void fakeExceptionStackTrace(Exception exceptionToFake) {
            try {
                StackTraceElement[] creationStackTrace = exceptionFromCreationPoint.getStackTrace();
                StackTraceElement[] fakedStackTrace =
                    new StackTraceElement[creationStackTrace.length - 1];
                System.arraycopy(creationStackTrace, 1, fakedStackTrace, 0, fakedStackTrace.length);
                exceptionToFake.setStackTrace(fakedStackTrace);
            } catch (NoSuchMethodError e) {
                // shame - not running in a 1.4 VM
            }
        }

        public MatchBuilder expectsOnce(String methodName) {
            return expects(once()).method(methodName).withNoArguments();
        }

        public MatchBuilder expectsOnce(String methodName, Object arg1) {
            return expects(once()).method(methodName).with(eq(arg1));
        }
        
        public MatchBuilder expectsOnce(String methodName, Constraint arg) {
            return expects(once()).method(methodName).with(arg);
        }
        
        public MatchBuilder expectsOnce(String methodName, Constraint arg1, Constraint arg2) {
            return expects(once()).method(methodName).with(arg1, arg2);
        }
        
        public MatchBuilder expectsOnce(String methodName, Constraint arg1, Constraint arg2, Constraint arg3) {
            return expects(once()).method(methodName).with(arg1, arg2, arg3);
        }
        
        public MatchBuilder expectsOnce(String methodName, Constraint arg1, Constraint arg2, Constraint arg3, Constraint arg4) {
            return expects(once()).method(methodName).with(arg1, arg2, arg3, arg4);
        }

        public MatchBuilder expectsOnce(String methodName, Constraint[] argumentConstraints) {
            return expects(once()).method(methodName).with(argumentConstraints);
        }
        
        public MatchBuilder stubs(String methodName) {
            return stubs().method(methodName).withAnyArguments();
        }
    }

    // Syntactic sugar methods that use our Mock class
    
    protected Object stub(Class type) {
        Mock mock = new Mock(type);
        mock.setDefaultStub(new DefaultResultStub());
        return mock.proxy();
    }
    
    protected Object stub(Class type, String name) {
        Mock mock = new Mock(type, name);
        mock.setDefaultStub(new DefaultResultStub());
        return mock.proxy();
    }
}
