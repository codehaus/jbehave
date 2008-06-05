package org.jbehave.it;

import org.jbehave.core.behaviour.Behaviours;

public class TestBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                org.jbehave.it.SampleBehaviourTest.class
        };
    }
}


