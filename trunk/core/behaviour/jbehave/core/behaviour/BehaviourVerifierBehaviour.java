package jbehave.core.behaviour;

import jbehave.core.listener.ResultListener;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;

public class BehaviourVerifierBehaviour extends UsingMiniMock {
    public void shouldVerifyBehaviour() throws Exception {
        // given...
        Mock behaviour = mock(Behaviour.class);
        ResultListener listener = (ResultListener) stub(ResultListener.class);
        BehaviourVerifier verifier = new BehaviourVerifier(listener);
        
        // expect...
        behaviour.expects("verifyTo").with(listener);
        
        // when...
        verifier.verifyBehaviour((Behaviour)behaviour);
        
        // then...
        verifyMocks();
    }
}
