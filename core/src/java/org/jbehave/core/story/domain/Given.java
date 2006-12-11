/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;

/**
 * <p>A Given is the context in which a scenario runs.</p>
 * 
 * <p>We recommend that you extend {@link GivenUsingMiniMock} to
 * implement a Given. But you don't have to.</p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @see GivenUsingMiniMock
 */
public interface Given extends ScenarioComponent {
    void setUp(World world);
}