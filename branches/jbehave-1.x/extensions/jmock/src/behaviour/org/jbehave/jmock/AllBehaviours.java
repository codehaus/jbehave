package org.jbehave.jmock;

import org.jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {
    public Class[] getBehaviours() {
        return new Class[] {
            UsingJMockBehaviour.class
        };
    }
}
