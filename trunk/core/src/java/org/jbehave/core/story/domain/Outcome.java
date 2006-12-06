/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.story.domain.OutcomeUsingMiniMock;

/**
 * <p>An outcome follows an event that has been run in a 
 * particular context.</p>
 * 
 * <p>Outcomes can be aggregated using the {@link Outcomes} class.</p>
 * 
 * <p>We recommend that you extend {@link OutcomeUsingMiniMock} to
 * implement an Outcome. But you don't have to.</p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @see OutcomeUsingMiniMock
 * @see Outcomes
 */
public interface Outcome extends ScenarioComponent {
    void verify(World world);
}