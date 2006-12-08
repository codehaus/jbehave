/*
 * Created on 27-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.mock.Expectation;
import org.jbehave.core.mock.ExpectationRegistry;
import org.jbehave.core.mock.Mock;




/**
 * Simple implementation of mock object, inspired by <a href="http://www.jmock.org>JMock</a>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MiniMockObject implements Mock, ExpectationRegistry {
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
    protected class ExpectationHandler implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (args == null) args = new Object[0];
            
            for (Iterator i = expectations.iterator(); i.hasNext();) {
                Expectation expectation = (Expectation) i.next();
                if (expectation.matches(method.getName(), args)) {
                    return expectation.invoke(proxy, method, args);
                }
            }
            
            // if we get here we didn't match on any expectations           
            verifyNoExpectationsMatchMethodName(method, args); 
            unexpectedInvocations.add(new Invocation(method.getName(), args));
            return fallbackBehaviour.invoke(proxy, method, args);
        }

        private void verifyNoExpectationsMatchMethodName(Method method, Object[] args) {
            if (anyExpectationsMatchMethodName(method.getName())) {
            	StringBuffer message = new StringBuffer();
            	message.append("Unexpected arguments for " + name + ".").append(method.getName())
            			.append(newLine())
                		.append("Expected:").append(newLine()).append(toString(expectations))
                		.append("Got:").append(newLine()).append(method.getName()).append("[").append(toString(args)).append("]");
                throw new VerificationException(message.toString());
            }
        }

		private String newLine() {
			return System.getProperty("line.separator");
		}

		private String toString(List expectations) {
			StringBuffer message = new StringBuffer();
			for (int i = 0; i < expectations.size(); i++) {
				message.append(expectations.get(i).toString()).append(newLine());
			}
			return message.toString();
		}
		
		private String toString(Object[] args) {
			StringBuffer message = new StringBuffer();
			for (int i = 0; i < args.length; i++) {
				message.append(args[i].toString());
				if (i < args.length - 1) { message.append(", "); }
			}
			return message.toString();
		}

		private boolean anyExpectationsMatchMethodName(String methodName) {
            for (Iterator i = expectations.iterator(); i.hasNext();) {
               Expectation expectation = (Expectation) i.next();
               if(expectation.matches(methodName)) return true;
            }
            return false;
        }
    }

    
    public MiniMockObject(Class type, String name) {
        this.type = type;
        this.name = name;
        this.fallbackBehaviour = new StubInvocationHandler(name);
    }
    
    /** get the mocked instance */
    public Object proxy() {
        return Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new ExpectationHandler());
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
        for (Iterator i = expectations.iterator(); i.hasNext();) {
            ((Expectation) i.next()).verify();
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
    
    protected Class getType() {
        return type;
    }
    
    public String toString() {
        return name;
    }
    
    protected static Mock mock(final Class type, final String name) {
        Mock result = (Mock) Proxy.newProxyInstance(Mock.class.getClassLoader(),
                new Class[] { type, Mock.class, ExpectationRegistry.class },
                new InvocationHandler() {
                    private final MiniMockObject mock = new MiniMockObject(type, name);
                    
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
