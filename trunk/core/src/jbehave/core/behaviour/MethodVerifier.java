package jbehave.core.behaviour;


class MethodVerifier implements MethodHandler{

    private final BehaviourVerifier verifier;

    public MethodVerifier(BehaviourVerifier verifier) {
        this.verifier = verifier;
    }

    public void handleClass(BehaviourClass behaviourClass) {
        behaviourClass.verifyTo(verifier);
    }

    public void handleMethod(BehaviourMethod behaviourMethod) {
        verifier.verifyBehaviour(behaviourMethod);
    }
}
