package org.jbehave.core.story.domain;

/**
 * This class is provided for convenience for those who would like
 * to define their own world with its own accessors. Every 
 * method throws an UnsupportedOperationException unless explicitly
 * overridden by subclasses (in which case you should probably be
 * extending HashMapWorld instead). This means that it's easy for you to
 * create your own world without getting it mixed up with JBehave's
 * default implementation (the HashMapWorld).
 * 
 * It's not as pretty as we'd like, but we'd like to support the
 * old ways of doing things too.
 */
public abstract class CustomWorld implements World {

    public void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public Object get(Object key, Object defaultValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public Object get(Object key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void put(Object key, Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
