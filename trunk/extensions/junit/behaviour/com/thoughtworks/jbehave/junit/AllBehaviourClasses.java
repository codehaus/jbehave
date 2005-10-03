/*
 * Created on 11-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.junit;

import com.thoughtworks.jbehave.core.behaviour.Behaviours;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviourClasses implements Behaviours {

    public Class[] getBehaviourClasses() {
        return new Class[] {
                JUnitAdapterBehaviour.class,
                JUnitMethodAdapterBehaviour.class
        };
    }

}
