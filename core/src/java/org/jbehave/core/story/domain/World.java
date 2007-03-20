/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.domain;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @see HashMapWorld, CustomWorld
 */
public interface World {
    /**
     * Get a value out of the world. If it doesn't exist, store the default value.
     */
    Object get(Object key, Object defaultValue);
    
    /**
     * Get a value out of the world. If it doesn't exist, return null.
     */
    Object get(Object key);
    
    /**
     * Put a value into the world, replacing any existing value.
     */
    void put(Object key, Object value);

    /**
     * Reset the world to its default state
     */
    void clear();
}