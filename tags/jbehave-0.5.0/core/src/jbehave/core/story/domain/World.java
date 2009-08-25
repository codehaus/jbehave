/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface World {
    /**
     * Get a value out of the world.
     * 
     * If it doesn't exist, store the default value.
     */
    Object get(String key, Object defaultValue);
    
    /**
     * Put a value into the world, replacing any existing value.
     */
    void put(String key, Object value);

    /**
     * Reset the world to its default state
     */
    void clear();
}