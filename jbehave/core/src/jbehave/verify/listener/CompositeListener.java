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
import jbehave.framework.CriteriaVerificationResult;
import jbehave.verify.VerifierSpec;

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
    
    public void verificationStarted(VerifierSpec runner) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).verificationStarted(runner);
        }
    }

    public void verificationEnded(VerifierSpec runner) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).verificationEnded(runner);
        }
    }

    public void specVerificationStarted(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).specVerificationStarted(behaviourClass);
        }
    }

    public void specVerificationEnded(Class behaviourClass) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).specVerificationEnded(behaviourClass);
        }
    }

    public void beforeCriteriaVerificationStarts(CriteriaVerifier behaviour) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).beforeCriteriaVerificationStarts(behaviour);
        }
    }

    public void afterCriteriaVerificationEnds(CriteriaVerificationResult behaviourResult) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((Listener)i.next()).afterCriteriaVerificationEnds(behaviourResult);
        }
    }
}
