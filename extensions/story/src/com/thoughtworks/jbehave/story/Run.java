/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story;
import java.io.OutputStreamWriter;

import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.domain.HashMapWorld;
import com.thoughtworks.jbehave.story.domain.Story;
import com.thoughtworks.jbehave.story.invoker.ScenarioInvoker;
import com.thoughtworks.jbehave.story.invoker.VisitingScenarioInvoker;
import com.thoughtworks.jbehave.story.listener.PlainTextScenarioListener;
import com.thoughtworks.jbehave.story.verifier.ScenarioVerifier;
import com.thoughtworks.jbehave.story.verifier.StoryVerifier;
import com.thoughtworks.jbehave.story.verifier.VisitingScenarioVerifier;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {

    public static void main(String[] args) {
        try {
            story((Story)Class.forName(args[0]).newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void story(Story story) {
        World world = new HashMapWorld();
        ScenarioInvoker scenarioInvoker = new VisitingScenarioInvoker(story.getClass().getName(), world);
        ScenarioVerifier scenarioVerifier = new VisitingScenarioVerifier(story.getClass().getName(), world);
        StoryVerifier storyVerifier = new StoryVerifier(scenarioInvoker, scenarioVerifier);
        PlainTextScenarioListener listener = new PlainTextScenarioListener(new OutputStreamWriter(System.out));
        storyVerifier.addListener(listener);
        storyVerifier.verify(story);
        listener.printReport();
    }
}
