/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import jbehave.core.exception.JBehaveFrameworkError;
import jbehave.core.exception.PendingException;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.mock.Constraint;
import jbehave.core.result.BehaviourMethodResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethod implements Behaviour {

    private final Object instance;
    private final Method method;
    private Constraint methodFilter;
    public static final Constraint ALL_METHODS = new Constraint() {
        public boolean matches(Object arg) {
            return true;
        }
    };

    public BehaviourMethod(Object instance, Method method) {
        this(instance, method, ALL_METHODS);
    }

    public BehaviourMethod(Object instance, Method method, Constraint methodFilter) {
        this.instance = instance;
        this.method = method;
        this.methodFilter = methodFilter;
    }

    public Method method() {
        return method;
    }

    public Object instance() {
        return instance;
    }

    public void verifyTo(BehaviourListener listener) {
        try {
            if (methodFilter.matches(method)) {
                invokeBehaviourMethod();
                listener.gotResult(new BehaviourMethodResult(this));
            } else {
                listener.gotResult(new BehaviourMethodResult(this, new PendingException("method skippped")));
            }
        }
        catch (InvocationTargetException e) {
            listener.gotResult(new BehaviourMethodResult(this, e.getTargetException()));
        }
        catch (Throwable e) {
            throw new JBehaveFrameworkError("Problem verifying method " + method.getName(), e);
        }
    }

    private void invokeBehaviourMethod() throws Throwable {
        Exception thrownException = null;
        try {
            invokeMethod("setUp", instance);
            method.invoke(instance, new Object[0]);
        }
        catch (Exception e) {
            throw thrownException = e;
        }
        finally {
            try {
                invokeMethod("tearDown", instance);
            }
            catch (Exception problemWithTearDown) {
                throw(thrownException != null ? thrownException : problemWithTearDown);
            }
        }
    }

    private void invokeMethod(String methodName, Object behaviourClassInstance) throws Exception {
        try {
            Method target = behaviourClassInstance.getClass().getMethod(methodName, new Class[0]);
            target.invoke(behaviourClassInstance, new Object[0]);
        } catch (NoSuchMethodException e) {
            // there wasn't a method - never mind
        }
    }

    public int countBehaviours() {
        return 1;
    }
}
