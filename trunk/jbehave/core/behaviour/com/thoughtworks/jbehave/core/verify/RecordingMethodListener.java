/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.jbehave.core.MethodListener;


/** Listener that captures verification results */
class RecordingMethodListener implements MethodListener {
    public List verifications = new ArrayList(); // all verifications
    public Result latestResult = null;

    public void methodVerificationStarting(Method method) {
    }
    
    public Result methodVerificationEnding(Result result, Object behaviourClassInstance) {
        verifications.add(result);
        latestResult = result;
		return result;
    }
    
    public Result result(int i) {
        return (Result) verifications.get(i);
    }
}