/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;

import jbehave.framework.CriteriaVerificationSpec;
import jbehave.framework.CriteriaVerifierSpec;
import jbehave.framework.SpecContainer;
import jbehave.framework.SpecVerifierSpec;
import jbehave.framework.listeners.TextListenerSpec;
import jbehave.framework.listeners.TimerSpec;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllSpecs implements SpecContainer {
    public Class[] getSpecs() {
        return new Class[] {
            CriteriaVerifierSpec.class,
            SpecVerifierSpec.class,
            CriteriaVerificationSpec.class,
            TextListenerSpec.class,
            TimerSpec.class
        };
    }
}
