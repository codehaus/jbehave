package com.thoughtworks.jbehave.extensions.harness;

import com.thoughtworks.jbehave.core.behaviour.BehaviourClassContainer;
import com.thoughtworks.jbehave.extensions.harness.component.ComponentFinderBehaviour;

public class AllBehaviourClasses implements BehaviourClassContainer {

    public Class[] getBehaviourClasses() {
        return new Class[] {
                WindowGrabberBehaviour.class,
                QueuedMiniHashMapBehaviour.class,
                ComponentFinderBehaviour.class
        };
    }

}
