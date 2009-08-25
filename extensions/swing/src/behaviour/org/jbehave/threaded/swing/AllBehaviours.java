package org.jbehave.threaded.swing;

import org.jbehave.core.behaviour.Behaviours;
import org.jbehave.core.threaded.ClockedTimeouterBehaviour;
import org.jbehave.core.threaded.QueuedMiniHashMapBehaviour;



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
