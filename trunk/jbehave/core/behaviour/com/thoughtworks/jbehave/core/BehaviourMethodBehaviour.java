/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.Verifier;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.minimock.UsingMiniMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodBehaviour extends UsingMiniMock {
    
    public void shouldDispatchItselfToVerifier() throws Exception {
        // given...
        Mock verifier = new Mock(Verifier.class);
        Visitor visitor = (Visitor) stub(Visitor.class);
        BehaviourMethod behaviourMethod = new BehaviourMethod(null, null, (Verifier) verifier.proxy());
        
        // expect...
        verifier.expects("verify", behaviourMethod);
        
        // when...
        behaviourMethod.accept(visitor);
        
        // then...
        verifyMocks();
    }
    
    public void shouldNotifyVisitorBeforeAndAfterVerifyingItself() throws Exception {
        // given...
        Mock verifier = new Mock(Verifier.class);
        Mock visitor = new Mock(Visitor.class);
        Result result = new Result("", Result.SUCCEEDED);
        BehaviourMethod behaviourMethod = new BehaviourMethod(null, null, (Verifier) verifier.proxy());
 
        verifier.stubs("verify", anything()).willReturn(result);
        
        // expect...
        visitor.expects("before", behaviourMethod);
        visitor.expects("gotResult", result);
        visitor.expects("after", behaviourMethod);
        
        // when...
        behaviourMethod.accept((Visitor) visitor.proxy());
        
        // then...
        verifyMocks();
    }
}
