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
import jbehave.framework.BehaviourResultBehaviours;
import jbehave.framework.BehavioursSupportBehaviours;
import jbehave.runner.BehaviourRunnerBehaviours;
import jbehave.runner.listener.TextListenerBehaviours;
import jbehave.runner.listener.TimerBehaviours;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehaviours implements Aggregate {
    public Class[] getBehaviourClasses() {
        return new Class[] {
            CriterionBehaviour.class,
            BehaviourResultBehaviours.class,
            BehavioursSupportBehaviours.class,
            BehaviourRunnerBehaviours.class,
            TextListenerBehaviours.class,
            TimerBehaviours.class
        };
    }
}
