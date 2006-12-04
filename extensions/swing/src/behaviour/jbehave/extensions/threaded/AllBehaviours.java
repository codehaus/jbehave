package jbehave.extensions.threaded;

import jbehave.core.behaviour.Behaviours;
import jbehave.extensions.threaded.swing.ComponentFinderBehaviour;
import jbehave.extensions.threaded.swing.DefaultWindowWrapperBehaviour;
import jbehave.extensions.threaded.swing.HeadlessChecker;
import jbehave.extensions.threaded.swing.HeadlessCheckerBehaviour;
import jbehave.extensions.threaded.swing.NamedComponentFilterBehaviour;
import jbehave.extensions.threaded.time.ClockedTimeouterBehaviour;


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
