/*
 * Created on 30-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Listener;
import com.thoughtworks.jbehave.core.responsibility.Result;

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

    public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
        log.trace("responsibilityVerificationEnding: " + result.toString());
		return result;
    }
}
