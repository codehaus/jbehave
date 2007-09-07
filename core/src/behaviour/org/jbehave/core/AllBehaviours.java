/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.core;

import net.sf.cotta.jbehave.BehavioursLoader;
import org.jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new BehavioursLoader(getClass()).loadBehaviours();
    }
}
