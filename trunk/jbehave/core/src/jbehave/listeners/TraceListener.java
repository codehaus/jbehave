/*
 * Created on 30-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.listeners;

import java.lang.reflect.Method;

import jbehave.framework.Listener;
import jbehave.framework.ResponsibilityVerification;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TraceListener implements Listener {
    private class Log {
        public void trace(String message) {
//            System.err.println(message);
        }
    }
    Log log = new Log();

    public void behaviourClassVerificationStarting(Class behaviourClass) {
        log.trace("behaviourClassVerificationStarting: " + behaviourClass.getName());
    }

    public void behaviourClassVerificationEnding(Class behaviourClass) {
        log.trace("behaviourClassVerificationEnding: " + behaviourClass.getName());
    }

    public void responsibilityVerificationStarting(Method responsibilityMethod) {
        log.trace("responsibilityVerificationStarting: " + responsibilityMethod.getName());
    }

    public ResponsibilityVerification responsibilityVerificationEnding(ResponsibilityVerification evaluation, Object behaviourClassInstance) {
        log.trace("responsibilityVerificationEnding: " + evaluation.toString());
		return evaluation;
    }
}
