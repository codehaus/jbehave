package org.jbehave.core.story.codegen.antlr;
/*
 * Created on 27-Aug-2004
 *
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jbehave.core.story.codegen.domain.BasicDetails;
import org.jbehave.core.story.codegen.domain.ContextDetails;
import org.jbehave.core.story.codegen.domain.OutcomeDetails;
import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;

import antlr.RecognitionException;
import antlr.TokenStreamException;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StoryGeneratorSpike {
    private static String genDir = "build/generated/example/stories/";

    public StoryGeneratorSpike(Writer out) {
    }

    public static void main(String[] args) {
        try {
            // set up
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
            ve.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());

            ve.init();
            new File("build/generated").delete();
            new File("build/generated/example/stories").mkdirs();
            // read template
            StoryDetails story = buildStory();
            VelocityContext context = new VelocityContext();
            context.put("basePackage", "example.stories");
            generateStory(context, story, ve);
            generateOtherStuff(context, story, ve);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateOtherStuff(VelocityContext vcontext, StoryDetails story, VelocityEngine ve) throws Exception {
        List scenarios = story.scenarios;
        for (int i = 0; i < scenarios.size(); i++) {
            ScenarioDetails scenario = (ScenarioDetails) scenarios.get(i);
            ContextDetails context = scenario.context;
            givens(context, ve, vcontext);
            event(scenario.event, ve, vcontext);
            outcomes(scenario.outcome, ve, vcontext);

        }
    }

    private static void outcomes(OutcomeDetails outcomes, VelocityEngine ve, VelocityContext vcontext) throws Exception {
        Template outcomeTemplate = ve.getTemplate("templates/Expectation.vm");
        for (int i = 0; i < outcomes.outcomes.size(); i++) {
            BasicDetails outcome = (BasicDetails) outcomes.outcomes.get(i);
            vcontext.put("outcome", outcome.getClassName());
            merge(outcomeTemplate, vcontext, new FileWriter(genDir + outcome.getClassName() + ".java"));

        }
    }

    private static void event(BasicDetails event, VelocityEngine ve, VelocityContext vcontext) throws Exception {
        Template eventTemplate = ve.getTemplate("templates/Event.vm");
        vcontext.put("event", event.getClassName());
        merge(eventTemplate, vcontext, new FileWriter(genDir + event.getClassName() + ".java"));
    }

    private static void merge(Template eventTemplate, VelocityContext vcontext, Writer writer) throws Exception {
        eventTemplate.merge(vcontext, writer);
        writer.flush();
        writer.close();
    }

    private static void givens(ContextDetails context, VelocityEngine ve, VelocityContext vcontext) throws Exception {
        List givens = context.givens;
        Template givenTemplate = ve.getTemplate("templates/Given.vm");
        for (int j = 0; j < givens.size(); j++) {
            BasicDetails given = (BasicDetails) givens.get(j);
            vcontext.put("given", given);
            merge(givenTemplate, vcontext, new FileWriter(genDir+given.getClassName()+".java"));
        }
    }

    private static void generateStory(VelocityContext context, StoryDetails story, VelocityEngine ve) throws Exception {
        context.put("story", story);
        Template storyTemplate = ve.getTemplate("templates/Story.vm");
        merge(storyTemplate, context, new FileWriter(genDir + story.getClassName() + ".java"));
    }

    private static StoryDetails buildStory() throws TokenStreamException, RecognitionException, FileNotFoundException {
//        Reader r = new StringReader(
//               "Story: this is some text.\n" +
//                "As_a user.\n" +
//                "I_want some food.\n" +
//                "So_that I can do stuff.\n" +
//                "Scenario: Happy Scenario.\n" +
//                "Given a sentence.\n" +
//                "and another sentence.\n" +
//                "When something happens.\n" +
//                "Then do something interesting.\n" +
//                "and do something else.\n" +
//                "Scenario: UnHappy Path.\n" +
//                "Given some other sentence.\n" +
//                "When some other thing happens.\n" +
//                "Then do something good.\n" +
//                "endStory");

        new FileReader("analysis/user withdraws cash.txt");
        StoryDetails details = null;
        return details;
    }
}
