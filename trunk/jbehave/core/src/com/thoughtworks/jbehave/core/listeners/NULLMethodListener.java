/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.listeners;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.verify.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class NULLMethodListener implements BehaviourListener {

    public boolean caresAbout(Behaviour behaviour) {
        return false;
    }

    public void behaviourVerificationStarting(Behaviour behaviour) {
    }

    public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
        return result;
    }
}