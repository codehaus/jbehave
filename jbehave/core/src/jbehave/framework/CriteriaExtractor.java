/*
 * Created on 25-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
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
public class CriteriaExtractor {
    private static final String CRITERIA_PREFIX = "should";
    private final Class spec;

    /**
     * @param spec the class to examine for criteria.
     */
    public CriteriaExtractor(Class spec) {
        this.spec = spec;
    }

    /**
     * Find all criteria in a spec and wrap each one in a
     * {@link CriteriaVerifier}
     * 
     * There is no particular constraint on the spec class. The criteria
     * are public void methods that take no parameters and start
     * with <tt>"<i>should</i>"</tt>.<br>
     * <br>
     * If the class implements the {@link AggregateSpec} interface, it is
     * recursively examined for any nested specs.<br>
     * <br>
     * Annoyingly, {@link Class#getMethods()} returns the methods in an
     * indeterminate order so we return an unordered {@link Collection}.<br>
     * <br>
     * It would be much nicer if the methods could be run in the order they
     * were written, since there will usually be a narrative thread to them.
     * 
     * @return an unordered set of {@link CriteriaVerifier}s.
     * @throws BehaviourFrameworkError if there are any problems constructing an Aggregate.
     */
    public Collection createCriteriaVerifiers() {
        final Collection result = new HashSet();
        
        result.addAll(extractCriteriaFromSpec());
        result.addAll(extractCriteriaFromAggregatedSpecs());
        return result;
    }

    private Collection extractCriteriaFromSpec() {
        final Collection result = new HashSet();
        Method[] methods = spec.getMethods(); // only interested in public methods
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith(CRITERIA_PREFIX) && method.getParameterTypes().length == 0) {
                result.add(new CriteriaVerifier(method));
            }
        }
        return result;
    }

    private Collection extractCriteriaFromAggregatedSpecs() {
        if (!AggregateSpec.class.isAssignableFrom(spec)) return Collections.EMPTY_SET;
        
        try {
            final Collection result = new HashSet();
            AggregateSpec aggregateInstance = (AggregateSpec)spec.newInstance();
            Class[] specs = aggregateInstance.getSpecs();
            for (int i = 0; i < specs.length; i++) {
                result.addAll(new CriteriaExtractor(specs[i]).createCriteriaVerifiers());
            }
            return result;
        }
        catch (Exception e) {
            // Something bad happened constructing the Aggregate instance
            throw new BehaviourFrameworkError("Problem getting specs from Aggregate " + spec.getName(), e);
        }
    }
}
