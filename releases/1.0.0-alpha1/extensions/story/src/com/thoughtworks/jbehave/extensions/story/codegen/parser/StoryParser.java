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
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface StoryParser {

    StoryDetails parseStory(Reader reader);
    
}
