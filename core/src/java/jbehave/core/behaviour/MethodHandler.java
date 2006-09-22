package jbehave.core.behaviour;


interface MethodHandler {
    void handleClass(BehaviourClass behaviourClass);

    void handleMethod(BehaviourMethod behaviourMethod);
}
