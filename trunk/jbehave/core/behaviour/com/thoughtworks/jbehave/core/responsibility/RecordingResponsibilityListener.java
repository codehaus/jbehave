/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.jbehave.core.ResponsibilityListener;


/** Listener that captures verification results */
class RecordingResponsibilityListener implements ResponsibilityListener {
    public List verifications = new ArrayList(); // all verifications
    public Result latestResult = null;

    public void responsibilityVerificationStarting(Method responsibilityMethod) {
    }
    
    public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
        verifications.add(result);
        latestResult = result;
		return result;
    }
    
    public Result result(int i) {
        return (Result) verifications.get(i);
    }
}