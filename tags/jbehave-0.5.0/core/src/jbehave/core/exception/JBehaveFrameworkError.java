/*
 * Created on 27-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * Signifies that something has gone wrong in the mechanics of executing
 * behaviours, rather than exceptions or failures in the behaviours themselves.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class JBehaveFrameworkError extends Error {

    private Throwable nestedException;

    public JBehaveFrameworkError(String message, Throwable nestedException) {
        super(message);
        this.nestedException = nestedException;
    }

    public JBehaveFrameworkError() {
        this(null, null);
    }

    public JBehaveFrameworkError(String message) {
        this(message, null);
    }

    public JBehaveFrameworkError(Throwable nestedException) {
        this(null, nestedException);
        nestedException.printStackTrace();
    }
    
	public Throwable getNestedException() {
		return nestedException;
	}
    
    public void printStackTrace() {
        printStackTrace(System.err);
    }
    
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        if (nestedException != null) {
            s.println("Caused by:");
            nestedException.printStackTrace(s);
        }
    }

    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        if (nestedException != null) {
            s.println("Caused by:");
            nestedException.printStackTrace(s);
        }
    }
}
