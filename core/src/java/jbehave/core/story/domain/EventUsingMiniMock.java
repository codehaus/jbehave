/*
 * Created on 25-Aug-2004
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
public abstract class EventUsingMiniMock extends UsingMiniMock implements Event {
    
    public void tidyUp(World world) {
        // default empty implementation
    };
	
	public void narrateTo(Renderer renderer) {
		renderer.renderEvent(this);
	}
}
