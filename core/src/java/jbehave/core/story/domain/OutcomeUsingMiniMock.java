/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.renderer.Renderer;


/**
 * Represents an outcome of a scenario
 * 
 * This class is stateful - see {@link #narrateTo(Renderer)} for details.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class OutcomeUsingMiniMock extends UsingMiniMock implements Outcome {
    public abstract void verify(World world);
	public void narrateTo(Renderer renderer) {
		renderer.renderOutcome(this);
	}
}
