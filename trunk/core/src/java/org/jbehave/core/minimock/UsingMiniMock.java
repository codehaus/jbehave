/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.jbehave.core.UsingMocks;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.mock.UsingMatchers;


/**
 * <p>We recommend that you extend this class to use MiniMocks in
 * your behaviours. Or you could just delegate to it.</p>
 * 
 * <p>This class is also extended by various parts of JBehave's Story
 * framework.</p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class UsingMiniMock extends UsingMatchers implements UsingMocks {
    
    private final Collection mocks = new HashSet();

    public Mock mock(Class type) {
        return mock(type, mockName(type));
    }
    
    public Mock mock(Class type, String name) {
        Mock mock = MiniMockObject.mock(type, name);
        mocks.add(mock);
        return mock;
    }
    
    private String mockName(Class type) {
        String[] parts = type.getName().split("[\\.$]");
        String shortName = parts[parts.length-1];
        String lcfirst = shortName.substring(0, 1).toLowerCase() + shortName.substring(1);
        return lcfirst;
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
        return stub(type, mockName(type));
    }
    
    public Object stub(Class type, String name) {
        return mock(type, name);
//        return Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new StubInvocationHandler(name));
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
