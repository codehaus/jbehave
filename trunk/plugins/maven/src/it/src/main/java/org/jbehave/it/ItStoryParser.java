package org.jbehave.it;

import java.io.Reader;

import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.story.codegen.parser.TextStoryParser;


public class ItStoryParser extends TextStoryParser {
    
    public ItStoryParser(){
        super();
        System.out.println("Using ItStoryParser");        
    }
    
}
