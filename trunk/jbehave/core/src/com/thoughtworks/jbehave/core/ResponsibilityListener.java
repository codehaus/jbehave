/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.responsibility.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface ResponsibilityListener {
    void responsibilityVerificationStarting(Method responsibilityMethod);
    Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance);
}