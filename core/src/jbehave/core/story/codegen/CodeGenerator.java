/*
 * Created on 25-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.codegen;

import jbehave.core.story.codegen.domain.StoryDetails;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface CodeGenerator {

    void generateStory(StoryDetails storyDetails);

}
