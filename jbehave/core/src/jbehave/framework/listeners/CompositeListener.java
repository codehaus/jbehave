/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.framework.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.framework.CriteriaVerification;
import jbehave.framework.CriteriaVerifier;
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
    
    public void specVerificationStarting(Class spec) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).specVerificationStarting(spec);
        }
    }

    public void specVerificationEnding(Class spec) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).specVerificationEnding(spec);
        }
    }

    public void criteriaVerificationStarting(CriteriaVerifier verifier) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).criteriaVerificationStarting(verifier);
        }
    }

    public void criteriaVerificationEnding(CriteriaVerification verification) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).criteriaVerificationEnding(verification);
        }
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
