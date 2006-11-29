package jbehave.extensions.classmock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import jbehave.core.minimock.MiniMockObject;
import jbehave.core.mock.ExpectationRegistry;
import jbehave.core.mock.Mock;
import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Minimal implementation of mock object for classes, inspired by <a href="http://www.jmock.org>JMock</a>
 * 
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
class ClassMockObject extends MiniMockObject {

	private final Class[] constructorArgClasses;
	private final Object[] constructorArgs;

	private ClassMockObject(Class type, String name, Class[] constructorArgClasses, Object[] constructorArgs) {
		super(type, name);
		this.constructorArgClasses = constructorArgClasses;
		this.constructorArgs = constructorArgs;
	}
	
    /** get the mocked instance */
    public Object proxy() {
    	Enhancer enhancer = new Enhancer();
    	enhancer.setClassLoader(getType().getClassLoader());
    	enhancer.setSuperclass(getType());
    	enhancer.setCallback(new ExpectationHandlerDelegate());
        return enhancer.create(constructorArgClasses, constructorArgs);
    }	

    public static Mock mockClass(Class type, String name) {
    	Class[] constructorArgClasses = getConstructorArgClasses(type);
    	Object[] constructorArgs = createConstructorArgsFor(constructorArgClasses);
    	return createMockClass(type, name, constructorArgClasses, constructorArgs);
    	
    }
    
    public static Mock mockClass(final Class type, final String name, Class[] constructorArgClasses, Object[] constructorArgs) {
    	return createMockClass(type, name, constructorArgClasses, constructorArgs);
    }
    
	private static Mock createMockClass(final Class type, final String name, final Class[] constructorArgClasses, final Object[] constructorArgs) {
    	if (type.getDeclaringClass() != null && !Modifier.isStatic(type.getModifiers())) {
            throw new IllegalArgumentException("cannot mock non-static inner class " + type.getName());
        }
    	
    	Enhancer enhancer = new Enhancer();
    	enhancer.setSuperclass(type);
    	enhancer.setClassLoader(Mock.class.getClassLoader());
    	enhancer.setInterfaces(new Class[]{Mock.class, ExpectationRegistry.class});
    	enhancer.setCallback(new MethodInterceptor() {
    		final ClassMockObject mock = new ClassMockObject(type, name, constructorArgClasses, constructorArgs);
    		
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
    	
    	try {
    		return (Mock) enhancer.create(constructorArgClasses, constructorArgs);
    	} catch (CodeGenerationException e) {
    		// Does this in Eclipse, but...
    		if (e.getCause() instanceof NullPointerException) {
	    		throw caughtANullPointer(type, e);
    		} else {
    			throw e;
    		}
    	} catch (NullPointerException e) {
    		// For some reason, it does this on the command line 
    		// (calls Enhancer.nextInstance() instead of Enhancer.firstInstance() )
    		throw caughtANullPointer(type, e);
    	}
    }

	private static IllegalArgumentException caughtANullPointer(final Class type, Throwable e) {
		return new IllegalArgumentException("Caught a NullPointerException while trying to mock a " + type + ". This could be caused " +
				"because a constructor argument that couldn't be instantiated was used in the constructor. Have you tried " +
				"providing constructor arguments?", e);
	}


	private static Class[] getConstructorArgClasses(Class type) {
		Constructor[] constructors = type.getDeclaredConstructors();
		if (constructors.length == 0) {
			constructors = type.getConstructors();			
		}
		if (constructors.length == 0) {
			throw new IllegalArgumentException("No constructors available for class " + type);
		}
		if (Modifier.isPrivate(constructors[0].getModifiers())) {
			try {
				constructors[0].setAccessible(true);
			} catch (SecurityException e) {
				throw new IllegalArgumentException("No constructors available for class " + type);
			}
		}
		return constructors[0].getParameterTypes();
	}
    
	private static Object[] createConstructorArgsFor(Class[] constructorArgClasses) {
		Object[] args = new Object[constructorArgClasses.length];
		ConstructorFactory constructorFactory = new ConstructorFactory();
		
		for (int i = 0; i < args.length; i++) {
			Class clazz = constructorArgClasses[i];
			try {
				Object result = constructorFactory.construct(clazz);
				args[i] = result;
			} catch (Exception e) {
				throw new RuntimeException("Could not create constructor argument for class " + constructorArgClasses[i] + " at index " + i, e);
			}
		}
		
		return args;
	}

	private class ExpectationHandlerDelegate extends ExpectationHandler implements MethodInterceptor {
		public Object intercept(Object thisProxy, Method method, Object[] args, MethodProxy superProxy) throws Throwable {
			return this.invoke(thisProxy, method, args);
		}
	}
}
