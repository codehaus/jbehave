/*
 * Created on 27-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.framework.exception;

/**
 * Signifies that something has gone wrong in the mechanics of executing
 * behaviours, rather than exceptions or failures in the behaviours themselves.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehaviourFrameworkError extends Error {

    private Exception nestedException;

    public BehaviourFrameworkError(String message, Exception nestedException) {
        super(message);
        this.nestedException = nestedException;
    }

    public BehaviourFrameworkError() {
        this(null, null);
    }

    public BehaviourFrameworkError(String message) {
        this(message, null);
    }

    public BehaviourFrameworkError(Exception nestedException) {
        this(null, nestedException);
        nestedException.printStackTrace();
    }
    
	public Exception getNestedException() {
		return nestedException;
	}
}
