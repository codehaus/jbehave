package org.jbehave.core.minimock;

import org.jbehave.core.behaviour.Behaviours;

/**
 * @author Mauro Talevi
 */
public class MiniMockBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
               MiniMockObjectBehaviour.class,
               UsingMiniMockBehaviour.class
        };
    }

}
