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
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class GivenUsingMiniMock extends UsingMiniMock implements Given {
	public void narrateTo(Renderer renderer) {
		renderer.renderGiven(this);
	}
    
    public void tidyUp(World world) {
        // default empty implementation
    }
	
	
}
