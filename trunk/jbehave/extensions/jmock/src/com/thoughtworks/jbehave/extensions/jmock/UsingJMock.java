package com.thoughtworks.jbehave.extensions.jmock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.AssertionFailedError;

import org.jmock.builder.MatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.DynamicMock;
import org.jmock.core.Verifiable;
import org.jmock.core.stub.DefaultResultStub;

import com.thoughtworks.jbehave.core.exception.DelegatingVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;

/**
 * @author <a href="mailto:dnorth@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 */
public abstract class UsingJMock extends JMockSugar {

    protected final List mocks = new ArrayList();
    
    public void verify() {
        for (Iterator i = mocks.iterator(); i.hasNext();) {
            try {
                ((Verifiable) i.next()).verify();
            }
            catch (Error e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
    
    private void addMock(Mock m) {
        mocks.add(m);
    }
    
    public List getMocks() {
        return mocks;
    }

    public boolean containsMocks() {
        return !mocks.isEmpty();
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
        
        public void verify() {
            try {
                super.verify();
            }
            catch (AssertionFailedError e) {
                throw new DelegatingVerificationException(e.getMessage(), exceptionFromCreationPoint);
            }
        }

        public MatchBuilder expectsOnce(String methodName) throws VerificationException {
            return expects(once()).method(methodName).withNoArguments();
        }

        public MatchBuilder expectsOnce(String methodName, Object arg1) {
            return expects(once()).method(methodName).with(eq(arg1));
        }

        public MatchBuilder expectsOnce(String methodName, Object arg1, Object arg2) {
            return expects(once()).method(methodName).with(eq(arg1), eq(arg2));
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
            return stubs().method(methodName).withNoArguments();
        }

        public MatchBuilder stubs(String methodName, Object arg1) {
            return stubs().method(methodName).with(eq(arg1));
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
