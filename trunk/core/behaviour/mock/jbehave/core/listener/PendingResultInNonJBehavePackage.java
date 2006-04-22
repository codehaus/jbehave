package mock.jbehave.core.listener;

import jbehave.core.behaviour.BehaviourMethod;
import jbehave.core.exception.PendingException;
import jbehave.core.result.BehaviourMethodResult;

public class PendingResultInNonJBehavePackage extends BehaviourMethodResult {
    public PendingResultInNonJBehavePackage(BehaviourMethod behaviourMethod) {
        super(behaviourMethod, new PendingException());
    }
}
