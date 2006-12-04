/*
 * Created on 02-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.mock;


/**
 * This is the core of the mocking framework - it represents an object that can have expectations set on it
 */
public interface Mock {
    /** Create expectation with default invocation properties - invoked once */
    Expectation expects(String methodName);
    
    /** Create expectation with default invocation properties - invoked zero or more times */
    Expectation stubs(String methodName);
    
    /** Verify all the expectations on this mock */
    void verify();
    
    /** Get the underlying object - you don't need this if you use UsingMiniMock#mock(Class) */
    Object proxy();
}