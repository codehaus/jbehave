package org.jbehave.threaded;

import org.jbehave.core.behaviour.Behaviours;
import org.jbehave.threaded.swing.ComponentFinderBehaviour;
import org.jbehave.threaded.swing.DefaultWindowWrapperBehaviour;
import org.jbehave.threaded.swing.HeadlessChecker;
import org.jbehave.threaded.swing.HeadlessCheckerBehaviour;
import org.jbehave.threaded.swing.NamedComponentFilterBehaviour;
import org.jbehave.threaded.time.ClockedTimeouterBehaviour;



public class AllBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                WindowGrabberBehaviour.class,
                QueuedMiniHashMapBehaviour.class,
                
                ComponentFinderBehaviour.class,
                DefaultWindowWrapperBehaviour.class,
                HeadlessCheckerBehaviour.class,
                NamedComponentFilterBehaviour.class,
                
                ClockedTimeouterBehaviour.class,
        };
    }

}
