package org.jbehave.it;

import org.jbehave.core.behaviour.Behaviours;

public class ItBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                org.jbehave.it.SampleBehaviour.class
        };
    }
}


