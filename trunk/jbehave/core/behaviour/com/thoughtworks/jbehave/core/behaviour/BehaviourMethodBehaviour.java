/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.behaviour;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodBehaviour extends UsingMiniMock {
    
    public void shouldDispatchItselfToVisitor() throws Exception {
        // given...
        Mock visitor = mock(Visitor.class);
        BehaviourMethod behaviourMethod = new BehaviourMethod(null, null);
        
        // expect...
        visitor.expects("visit").with(behaviourMethod);
        
        // when...
        behaviourMethod.accept((Visitor) visitor);
        
        // then...
        verifyMocks();
    }
}
