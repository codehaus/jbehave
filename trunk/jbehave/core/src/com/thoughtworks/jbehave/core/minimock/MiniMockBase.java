/*
 * Created on 24-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MiniMockBase {
    /**
     * This is the core of MiniMock - it represents an object that can have expectations set on it
     */
    protected interface Mock {
        /** Create expectation with default invocation properties - invoked once */
        Expectation expects(String methodName);
        
        /** Create expectation with default invocation properties - invoked zero or more times */
        Expectation stubs(String methodName);
        
        /** Verify all the expectations on this mock */
        void verify();
        
        /** @deprecated you don't need this if you use {@link UsingMiniMock#mock(Class)} */
        Object proxy();
    }
    
    public interface Constraint {
        boolean matches(Object arg);
    }
    
    // Some handy constraints

    protected Constraint eq(final Object expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return arg == null ? expectedArg == null : arg.equals(expectedArg);
            }
            public String toString() {
                return "eq(" + expectedArg + ")";
            }
        };
    }

    protected Constraint same(final Object expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return expectedArg == arg;
            }
            public String toString() {
                return "same(" + expectedArg + ")";
            }
        };
    }

    protected Constraint anything() {
        return new Constraint() {
            public boolean matches(Object arg) {
                return true;
            }
            public String toString() {
                return "anything";
            }
        };
    }

    public Constraint instanceOf(final Class type) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return type.isInstance(arg);
            }
            public String toString() {
                return "instance of " + type.getName();
            }
        };
    }
    
    // some handy invocation handlers for setting up expectations
    
    protected InvocationHandler returnValue(final Object result) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                return result;
            }
        };
    }
    
    protected InvocationHandler throwException(final Throwable cause) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                throw cause;
            }
        };
    }
    
    // yawn - box all the primitive types
    
    protected InvocationHandler returnValue(byte result) {
        return returnValue(new Byte(result));
    }
    protected InvocationHandler returnValue(char result) {
        return returnValue(new Character(result));
    }
    protected InvocationHandler returnValue(short result) {
        return returnValue(new Short(result));
    }
    protected InvocationHandler returnValue(int result) {
        return returnValue(new Integer(result));
    }
    protected InvocationHandler returnValue(long result) {
        return returnValue(new Long(result));
    }
    protected InvocationHandler returnValue(float result) {
        return returnValue(new Float(result));
    }
    protected InvocationHandler returnValue(double result) {
        return returnValue(new Double(result));
    }
    protected InvocationHandler returnValue(boolean result) {
        return returnValue(result ? Boolean.TRUE : Boolean.FALSE);
    }
}
