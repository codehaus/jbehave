/*
 * Created on 30-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.visitor;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;

/**
 * This is a swiss army knife class - it extends {@link UsingMiniMock}, implements {@Visitable}
 * and implements the Composite pattern for visitable components.
 * 
 * See the JBehave Story Runner extension for some examples of this class in action.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class CompositeVisitableBehaviour extends UsingMiniMock {

    public void shouldPassItselfAndComponentsToVisitorInCorrectOrder() throws Exception {
        // given...
        CompositeVisitable composite = new CompositeVisitable();
        Mock component1 = mock(Visitable.class, "component1");
        Mock component2 = mock(Visitable.class, "component2");
        composite.add((Visitable) component1);
        composite.add((Visitable) component2);
        Mock visitor = mock(Visitor.class);
        
        // expect...
        visitor.expects("visit").with(composite);
        component1.expects("accept").with(visitor).after(visitor, "visit");
        component2.expects("accept").with(visitor).after(component1, "accept");
        
        // when...
        composite.accept((Visitor) visitor);
        
        // then...
        verifyMocks();
    }
    
    private static class SomeRuntimeException extends RuntimeException {}
    
    public void shouldPropagateRuntimeExceptionFromVisitingComponent() throws Exception {
        // given...
        CompositeVisitable composite = new CompositeVisitable();
        Mock component = mock(Visitable.class);
        composite.add((Visitable) component);
        Visitor visitor = (Visitor)stub(Visitor.class);

        // expect...
        component.expects("accept").with(anything()).willThrow(new SomeRuntimeException());
        
        // when...
        try {
            composite.accept(visitor);
            Verify.impossible("should have thrown exception");
        }
        catch (SomeRuntimeException expected) {
        }
        
        // then...
        verifyMocks();
    }
}
