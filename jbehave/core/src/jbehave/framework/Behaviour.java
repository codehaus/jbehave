/*
 * Created on 28-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jbehave.BehaviourFrameworkError;

/**
 * Represents a behaviour, which is a method that can run
 * itself and present the results of its execution.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class Behaviour {
    private final Object behaviourClassInstance;
    private final Method method;

    public Behaviour(Method method) {
        this.method = method;
        try {
            this.behaviourClassInstance = method.getDeclaringClass().newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BehaviourFrameworkError("Error instantiating " + method.getDeclaringClass().getName());
        }
    }

    public String getName() {
        return method.getName();
    }
    
    public String getBehaviourClassName() {
        String className = behaviourClassInstance.getClass().getName();
        int lastDot = className.lastIndexOf('.');
        return className.substring(lastDot + 1);
    }

    /**
     * Run an individual behaviour.<br>
     * <br>
     * We call the lifecycle methods <tt>setUp</tt> and <tt>tearDown</tt>
     * in the appropriate places if either of them exist.
     */
    public BehaviourResult run() {
        BehaviourResult result = null;
        try {
            setUp();
            method.invoke(behaviourClassInstance, new Object[0]);
            return getBehaviourResult(null);
        } catch (InvocationTargetException e) {
            // method failed
            return result = getBehaviourResult(e.getTargetException());
        } catch (Exception e) {
            // anything else is bad news
            throw new BehaviourFrameworkError(e);
        }
        finally {
            try {
				tearDown();
			} catch (InvocationTargetException e) {
                // method failed
                return result != null ? result : getBehaviourResult(e.getTargetException());
            } catch (Exception e) {
                // anything else is bad news
                throw new BehaviourFrameworkError(e);
            }
        }
    }

	private void setUp() throws InvocationTargetException {
        try {
            Method setUp = behaviourClassInstance.getClass().getMethod("setUp", new Class[0]);
            setUp.invoke(behaviourClassInstance, new Object[0]);
        } catch (NoSuchMethodException e) {
            // there wasn't a setUp() method - never mind
        } catch (InvocationTargetException e) {
            // setUp failed - rethrow it
            throw e;
        } catch (Exception e) {
            // anything else is bad news
            throw new BehaviourFrameworkError(e);
        }
    }

    private void tearDown() throws InvocationTargetException {
        try {
            Method tearDown = behaviourClassInstance.getClass().getMethod("tearDown", new Class[0]);
            tearDown.invoke(behaviourClassInstance, new Object[0]);
        } catch (NoSuchMethodException e) {
            // there wasn't a tearDown() method - never mind
        } catch (InvocationTargetException e) {
            // tearDown failed - rethrow it
            throw e;
        } catch (Exception e) {
            // anything else is bad news
            throw new BehaviourFrameworkError(e);
        }
    }

    /**
     * Build a {@link BehaviourResult} based on an error condition.
     * 
     * This will be one of the following cases:
     * <ul>
     * <li>a <tt>VerificationException</tt>, which means the verify failed.
     * <li>a {@link ThreadDeath}, which should always be propagated.
     * <li>some other kind of exception was thrown.
     * </ul>
     * 
     * @throws ThreadDeath if the target exception itself is a <tt>ThreadDeath</tt>.
     */
    private BehaviourResult getBehaviourResult(Throwable targetException) {
        
        // propagate thread death otherwise Bad Things happen (or rather Good Things don't)
        if (targetException instanceof ThreadDeath) {
            throw (ThreadDeath)targetException;
        }
        else {
            return new BehaviourResult(method.getName(), method.getDeclaringClass().getName(), behaviourClassInstance, targetException);
        }
    }
}
