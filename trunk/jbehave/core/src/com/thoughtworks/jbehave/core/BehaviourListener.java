/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.core.verify.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface BehaviourListener {
    boolean caresAbout(Behaviour behaviour);
    void behaviourVerificationStarting(Behaviour behaviour);
    Result behaviourVerificationEnding(Result result, Behaviour behaviour);
}
