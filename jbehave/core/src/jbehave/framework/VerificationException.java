/*
 * Created on 26-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VerificationException extends RuntimeException {
	private final Object expected;
	private final Object actual;

	public VerificationException(String message, Object expected, Object actual) {
        super(message);
		this.expected = expected;
		this.actual = actual;
	}
    
	public VerificationException(String message) {
        this(message, null, null);
	}

	public Object getActual() {
		return actual;
	}
    
	public Object getExpected() {
		return expected;
	}
    
    public String toString() {
        StringBuffer buf = new StringBuffer("VerificationException: ");
        if (getMessage() != null) {
            buf.append(getMessage()).append(": ");
        }
        if (expected != actual) {
        	buf.append("expected:<" + expected + "> but was:<" + actual + ">");
        }
        return buf.toString();
    }
}
