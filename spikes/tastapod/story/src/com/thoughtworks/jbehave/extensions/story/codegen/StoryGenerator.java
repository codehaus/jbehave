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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.thoughtworks.jbehave.extensions.story.codegen.domain.BasicDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ContextDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.OutcomeDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ScenarioDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryGenerator {
    public StoryGenerator(Writer out) {
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
            context.put("story", story);
            Template storyTemplate = ve.getTemplate("templates/Story.vm");
            
            Writer out = new StringWriter();
            storyTemplate.merge(context, out);
            System.out.println(out.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StoryDetails buildStory() {
        StoryDetails story = new StoryDetails("Do something cool", "X", "Y", "Z");
        ContextDetails context = new ContextDetails();
        context.addGiven(new BasicDetails("account has overdraft facility", "account overdraft = $100"));
        OutcomeDetails outcome = new OutcomeDetails();
        outcome.addExpectation(new BasicDetails("dispense cash", "dispense $20"));
        ScenarioDetails scenario = new ScenarioDetails("Happy Day Scenario", context, new BasicDetails("UserRequestsCash", "user asks for $20"), outcome);
        story.addScenario(scenario);
        return story;
    }
}
