package org.jbehave.core.behaviour;

import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.result.Result;

/**
 * Manages the before/after lifecycle around verifying behaviour
 * 
 * Passes itself in as a {@link BehaviourListener} and has the composite
 * behaviour around notifying listeners when things happen
 * 
 * @author Dan North
 */
public class BehaviourVerifier implements BehaviourListener {

    private final BehaviourListener listener;

    public BehaviourVerifier(BehaviourListener listener) {
        this.listener = listener;
    }

    public BehaviourVerifier() {
        this(BehaviourListener.NULL);
    }

    public void verifyBehaviour(Behaviour behaviour) {
        listener.before(behaviour);
        behaviour.verifyTo(this);
        listener.after(behaviour);
    }

    public void before(Behaviour behaviour) {
        listener.before(behaviour);
    }

    public void gotResult(Result result) {
        listener.gotResult(result);
    }

    public void after(Behaviour behaviour) {
        listener.after(behaviour);
    }

    public void printReport() {
        listener.printReport();
    }

    public boolean hasBehaviourFailures() {
        return listener.hasBehaviourFailures();
    }
}
