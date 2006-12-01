package jbehave.jmock;

import jbehave.core.behaviour.Behaviours;
import jbehave.core.util.BehavioursLoader;

public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
            UsingJMockBehaviour.class
        };
    }
}
