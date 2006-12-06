package org.jbehave.jmock;

import org.jbehave.core.behaviour.Behaviours;
import org.jbehave.core.util.BehavioursLoader;

public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
            UsingJMockBehaviour.class
        };
    }
}
