/*
 * Created on 31-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Given extends ScenarioComponent {
    void setUp(World world);
    void tidyUp(World world);
}