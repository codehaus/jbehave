/*
 * Created on 12-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.core.behaviour;

/**
 * Represents multiple behaviour classes
 * 
 * Any implementing class must also have a default constructor so that it
 * can be instantiated and interrogated.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public interface Behaviours {
    /**
     * Return an array of all the contained behaviour classes.
     * 
     * This is processed recursively, so the array could contain nested
     * <tt>Behaviours</tt>.
     */
    Class[] getBehaviourClasses();
}
