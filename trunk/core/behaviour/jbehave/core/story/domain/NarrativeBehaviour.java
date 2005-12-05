/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class NarrativeBehaviour extends UsingMiniMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Narrative narrative = new Narrative("role", "feature", "benefit");
        Mock visitor = mock(Visitor.class);
        
        // expect...
        visitor.expects("visitNarrative").with(sameInstanceAs(narrative));

        // when...
        narrative.accept((Visitor)visitor);
    }
}
