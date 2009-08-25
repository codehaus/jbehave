package jbehave.it;

import jbehave.core.behaviour.Behaviours;

public class ItBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                jbehave.it.SampleBehaviour.class
        };
    }
}


