/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.Result;


/** Listener that captures verification results */
class RecordingBehaviourListener implements BehaviourListener {
    public List results = new ArrayList(); // all verifications
    public Result latestResult = null;

    public void behaviourVerificationStarting(Behaviour behaviour) {
    }
    
    public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
        results.add(result);
        latestResult = result;
		return result;
    }
    
    public Result result(int i) {
        return (Result) results.get(i);
    }

    public boolean caresAbout(Behaviour behaviour) {
        return true;
    }
}