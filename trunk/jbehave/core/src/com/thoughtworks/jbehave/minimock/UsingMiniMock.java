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
public class UsingMiniMock {
    

    /** Represents a constraint on a method argument */
    public interface Constraint {
        boolean matches(Object arg);
    }
    
    /**
     * Minimal implementation of mock object, inspired by <a href="http://www.jmock.org>JMock</a>
     * 
     * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
     */
    public class Mock {
        private final List expectations = new ArrayList();
        
        private final Class type;
        
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
                throw new VerificationException("Unexpected: " + method.getName() + '(' + Arrays.asList(args) + ')');
            }
        }
        
        /** Constructor */
        public Mock(Class type) {
            this.type = type;
            mocks.add(this);
        }
        
        /** get the mocked instance */
        public Object proxy() {
            return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {type}, new Handler());
        }

        /** expects method with no args */
        public Expectation expects(String methodName) {
            return expects(new Expectation(methodName));
        }

        /** expects method with one arg */
        public Expectation expects(String methodName, Object arg1) {
            return expects(methodName, eq(arg1));
        }

        /** expects method with one arg, using custom {@link Constraint} */
        public Expectation expects(String methodName, Constraint constraint1) {
            if (constraint1 == null) constraint1 = same(null); // for some reason, expects("method", null) matches this signature
            return expects(new Expectation(methodName, constraint1));
        }

        private Expectation expects(Expectation expectation) {
            expectations.add(expectation);
            return expectation;
        }

        /** stubs method with no args */
        public Expectation stubs(String methodName) {
            verifyMethodExists(methodName);
            return expects(new Expectation(methodName, new Constraint[0]));
        }

        /** stubs method with one arg, using custom {@link Constraint} */
        public Expectation stubs(String methodName, Constraint constraint1) {
            return expects(new Expectation(methodName, new Constraint[] {constraint1})).stubs();
        }
        
        /** verify all expectations on the mock */
        public void verify() {
            for (Iterator i = expectations.iterator(); i.hasNext();) {
                ((Expectation) i.next()).verify();
            }
        }

        /** sanity check for method name - not strictly necessary */
        private void verifyMethodExists(String methodName) {
            Method[] methods = type.getMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals(methodName)) {
                    return;
                }
            }
            throw new VerificationException("Unknown method: " + methodName + " in " + type.getName());
        }
    }
    
    private final List mocks = new ArrayList();

    /** Verify all registered mocks */
    public void verifyMocks() {
        for (Iterator i = mocks.iterator(); i.hasNext();) {
            ((Mock) i.next()).verify();
        }
    }

    // Some common constraints
    
    public Constraint eq(final Object expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return arg == null ? expectedArg == null : arg.equals(expectedArg);
            }
            public String toString() {
                return "eq(" + expectedArg + ")";
            }
        };
    }

    public Constraint same(final Object expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return expectedArg == arg;
            }
            public String toString() {
                return "same(" + expectedArg + ")";
            }
        };
    }

    public Constraint anything() {
        return new Constraint() {
            public boolean matches(Object arg) {
                return true;
            }
            public String toString() {
                return "anything";
            }
        };
    }
    
    
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
