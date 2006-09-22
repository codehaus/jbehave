package jbehave.core.behaviour;


public class MethodCounter implements MethodHandler {

    int total = 0;
    
    public void handleClass(BehaviourClass behaviourClass) {
        total += behaviourClass.countBehaviours();
    }

    public void handleMethod(BehaviourMethod behaviourMethod) {
        total++;
    }

    public int total() {
        return total;
    }

}
