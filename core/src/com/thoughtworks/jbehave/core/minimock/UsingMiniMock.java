/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

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
public class UsingMiniMock extends MiniMockSugar implements UsingMocks {
    
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
	public final void verify() {
		doVerify();
		verifyMocks();
	}

	/**
	 * Hook method to use per-method verify functionality.
	 */
	protected void doVerify() {
	}
}
