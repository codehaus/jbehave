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
 * Syntactic sugar for MiniMock
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MiniMockSugar {
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
