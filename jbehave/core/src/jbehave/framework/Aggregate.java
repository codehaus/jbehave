/*
 * Created on 12-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.framework;


/**
 * Implemented by any aggregate behaviour classes. It should probably
 * be called <tt>Composite</tt> but then I'd get strung up by the patterns
 * community because it isn't really an implementation of the Composite
 * pattern - more a way of grouping behaviour classes together.<br>
 * <br>
 * Any implementing class must also have a default constructor so that it
 * can be interrogated by {@link BehavioursSupport}.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public interface Aggregate {
    /**
     * Return an array of all the behaviour classes.
     * 
     * This is processed recursively, so the array could contain other
     * <tt>Aggregate</tt> behaviour classes, analagous to the Composite
     * pattern.
     */
    Class[] getBehaviourClasses();
}
