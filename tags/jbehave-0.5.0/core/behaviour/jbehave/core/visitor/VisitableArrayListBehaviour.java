/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.visitor;

import jbehave.core.Block;
import jbehave.core.Ensure;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitableArrayListBehaviour extends UsingMiniMock {
    
    public void shouldTellEachElementToAcceptVisitor() throws Exception {
        // setup...
        Mock child1 = mock(Visitable.class, "child1");
        Mock child2 = mock(Visitable.class, "child2");
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        VisitableArrayList list = new VisitableArrayList();
        list.add(child1);
        list.add(child2);
        
        // expect...
        child1.expects("accept").with(visitor);
        child2.expects("accept").with(visitor).after(child1, "accept");

        // when...
        list.accept(visitor);
    }

    private static class SomeRuntimeException extends RuntimeException {}
    
    public void shouldPropagateRuntimeExceptionFromVisitingComponent() throws Exception {
        // given...
        final VisitableArrayList composite = new VisitableArrayList();
        Mock component = mock(Visitable.class);
        composite.add((Visitable) component);
        final Visitor visitor = (Visitor)stub(Visitor.class);

        // expect...
        component.expects("accept").with(anything()).will(throwException(new SomeRuntimeException()));
        
        // when...
        Ensure.throwsException(SomeRuntimeException.class, new Block() {
            public void execute() {
                composite.accept(visitor);
            }
        });
    }
}
