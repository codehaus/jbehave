/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;

import jbehave.framework.AggregateSpec;
import jbehave.framework.CriteriaSpec;
import jbehave.framework.CriteriaExtractorSpec;
import jbehave.framework.CriteriaVerificationSpec;
import jbehave.verify.VerifierSpec;
import jbehave.verify.listener.TextListenerSpec;
import jbehave.verify.listener.TimerSpec;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllSpecs implements AggregateSpec {
    public Class[] getSpecs() {
        return new Class[] {
            CriteriaSpec.class,
            CriteriaVerificationSpec.class,
            CriteriaExtractorSpec.class,
            VerifierSpec.class,
            TextListenerSpec.class,
            TimerSpec.class
        };
    }
}
