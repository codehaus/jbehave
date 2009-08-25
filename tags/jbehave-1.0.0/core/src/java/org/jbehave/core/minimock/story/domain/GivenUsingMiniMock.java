/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.minimock.story.domain;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.story.domain.Given;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.story.renderer.Renderer;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class GivenUsingMiniMock extends UsingMiniMock implements Given {
	public void narrateTo(Renderer renderer) {
		renderer.renderGiven(this);
	}
    
    public void cleanUp(World world) {
        // default empty implementation
    }
	
	
}
