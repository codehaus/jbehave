package org.jbehave.junit;

import org.jbehave.core.behaviour.Behaviours;


public class AllBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                JUnitAdapterBehaviour.class,
                JUnitMethodAdapterBehaviour.class
        };
    }
}
