/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.UsingMocks;

/**
 * Base class for behaviour classes that use MiniMock.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class UsingMiniMock extends UsingConstraints implements UsingMocks {
    
    private final List mocks = new ArrayList();

    public Mock mock(Class type) {
        return mock(type, "mock " + type.getName());
    }
    
    public Mock mock(Class type, String name) {
        Mock mock = MockObject.mock(type, name);
        mocks.add(mock);
        return mock;
    }

    public void verifyMocks() {
        for (Iterator i = mocks.iterator(); i.hasNext();) {
            ((Mock) i.next()).verify();
        }
    }

    public boolean containsMocks() {
        return !mocks.isEmpty();
    }
    
    public Object stub(Class type) {
        return stub(type, type.getName());
    }
    
    public Object stub(Class type, String name) {
        return Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new StubInvocationHandler(name));
    }

	/**
	 * Template method to verify mocks after every behaviour method.
	 * 
	 * Made final to ensure mocks are verified even if you roll your own verify method,
	 * in which case you simply override {@link #doVerify()}.
	 */
	public final void verify() throws Exception {
		doVerify();
		verifyMocks();
	}

	/**
	 * Hook method to use per-method verify functionality, called from {@link #verify()}.
	 */
	protected void doVerify() throws Exception {
	}
	
	// will(...)
	
    public InvocationHandler returnValue(final Object result) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                return result;
            }
        };
    }
    
    public InvocationHandler throwException(final Throwable cause) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                throw cause;
            }
        };
    }
    
    // yawn - box all the primitive types
    
    public InvocationHandler returnValue(byte result) {
        return returnValue(new Byte(result));
    }
    public InvocationHandler returnValue(char result) {
        return returnValue(new Character(result));
    }
    public InvocationHandler returnValue(short result) {
        return returnValue(new Short(result));
    }
    public InvocationHandler returnValue(int result) {
        return returnValue(new Integer(result));
    }
    public InvocationHandler returnValue(long result) {
        return returnValue(new Long(result));
    }
    public InvocationHandler returnValue(float result) {
        return returnValue(new Float(result));
    }
    public InvocationHandler returnValue(double result) {
        return returnValue(new Double(result));
    }
    public InvocationHandler returnValue(boolean result) {
        return returnValue(result ? Boolean.TRUE : Boolean.FALSE);
    }
}
