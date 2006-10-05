/*
 * Created on 25-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.mock;

import java.util.List;

import jbehave.core.exception.VerificationException;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MockVerificationException extends VerificationException {

    public MockVerificationException(Exception cause, List invocations) {
        super(cause.getMessage());
        
    }
}
