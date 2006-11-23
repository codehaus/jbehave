/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.story.renderer.Renderable;


/**
 * <p>A Scenario illustrates one situation in which the Story
 * of which it is a part delivers its benefit. It can be used,
 * with other scenarios if required, to verify that the Story
 * has been delivered. It's the JBehave equivalent of an 
 * acceptance test.</p>
 * 
 * <p>A Scenario describes a series of events, run in a particular
 * context, and for which certain outcomes are expected.</p>
 * 
 * <p>We recommend that you extend {@link ScenarioUsingMiniMock} to
 * implement Scenario. But you don't have to.</p>
 * 
 * <p>A scenario can also be used as the context to another scenario,
 * by wrapping it with a {@link GivenScenario}.</p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @see ScenarioDrivenStory
 * @see ScenarioUsingMiniMock
 */
public interface Scenario extends Renderable {
    String getStoryName();
    String getDescription();
    void run(World world);
    void tidyUp(World world);
    boolean containsMocks();
}