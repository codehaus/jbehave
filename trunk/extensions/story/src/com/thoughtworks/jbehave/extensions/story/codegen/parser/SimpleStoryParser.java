/*
 * Created on 23-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.parser;

import java.io.Reader;

import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class SimpleStoryParser implements StoryParser {

    public StoryDetails parseStory(Reader reader) {
        return new StoryDetails("User withdraws cash",
                "Bank card holder", "be able to withdraw cash from an ATM",
                "I don't have to visit the bank");
    }
}