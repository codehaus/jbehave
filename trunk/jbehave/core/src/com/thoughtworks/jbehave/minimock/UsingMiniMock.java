/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.core.exception.VerificationException;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class UsingMiniMock extends MiniMockBase {
    
    private final List mocks = new ArrayList();

    /**
     * Minimal implementation of mock object, inspired by <a href="http://www.jmock.org>JMock</a>
     * 
     * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
     */
    protected class Mock {
        private final List expectations = new ArrayList();
        private final Class type;
        private final String name;
        
        /** Manages method invocations on the mock */
        private class Handler implements InvocationHandler {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Expectation expectation = findExpectationFor(method, args);
                expectation.methodCalled();
                return expectation.returnValue();
            }

            /** find expectation matching a method invocation */
            private Expectation findExpectationFor(Method method, Object[] args) {
                for (Iterator i = expectations.iterator(); i.hasNext();) {
                    Expectation expectation = (Expectation) i.next();
                    if (expectation.matches(method.getName(), args)) {
                        return expectation;
                    }
                }
                throw new VerificationException("Unexpected invocation: " + name + "." + method.getName() + '(' + Arrays.asList(args) + ')');
            }
        }
        
        /** Constructor */
        public Mock(Class type) {
            this(type, "Mock " + type.getName());
        }
        
        public Mock(Class type, String name) {
            this.type = type;
            this.name = name;
            mocks.add(this);
        }
        
        /** get the mocked instance */
        public Object proxy() {
            return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {type}, new Handler());
        }
        
        public Expectation expectsOnce(String methodName) {
            return expects(new Expectation(this, methodName)).once();
        }
        
        public Expectation expectsAtLeastOnce(String methodName) {
            return expects(new Expectation(this, methodName)).atLeastOnce();
        }
        
        public Expectation expectsNever(String methodName) {
            return expects(new Expectation(this, methodName)).never();
        }

        public Expectation ignores(String methodName) {
            return expects(new Expectation(this, methodName)).zeroOrMoreTimes();
        }
        
        public Expectation stubs(String methodName) {
            return ignores(methodName);
        }

        private Expectation expects(Expectation expectation) {
            expectations.add(expectation);
            return expectation;
        }

        /** verify all expectations on the mock */
        public void verify() {
            for (Iterator i = expectations.iterator(); i.hasNext();) {
                ((Expectation) i.next()).verify();
            }
        }

        public Expectation expectation(String id) {
            for (Iterator i = expectations.iterator(); i.hasNext();) {
                Expectation expectation = (Expectation) i.next();
                if (expectation.id().equals(id)) {
                    return expectation;
                }
            }
            throw new VerificationException("Unknown expectation id '" + id + "' for " + this);
        }
    }

    /** Verify all registered mocks */
    public void verifyMocks() {
        for (Iterator i = mocks.iterator(); i.hasNext();) {
            ((Mock) i.next()).verify();
        }
    }

    // Some common constraints
    
    /** stub an interface */
    public Object stub(final Class type) {
        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {type}, new StubInvocationHandler(type));
    }
    
    private class StubInvocationHandler implements InvocationHandler {
        private final Class type;

        private StubInvocationHandler(Class type) {
            super();
            this.type = type;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getReturnType().isInterface()) {
                return stub(method.getReturnType());
            }
            else if (method.getReturnType() == boolean.class) {
                return Boolean.FALSE;
            }
            else if (method.getReturnType() == byte.class) {
                return new Byte((byte) 0);
            }
            else if (method.getReturnType() == char.class) {
                return new Character((char) 0);
            }
            else if (method.getReturnType() == short.class) {
                return new Short((short) 0);
            }
            else if (method.getReturnType() == int.class) {
                return new Integer(0);
            }
            else if (method.getReturnType() == long.class) {
                return new Long(0);
            }
            else if (method.getReturnType() == float.class) {
                return new Float(0.0);
            }
            else if (method.getReturnType() == double.class) {
                return new Double(0.0);
            }
            else if (method.getDeclaringClass().equals(Object.class)) {
                // TODO equals doesn't work for stubs
                try {
                    // invoke Object methods on self
                    return method.invoke(this, args);
                }
                catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
                catch (Exception e) {
                    throw new JBehaveFrameworkError("Problem invoking " + method.getName());
                }
            }
            return null;
        }

        public String toString() {
            return "Stub for " + type.getName();
        }
    }
}
