/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.core;

import jbehave.core.behaviour.Behaviours;
import jbehave.core.util.BehavioursLoader;


public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new BehavioursLoader(BehavioursLoader.class).loadBehaviours();
    }
}
