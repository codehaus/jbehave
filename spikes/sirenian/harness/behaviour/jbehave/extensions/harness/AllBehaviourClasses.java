package jbehave.extensions.harness;

import jbehave.core.behaviour.Behaviours;
import jbehave.extensions.harness.component.ComponentFinderBehaviour;
import jbehave.extensions.harness.component.DefaultWindowWrapperBehaviour;
import jbehave.extensions.harness.component.NamedComponentFilterBehaviour;
import jbehave.extensions.harness.time.ClockedTimeouterBehaviour;


public class AllBehaviourClasses implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                WindowGrabberBehaviour.class,
                QueuedMiniHashMapBehaviour.class,
                ComponentFinderBehaviour.class,
				ClockedTimeouterBehaviour.class,
				NamedComponentFilterBehaviour.class,
				DefaultWindowWrapperBehaviour.class
        };
    }

}
