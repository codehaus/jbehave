/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story;
import java.io.OutputStreamWriter;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.HashMapEnvironment;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.invoker.ScenarioInvoker;
import com.thoughtworks.jbehave.extensions.story.invoker.VisitingScenarioInvoker;
import com.thoughtworks.jbehave.extensions.story.listener.PlainTextScenarioListener;
import com.thoughtworks.jbehave.extensions.story.verifier.ScenarioVerifier;
import com.thoughtworks.jbehave.extensions.story.verifier.StoryVerifier;
import com.thoughtworks.jbehave.extensions.story.verifier.VisitingScenarioVerifier;


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
