/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen;

import java.io.StringWriter;
import java.io.Writer;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.thoughtworks.jbehave.extensions.story.codegen.domain.BasicDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ContextDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.OutcomeDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ScenarioDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.parser.StoryLexer;
import com.thoughtworks.jbehave.extensions.story.codegen.parser.AntlrStoryParser;
import com.thoughtworks.jbehave.extensions.story.codegen.parser.StoryTreeWalker;
import antlr.TokenStreamException;
import antlr.RecognitionException;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryGeneratorSpike {
    public StoryGeneratorSpike(Writer out) {
    }
    
    public static void main(String[] args) {
        try {
            // set up
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
            ve.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());

            ve.init();
            
            // read template
            StoryDetails story = buildStory();
            VelocityContext context = new VelocityContext();
            context.put("basePackage", "com.thoughtworks.example.story");
            context.put("story", story);
            Template storyTemplate = ve.getTemplate("templates/Story.vm");
            Writer out = new StringWriter();
            storyTemplate.merge(context, out);
            System.out.println(out.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StoryDetails buildStory() throws TokenStreamException, RecognitionException {
        Reader r = new StringReader(
                "Story: this is some text\n" +
                "As_a user\n" +
                "I_want some food\n" +
                "So_that I can do stuff\n" +
                "Scenario: Happy Scenario\n" +
                "Given a sentence\n" +
                "and another sentence\n" +
                "When something happens\n" +
                "Then do something interesting\n" +
                "and do something else\n");

        StoryLexer lexer = new StoryLexer(r);
        AntlrStoryParser parser = new AntlrStoryParser(lexer);

        parser.story();
        StoryTreeWalker walker = new StoryTreeWalker();
        StoryDetails details = walker.storyDetail(parser.getAST());
        return details;
    }
}
