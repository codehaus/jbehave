/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

/**
 * <p>An Event, occurring in a particular context, with a desired outcome,
 * forms the basis of a {@link Scenario}.</p>
 * 
 * <p>Events can be aggregated into one event using the {@link Events} class.</p>
 * 
 * <p>We recommend that you extend {@link EventUsingMiniMock} to
 * implement an Event. But you don't have to.</p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @see EventUsingMiniMock
 * @see Events
 */
public interface Event extends ScenarioComponent {
    void occurIn(World world);
    void undoIn(World world);
}