/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;
import jbehave.framework.Aggregate;
import jbehave.framework.CriterionBehaviour;
import jbehave.framework.EvaluationBehaviour;
import jbehave.framework.CriteriaSupportBehaviour;
import jbehave.runner.RunnerBehaviour;
import jbehave.runner.listener.TextListenerBehaviour;
import jbehave.runner.listener.TimerBehaviour;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviours implements Aggregate {
    public Class[] getBehaviourClasses() {
        return new Class[] {
            CriterionBehaviour.class,
            EvaluationBehaviour.class,
            CriteriaSupportBehaviour.class,
            RunnerBehaviour.class,
            TextListenerBehaviour.class,
            TimerBehaviour.class
        };
    }
}
