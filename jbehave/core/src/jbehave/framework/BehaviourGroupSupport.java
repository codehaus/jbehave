/*
 * Created on 25-Dec-2003
 * 
 * (c) 2003-2004 Dan North
 * 
 * See license.txt for license details
 */
package jbehave.framework;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import jbehave.BehaviourFrameworkError;

/**
 * Support class for behaviour classes.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public abstract class BehaviourGroupSupport {
    private static final String BEHAVIOUR_METHOD_PREFIX = "should";

    /**
     * Find all behaviour methods in a class and wrap each one in a
     * {@link Behaviour}
     * 
     * There is no particular constraint on the class. The behaviours
     * are public void methods that take no parameters and start
     * with <tt>"<i>should</i>"</tt>.<br>
     * <br>
     * If the class implements the {@link Aggregate} interface, it is
     * recursively examined for any nested behaviour classes.<br>
     * <br>
     * Annoyingly, {@link Class#getMethods()} returns the methods in an
     * indeterminate order so we return an unordered <tt>Collection</tt>.<br>
     * <br>
     * It would be much nicer if the methods could be run in the order they
     * were written, since there will usually be a narrative thread to them.
     * 
     * @param behaviourClass the class to examine for behaviours.
     * @return an unordered set of <tt>Behaviour</tt>s.
     * @throws BehaviourFrameworkError if there are any problems constructing an Aggregate.
     */
    public static Collection getBehaviours(Class behaviourClass) {
        final Collection result = new HashSet();
        
        result.addAll(collectBehaviours(behaviourClass));
        result.addAll(collectBehavioursFromAggregate(behaviourClass));
        return result;
    }

    private static Collection collectBehaviours(Class behaviourClass) {
        final Collection result = new HashSet();
        Method[] methods = behaviourClass.getMethods(); // only interested in public methods
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith(BEHAVIOUR_METHOD_PREFIX) && method.getParameterTypes().length == 0) {
                result.add(new Behaviour(method));
            }
        }
        return result;
    }

    private static Collection collectBehavioursFromAggregate(Class behaviourClass) {
        if (!Aggregate.class.isAssignableFrom(behaviourClass)) return Collections.EMPTY_SET;
        
        try {
            final Collection result = new HashSet();
            Aggregate aggregate = (Aggregate)behaviourClass.newInstance();
            Class[] behaviourClasses = aggregate.getBehaviourClasses();
            for (int i = 0; i < behaviourClasses.length; i++) {
                result.addAll(getBehaviours(behaviourClasses[i]));
            }
            return result;
        }
        catch (Exception e) {
            // Something bad happened constructing the Aggregate instance
            throw new BehaviourFrameworkError("Problem getting behaviours from Aggregate " + behaviourClass.getName(), e);
        }
    }
}
