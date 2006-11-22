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
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Scenario extends Renderable {
    String getStoryName();
    String getDescription();
    void run(World world);
    void tidyUp(World world);
    boolean containsMocks();
}