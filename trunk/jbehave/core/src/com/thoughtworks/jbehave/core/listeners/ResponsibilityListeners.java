/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.ResponsibilityListener;
import com.thoughtworks.jbehave.core.responsibility.Result;

/**
 * Good old Composite pattern. No framework is complete without one. I still
 * need to find an excuse to use the Simpleton pattern.<br>
 * <br>
 * &lt;plug&gt;This would be so much cleaner in Ruby.&lt;/plug&gt;
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ResponsibilityListeners implements ResponsibilityListener {
    private final List listeners = new ArrayList();
    
    /**
     * The composition method for the composite.
     */
    public void add(ResponsibilityListener listener) {
        listeners.add(listener);
    }
    
    // Listener methods

    public void responsibilityVerificationStarting(Method responsibilityMethod) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((ResponsibilityListener)i.next()).responsibilityVerificationStarting(responsibilityMethod);
        }
    }

    public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
           result = ((ResponsibilityListener)i.next()).responsibilityVerificationEnding(result, behaviourClassInstance);
        }
		return result;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[CompositeListener:").append(" listeners: ").append(listeners).append("]");
        return buffer.toString();
    }
}
