/*
 * Created on 27-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.exception.VerificationException;


/**
 * Minimal implementation of mock object, inspired by <a href="http://www.jmock.org>JMock</a>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
class MockObject implements Mock, Expectation.Registry {
    private final List expectations = new ArrayList();
    private final List unexpectedInvocations = new ArrayList();
    private final Class type;
    private final String name;
    private InvocationHandler fallbackBehaviour;
    
    private static class Invocation {
        public final String methodName;
        public  final Object[] args;
        public Invocation(String methodName, Object[] args) {
            this.methodName = methodName;
            this.args = args;
        }
    }
    
    /** Manages method invocations on the mock */
    private class ExpectationHandler implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (args == null) args = new Object[0];
            
            for (Iterator i = expectations.iterator(); i.hasNext();) {
                Expectation expectation = (Expectation) i.next();
                if (expectation.matches(method.getName(), args)) {
                    return expectation.invoke(proxy, method, args);
                }
            }
            
            // if we get here we didn't match on any expectations
            unexpectedInvocations.add(new Invocation(method.getName(), args));
            return fallbackBehaviour.invoke(proxy, method, args);
        }
    }
    
    public MockObject(Class type, String name) {
        this.type = type;
        this.name = name;
        this.fallbackBehaviour = new StubInvocationHandler(name);
    }
    
    /** get the mocked instance */
    public Object proxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {type}, new ExpectationHandler());
    }
    
    public Expectation stubs(String methodName) {
        return expects(methodName).zeroOrMoreTimes();
    }

    public Expectation expects(String methodName) {
        Expectation expects = new Expectation(this, methodName);
        expectations.add(expects);
        return expects.once();
    }

    /** verify all expectations on the mock */
    public void verify() {
        try {
            for (Iterator i = expectations.iterator(); i.hasNext();) {
                ((Expectation) i.next()).verify();
            }
        }
        catch (VerificationException e) {
            throw new MockVerificationException(e, unexpectedInvocations);
        }
    }

    public Expectation lookup(String id) {
        for (Iterator i = expectations.iterator(); i.hasNext();) {
            Expectation expectation = (Expectation) i.next();
            if (expectation.id().equals(id)) {
                return expectation;
            }
        }
        throw new VerificationException("Unknown expectation id '" + id + "' for " + this);
    }
    
    public String toString() {
        return '[' + name + ']';
    }

    static Mock mock(final Class type, final String name) {
        Mock result = (Mock) Proxy.newProxyInstance(type.getClassLoader(),
                new Class[] { type, Mock.class, Expectation.Registry.class },
                new InvocationHandler() {
                    private final MockObject mock = new MockObject(type, name);
                    
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        try {
                            Class targetClass = method.getDeclaringClass();
                            return (targetClass.isInterface() && targetClass.isAssignableFrom(type))
                                ? method.invoke(mock.proxy(), args)
                                : method.invoke(mock, args);
                        }
                        catch (InvocationTargetException e) {
                            throw e.getTargetException();
                        }
                    }
                });
        return result;
    }
}
