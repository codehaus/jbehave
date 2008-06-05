package org.jbehave.core.result;

import org.jbehave.core.behaviour.Behaviours;

/**
 * @author Mauro Talevi
 */
public class ResultBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
               ResultBehaviour.class
        };
    }

}
