/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.framework.Listener;
import jbehave.framework.responsibility.Result;

/**
 * Good old Composite pattern. No framework is complete without one. I still
 * need to find an excuse to use the Simpleton pattern.<br>
 * <br>
 * &lt;plug&gt;This would be so much cleaner in Ruby.&lt;/plug&gt;
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class CompositeListener implements Listener {
    private final List listeners = new ArrayList();
    
    /**
     * The composition method for the composite.
     */
    public void add(Listener listener) {
        listeners.add(listener);
    }
    
    // Listener methods
    
    public void behaviourClassVerificationStarting(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourClassVerificationStarting(behaviourClass);
        }
    }

    public void behaviourClassVerificationEnding(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).behaviourClassVerificationEnding(behaviourClass);
        }
    }

    public void responsibilityVerificationStarting(Method responsibilityMethod) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).responsibilityVerificationStarting(responsibilityMethod);
        }
    }

    public Result responsibilityVerificationEnding(Result result, Object behaviourClassInstance) {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
           result = ((Listener)i.next()).responsibilityVerificationEnding(result, behaviourClassInstance);
        }
		return result;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[CompositeListener:").append(" listeners: ").append(listeners).append("]");
        return buffer.toString();
    }
}
