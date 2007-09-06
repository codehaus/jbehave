/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.threaded;

import org.jbehave.core.behaviour.Behaviours;


/**
 * @author Mauro Talevi
 */
public class ThreadedBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
                ClockedTimeouterBehaviour.class,
                QueuedMiniHashMapBehaviour.class,
                QueuedObjectHolderBehaviour.class
        };
    }
}
