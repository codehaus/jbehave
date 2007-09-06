package org.jbehave.core.behaviour;

/**
 * @author Mauro Talevi
 */
public class BehaviourBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                BehaviourClassBehaviour.class,
                BehaviourMethodBehaviour.class,
                BehaviourVerifierBehaviour.class
        };
    }

}
