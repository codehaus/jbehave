/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.framework.Listener;

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

    public void responsibilityVerificationStarting(ResponsibilityVerifier verifier, Object behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).responsibilityVerificationStarting(verifier, behaviourClass);
        }
    }

    public ResponsibilityVerification responsibilityVerificationEnding(ResponsibilityVerification verification, Object behaviourClassInstance) {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
           verification = ((Listener)i.next()).responsibilityVerificationEnding(verification, behaviourClassInstance);
        }
		return verification;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[CompositeListener:");
        buffer.append(" listeners: ");
        buffer.append(listeners);
        buffer.append("]");
        return buffer.toString();
    }
}
