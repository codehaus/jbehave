/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;

import jbehave.framework.Aggregate;
import jbehave.framework.CriteriaSpec;
import jbehave.framework.CriteriaSupportSpec;
import jbehave.framework.CriteriaVerificationResultSpec;
import jbehave.verify.EvaluatorSpec;
import jbehave.verify.listener.TextListenerSpec;
import jbehave.verify.listener.TimerSpec;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllSpecs implements Aggregate {
    public Class[] getSpecs() {
        return new Class[] {
            CriteriaSpec.class,
            CriteriaVerificationResultSpec.class,
            CriteriaSupportSpec.class,
            EvaluatorSpec.class,
            TextListenerSpec.class,
            TimerSpec.class
        };
    }
}
