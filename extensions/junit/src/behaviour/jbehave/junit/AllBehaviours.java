package jbehave.junit;

import jbehave.core.behaviour.Behaviours;
import jbehave.core.util.BehavioursLoader;


public class AllBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                JUnitAdapterBehaviour.class,
                JUnitMethodAdapterBehaviour.class
        };
    }
}
