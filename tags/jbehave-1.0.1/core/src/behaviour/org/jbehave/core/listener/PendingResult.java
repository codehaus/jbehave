package org.jbehave.core.listener;

import org.jbehave.core.behaviour.BehaviourMethod;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.result.BehaviourMethodResult;

class PendingResult extends BehaviourMethodResult {
    public PendingResult(BehaviourMethod behaviourMethod) {
        super(behaviourMethod, new PendingException());
    }
}
