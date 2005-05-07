/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story;
import java.io.OutputStreamWriter;

import com.thoughtworks.jbehave.story.domain.Environment;
import com.thoughtworks.jbehave.story.domain.HashMapEnvironment;
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
            Story story = (Story) Class.forName(args[0]).newInstance();
            Environment environment = new HashMapEnvironment();
            ScenarioInvoker scenarioInvoker = new VisitingScenarioInvoker(story.getClass().getName(), environment);
            ScenarioVerifier scenarioVerifier = new VisitingScenarioVerifier(story.getClass().getName(), environment);
            StoryVerifier storyVerifier = new StoryVerifier(scenarioInvoker, scenarioVerifier);
            PlainTextScenarioListener listener = new PlainTextScenarioListener(new OutputStreamWriter(System.out));
            storyVerifier.addListener(listener);
            storyVerifier.verify(story);
            listener.printReport();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
