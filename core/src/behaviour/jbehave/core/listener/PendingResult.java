package jbehave.core.listener;

import jbehave.core.behaviour.BehaviourMethod;
import jbehave.core.exception.PendingException;
import jbehave.core.result.BehaviourMethodResult;

class PendingResult extends BehaviourMethodResult {
    public PendingResult(BehaviourMethod behaviourMethod) {
        super(behaviourMethod, new PendingException());
    }
}
