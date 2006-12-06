/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.renderer.Renderer;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class NarrativeBehaviour extends UsingMiniMock {

    public void shouldNarrateToRenderer() throws Exception {
        // given...
        Narrative narrative = new Narrative("role", "feature", "benefit");
        Mock renderer = mock(Renderer.class);
        
        // expect...
        renderer.expects("renderNarrative").with(narrative);

        // when...
        narrative.narrateTo((Renderer)renderer);
        
        // then...
        verifyMocks();
    }
}
