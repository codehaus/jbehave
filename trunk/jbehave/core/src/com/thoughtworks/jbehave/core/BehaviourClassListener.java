/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface BehaviourClassListener {
    void behaviourClassVerificationStarting(Class behaviourClass);
    void behaviourClassVerificationEnding(Class behaviourClass);
}