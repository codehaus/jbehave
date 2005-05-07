/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.domain;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Environment {
    /**
     * Get something out of the environment.
     * 
     * If it doesn't exist, store the default value.
     */
    Object get(String key, Object defaultValue);
    
    /**
     * Put a value into the environment, replacing any existing value.
     */
    void put(String key, Object value);

    /**
     * Reset the environment to its default state
     */
    void clear();
}