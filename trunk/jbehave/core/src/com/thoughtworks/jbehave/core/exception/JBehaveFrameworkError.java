/*
 * Created on 27-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.exception;

/**
 * Signifies that something has gone wrong in the mechanics of executing
 * behaviours, rather than exceptions or failures in the behaviours themselves.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class JBehaveFrameworkError extends Error {

    private Exception nestedException;

    public JBehaveFrameworkError(String message, Exception nestedException) {
        super(message);
        this.nestedException = nestedException;
    }

    public JBehaveFrameworkError() {
        this(null, null);
    }

    public JBehaveFrameworkError(String message) {
        this(message, null);
    }

    public JBehaveFrameworkError(Exception nestedException) {
        this(null, nestedException);
        nestedException.printStackTrace();
    }
    
	public Exception getNestedException() {
		return nestedException;
	}
}
