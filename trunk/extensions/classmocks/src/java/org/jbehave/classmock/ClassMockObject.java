package org.jbehave.classmock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.MethodProxy;

import org.jbehave.core.minimock.MiniMockObject;
import org.jbehave.core.minimock.StubInvocationHandler;
import org.jbehave.core.mock.ExpectationRegistry;
import org.jbehave.core.mock.Mock;

import com.thoughtworks.proxy.Invoker;
import com.thoughtworks.proxy.factory.CglibProxyFactory;

/**
 * Minimal implementation of mock object for classes, inspired by <a href="http://www.jmock.org>JMock</a>
 * 
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
class ClassMockObject extends MiniMockObject {

	private ClassMockObject(Class type, String name) {
		super(type, name, new StubInvocationHandler(name));
	}
	
    /** get the mocked instance */
    public Object proxy() {
        return new CglibProxyFactory().createProxy(new Class[] {getType()}, new ExpectationHandlerDelegate());
    }

    public static Mock mockClass(final Class type, final String name) {
    	if (type.getDeclaringClass() != null && !Modifier.isStatic(type.getModifiers())) {
            throw new IllegalArgumentException("cannot mock non-static inner class " + type.getName());
        }
    	
        return (Mock) new CglibProxyFactory().createProxy(new Class[]{type, Mock.class, ExpectationRegistry.class},
                new Invoker() {
    		final ClassMockObject mock = new ClassMockObject(type, name);

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    Class targetClass = method.getDeclaringClass();
                    return (targetClass.isAssignableFrom(type))
                        ? method.invoke(mock.proxy(), args)
                        : method.invoke(mock, args);
                }
                catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }    		
    	});
    }

	private class ExpectationHandlerDelegate extends ExpectationHandler implements Invoker {
		public Object intercept(Object thisProxy, Method method, Object[] args, MethodProxy superProxy) throws Throwable {
			return this.invoke(thisProxy, method, args);
		}
	}
}
