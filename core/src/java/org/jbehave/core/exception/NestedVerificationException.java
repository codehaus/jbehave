/*
 * Created on 21-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.exception;

import java.io.PrintWriter;

/**
 * Pretend to be a {@link VerificationException}.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class NestedVerificationException extends VerificationException {
    private static final long serialVersionUID = 1L;
    private final Throwable cause;

    public NestedVerificationException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }
    
    public NestedVerificationException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
    
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        NestedVerificationException other = (NestedVerificationException) obj;
        
        return cause.equals(other.cause);
    }
    
    public int hashCode() {
        return cause.hashCode();
    }
    
    public Throwable getCause() {
        return cause;
    }

	public void printStackTrace(PrintWriter out) {
		super.printStackTrace(out);
		out.println("Caused by:");
		cause.printStackTrace(out);
	}
	
}
