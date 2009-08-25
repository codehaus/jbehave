/*
 * Created on 02-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import jbehave.core.exception.JBehaveFrameworkError;


/**
 * This is a null object implementation
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
class StubInvocationHandler implements InvocationHandler {
    private final String name;

    StubInvocationHandler(String name) {
        super();
        this.name = name;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getReturnType().isInterface()) {
            Class returnType = method.getReturnType();
            return Proxy.newProxyInstance(getClass().getClassLoader(), 
                    new Class[] {returnType},
                    new StubInvocationHandler(returnType.getName()));
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
            // TODO equals doesn't work for stubs
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
        return "Stub for " + name;
    }
}