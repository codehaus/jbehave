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
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
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
     
   /** eq(primitive) for float and double */
    public Constraint eq(final double expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                Number n = (Number)arg;
                return n.doubleValue() == expectedArg;
            }          
        };
    }
    
    /** eq(primitive) for byte, short, integer and long */
    public Constraint eq(final long expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                Number n = (Number)arg;
                return n.longValue() == expectedArg;
            }          
        };
    }
    
    /** eq(primitive) for char - note {@link Character} is not a {@link Number} */
    public Constraint eq(char expectedArg) {
        return eq(new Character(expectedArg));
    }
     
    /** eq(primitive) for boolean */
    public Constraint eq(boolean expectedArg) {
        return eq(expectedArg ? Boolean.TRUE : Boolean.FALSE);
    }
    
    // some useful constraints

    public Constraint same(final Object expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return expectedArg == arg;
            }
            public String toString() {
                return expectedArg.toString();
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

    public Constraint a(final Class type) {
        return instanceOf(type);
    }

    public Constraint instanceOf(final Class type) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return type.isInstance(arg);
            }
            public String toString() {
                return "object of type " + type.getName();
            }
        };
    }
    
    // boolean constraints - and, or, not
    
    public Constraint and(final Constraint a, final Constraint b) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return a.matches(arg) && b.matches(arg);
            }
            public String toString() {
                return a + " and " + b;
            }
        };
    }
    
    public Constraint both(final Constraint a, final Constraint b) {
        return and(a, b);
    }
    
    public Constraint or(final Constraint a, final Constraint b) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return a.matches(arg) || b.matches(arg);
            }
            public String toString() {
                return a + " or " + b;
            }
        };
    }
    
    public Constraint either(final Constraint a, final Constraint b) {
        return either(a, b);
    }
    
    public Constraint not(final Constraint c) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return !c.matches(arg);
            }
            public String toString() {
                return "not " + c;
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
