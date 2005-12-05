/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.visitor.Visitor;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class GivenUsingMiniMock extends UsingMiniMock implements Given {
    public abstract void setUp(World world);

	public void accept(Visitor visitor) {
		visitor.visitGiven(this);
	}
	
	
}
