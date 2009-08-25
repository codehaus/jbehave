package org.jbehave.core.listener;

import org.jbehave.core.behaviour.Behaviours;

public class ListenerBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                PlainTextMethodListenerBehaviour.class
        };
    }

}
