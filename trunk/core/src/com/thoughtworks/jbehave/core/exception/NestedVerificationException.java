/*
 * Created on 21-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.exception;

/**
 * Pretend to be a {@link VerificationException}.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class NestedVerificationException extends VerificationException {

    private final Throwable cause;

    public NestedVerificationException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
        try {
            setStackTrace(cause.getStackTrace());
        }
        catch (NoSuchMethodError e) {
            // shame - not running in 1.4 VM
        }
    }
    
    public NestedVerificationException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
    
    public boolean equals(Object obj) {
        return cause.equals(obj);
    }
    public int hashCode() {
        return cause.hashCode();
    }
    public Throwable getCause() {
        return cause;
    }
}
