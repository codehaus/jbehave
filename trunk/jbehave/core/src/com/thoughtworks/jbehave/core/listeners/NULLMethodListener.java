/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.verify.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class NULLMethodListener implements MethodListener {

    public void methodVerificationStarting(Method method) {
    }

    public Result methodVerificationEnding(Result result, Object behaviourClassInstance) {
        return result;
    }
}