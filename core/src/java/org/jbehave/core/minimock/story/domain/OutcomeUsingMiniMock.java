/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.minimock.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.story.domain.Outcome;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.story.renderer.Renderer;


/**
 * Represents an outcome of a scenario
 * 
 * This class is stateful - see {@link #narrateTo(Renderer)} for details.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class OutcomeUsingMiniMock extends UsingMiniMock implements Outcome {

	public void narrateTo(Renderer renderer) {
		renderer.renderOutcome(this);
	}
    public void tidyUp(World world) {
    }
}
