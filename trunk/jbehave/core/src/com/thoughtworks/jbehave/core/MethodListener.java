/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.verify.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface MethodListener {
    void methodVerificationStarting(Method method);
    Result methodVerificationEnding(Result result, Object behaviourClassInstance);
}