/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;
import jbehave.evaluate.EvaluatorSpec;
import jbehave.evaluate.listener.TextListenerSpec;
import jbehave.evaluate.listener.TimerSpec;
import jbehave.framework.Aggregate;
import jbehave.framework.CriteriaSpec;
import jbehave.framework.CriteriaValidationResultSpec;
import jbehave.framework.CriteriaSupportSpec;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllSpecs implements Aggregate {
    public Class[] getSpecs() {
        return new Class[] {
            CriteriaSpec.class,
            CriteriaValidationResultSpec.class,
            CriteriaSupportSpec.class,
            EvaluatorSpec.class,
            TextListenerSpec.class,
            TimerSpec.class
        };
    }
}
