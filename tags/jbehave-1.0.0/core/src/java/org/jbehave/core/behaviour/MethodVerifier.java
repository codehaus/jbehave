package org.jbehave.core.behaviour;

import org.jbehave.core.listener.BehaviourListener;


class MethodVerifier implements MethodHandler {

    private final BehaviourVerifier verifier;
    private final BehaviourListener listener;

    public MethodVerifier(BehaviourVerifier verifier, BehaviourListener listener) {
        this.verifier = verifier;
        this.listener = listener;
    }

    public void handleClass(BehaviourClass behaviourClass) {
        behaviourClass.verifyTo(listener);
    }

    public void handleMethod(BehaviourMethod behaviourMethod) {
        verifier.verifyBehaviour(behaviourMethod);
    }
}
