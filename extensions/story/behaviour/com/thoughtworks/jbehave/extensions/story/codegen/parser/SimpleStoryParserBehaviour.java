/*
 * Created on 23-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.parser;

import java.io.StringReader;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class SimpleStoryParserBehaviour {
    public void shouldParseStoryTitle() throws Exception {
        // given...
        String storyText = "Story: User withdraws cash\n" + "\n"
                + "As a Bank card holder\n"
                + "I want to be able to withdraw cash from an ATM\n"
                + "So that I don't have to visit the bank\n";
        StoryParser storyParser = new SimpleStoryParser();
        
        // expect...
        StoryDetails expectedDetails = new StoryDetails(
                "User withdraws cash",
                "Bank card holder",
                "be able to withdraw cash from an ATM",
                "I don't have to visit the bank");

        // when...
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(storyText));

        // verify...
        Verify.equal(expectedDetails, storyDetails);
    }
}