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

import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;


public class Stub {
    public static Object instance(final Class type) {
        return Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new StubInvocationHandler(type));
    }

    private static class StubInvocationHandler implements InvocationHandler {
        private final Class type;

        StubInvocationHandler(Class type) {
            super();
            this.type = type;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getReturnType().isInterface()) {
                Class returnType = method.getReturnType();
                return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {returnType}, new StubInvocationHandler(returnType));
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
            return "Stub for " + type.getName();
        }
    }    
}
