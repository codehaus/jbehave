/*
 * Created on 27-Oct-2004
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

import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.minimock.MiniMockBase.Mock;


/**
 * Minimal implementation of mock object, inspired by <a href="http://www.jmock.org>JMock</a>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
class MockObject implements Mock {
    private final List expectations = new ArrayList();
    private final Class type;
    private final String name;
    
    /** Manages method invocations on the mock */
    private class ExpectationHandler implements InvocationHandler {
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
    
    public MockObject(Class type, String name) {
        this.type = type;
        this.name = name;
    }
    
    /** get the mocked instance */
    public Object proxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {type}, new ExpectationHandler());
    }
    
    public Expectation stubs(String methodName) {
        return expects(methodName).zeroOrMoreTimes();
    }

    public Expectation expects(String methodName) {
        Expectation expectation = new Expectation(this, methodName);
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

    static Mock mock(final Class type, final String name) {
        Mock result = (Mock) Proxy.newProxyInstance(type.getClassLoader(),
                new Class[] { type, Mock.class },
                new InvocationHandler() {
                    private final MockObject mock = new MockObject(type, name);
                    
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getDeclaringClass().equals(Mock.class)) {
                            return method.invoke(mock, args);
                        }
                        else {
                            try {
                                return method.invoke(mock.proxy(), args);
                            }
                            catch (InvocationTargetException e) {
                                throw e.getTargetException();
                            }
                        }
                    }
                });
        return result;
    }
}
