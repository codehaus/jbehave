/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.verify.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerification;
import jbehave.verify.Verifier;

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
    
    public void verificationStarted(Verifier verifier) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).verificationStarted(verifier);
        }
    }

    public void verificationEnded(Verifier verifier) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).verificationEnded(verifier);
        }
    }

    public void specVerificationStarted(Class spec) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).specVerificationStarted(spec);
        }
    }

    public void specVerificationEnded(Class spec) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).specVerificationEnded(spec);
        }
    }

    public void beforeCriteriaVerificationStarts(CriteriaVerifier verifier) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).beforeCriteriaVerificationStarts(verifier);
        }
    }

    public void afterCriteriaVerificationEnds(CriteriaVerification verification) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).afterCriteriaVerificationEnds(verification);
        }
    }
}
