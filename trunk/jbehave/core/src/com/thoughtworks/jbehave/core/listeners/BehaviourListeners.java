/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.verify.Result;

/**
 * Good old Composite pattern. No framework is complete without one. I still
 * need to find an excuse to use the Simpleton pattern.<br>
 * <br>
 * &lt;plug&gt;This would be so much cleaner in Ruby.&lt;/plug&gt;
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @deprecated just register lots of Listeners with the BehaviourVerifier
 */
public class BehaviourListeners implements BehaviourListener {
    private final List listeners = new ArrayList();
    
    /**
     * The composition method for the composite.
     */
    public void add(BehaviourListener listener) {
        listeners.add(listener);
    }
    
    // Listener methods

    public void behaviourVerificationStarting(Behaviour behaviour) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((BehaviourListener)i.next()).behaviourVerificationStarting(behaviour);
        }
    }

    public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
		    result = ((BehaviourListener)i.next()).behaviourVerificationEnding(result, behaviour);
        }
		return result;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[CompositeListener:").append(" listeners: ").append(listeners).append("]");
        return buffer.toString();
    }

    public boolean caresAbout(Behaviour behaviour) {
        return true;
    }
}
