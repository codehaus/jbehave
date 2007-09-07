package org.jbehave.core.behaviour;

import org.jbehave.core.listener.BehaviourListener;


class MethodVerifier implements MethodHandler {

    private final BehaviourListener listener;

    public MethodVerifier(BehaviourListener listener) {
        this.listener = listener;
    }

    public void handleClass(BehaviourClass behaviourClass) {
        behaviourClass.verifyTo(listener);
    }

    public void handleMethod(BehaviourMethod behaviourMethod) {
        listener.before(behaviourMethod);
        behaviourMethod.verifyTo(listener);
        listener.after(behaviourMethod);
    }
}
