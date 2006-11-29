package jbehave.extensions.classmock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import jbehave.core.minimock.MiniMockObject;
import jbehave.core.mock.ExpectationRegistry;
import jbehave.core.mock.Mock;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Minimal implementation of mock object for classes, inspired by <a href="http://www.jmock.org>JMock</a>
 * 
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
class ClassMockObject extends MiniMockObject {

	private ClassMockObject(Class type, String name) {
		super(type, name);
	}
	
    /** get the mocked instance */
    public Object proxy() {
    	Enhancer enhancer = new Enhancer();
    	enhancer.setClassLoader(getType().getClassLoader());
    	enhancer.setSuperclass(getType());
    	enhancer.setCallback(new ExpectationHandlerDelegate());
    	Class[] constructorArgClasses = getConstructorArgClasses(getType());
    	Object[] constructorArgs = createConstructorArgsFor(constructorArgClasses);
        return enhancer.create(constructorArgClasses, constructorArgs);
    }	

    static Mock mockClass(final Class type, final String name) {
    	if (type.getDeclaringClass() != null && !Modifier.isStatic(type.getModifiers())) {
            throw new IllegalArgumentException("cannot mock non-static inner class " + type.getName());
        }
    	
    	Enhancer enhancer = new Enhancer();
    	enhancer.setSuperclass(type);
    	enhancer.setClassLoader(Mock.class.getClassLoader());
    	enhancer.setInterfaces(new Class[]{Mock.class, ExpectationRegistry.class});
    	enhancer.setCallback(new MethodInterceptor() {
    		final ClassMockObject mock = new ClassMockObject(type, name);
    		
    	    public Object intercept(Object thisProxy, Method method, Object[] args, MethodProxy superProxy) throws Throwable 
    		{
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
    	
    	Class[] constructorArgClasses = getConstructorArgClasses(type);
    	Object[] constructorArgs = createConstructorArgsFor(constructorArgClasses);
    	
        return (Mock) enhancer.create(constructorArgClasses, constructorArgs);
    }


	private static Class[] getConstructorArgClasses(Class type) {
		Constructor[] constructors = type.getConstructors();
		if (constructors.length == 0) {
			throw new IllegalArgumentException("Cannot construct class " + type);
		}
		return constructors[0].getParameterTypes();
	}
    
	private static Object[] createConstructorArgsFor(Class[] constructorArgClasses) {
		return new Object[constructorArgClasses.length];
	}

	private class ExpectationHandlerDelegate extends ExpectationHandler implements MethodInterceptor {
		public Object intercept(Object thisProxy, Method method, Object[] args, MethodProxy superProxy) throws Throwable {
			return this.invoke(thisProxy, method, args);
		}
	}
}
