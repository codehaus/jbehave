/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.listeners;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Listener;
import com.thoughtworks.jbehave.core.responsibility.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class ListenerSupport implements Listener {

    public void behaviourClassVerificationStarting(Class behaviourClass) {
    }

    public void responsibilityVerificationStarting(Method responsibilityMethod) {
    }

    public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
        return result;
    }

    public void behaviourClassVerificationEnding(Class behaviourClass) {
    }
}