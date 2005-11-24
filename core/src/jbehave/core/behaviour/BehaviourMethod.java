/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jbehave.core.exception.JBehaveFrameworkError;
import jbehave.core.result.BehaviourMethodResult;
import jbehave.core.result.Result;
import jbehave.core.visitor.Visitable;
import jbehave.core.visitor.Visitor;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethod implements Visitable {

    private final Object instance;
    private final Method method;

    public BehaviourMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }
	
	public BehaviourMethod(Object instance, String methodName) {
		try {
			this.instance = instance;
			this.method = instance.getClass().getMethod(methodName, new Class[0]);
		} catch (NoSuchMethodException e) {
			throw new JBehaveFrameworkError(e);
		}
	}

    public Method method() {
        return method;
    }

    public Object instance() {
        return instance;
    }

    public void accept(Visitor visitor) {
        visitor.visitBehaviourMethod(this);
    }

    public Result invoke() {
        try {
            invoke(instance, method);
            return new BehaviourMethodResult(this);
        }
        catch (InvocationTargetException e) {
            return new BehaviourMethodResult(this, e.getTargetException());
        }
        catch (Throwable e) {
            throw new JBehaveFrameworkError("Problem verifying method " + method.getName(), e);
        }
    }

    private void invoke(Object instance, Method method) throws Throwable {
        Exception thrownException = null;
        try {
            invokeMethod("setUp", instance);
            method.invoke(instance, new Object[0]);
            invokeMethod("verify", instance);
        }
        catch (Exception e) {
            throw thrownException = e;
        }
        finally {
            try {
                invokeMethod("tearDown", instance);
            }
            catch (Exception problemWithTearDown) {
                throw (thrownException != null ? thrownException : problemWithTearDown);
            }
        }
    }

    private void invokeMethod(String methodName, Object behaviourClassInstance) throws IllegalAccessException, InvocationTargetException {
        try {
            Method target = behaviourClassInstance.getClass().getMethod(methodName, new Class[0]);
            target.invoke(behaviourClassInstance, new Object[0]);
        } catch (NoSuchMethodException e) {
            // there wasn't a method - never mind
        }
    }
}
