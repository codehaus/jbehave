/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.util;

import org.jbehave.core.behaviour.Behaviours;


/**
 * @author Mauro Talevi
 */
public class UtilBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
                CamelCaseConverterBehaviour.class,
                TimerBehaviour.class
        };
    }
}
