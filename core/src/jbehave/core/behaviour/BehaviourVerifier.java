package jbehave.core.behaviour;

import jbehave.core.listener.ResultListener;

public class BehaviourVerifier {

    private final ResultListener listener;

    public BehaviourVerifier(ResultListener listener) {
        this.listener = listener;
    }

    public void verifyBehaviour(Behaviour behaviour) {
        behaviour.verifyTo(listener);
    }
}
