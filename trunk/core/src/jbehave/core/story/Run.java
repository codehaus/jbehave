/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story;
import java.io.OutputStreamWriter;

import jbehave.core.story.domain.HashMapWorld;
import jbehave.core.story.domain.Story;
import jbehave.core.story.domain.World;
import jbehave.core.story.invoker.ScenarioInvoker;
import jbehave.core.story.invoker.VisitingScenarioInvoker;
import jbehave.core.story.listener.PlainTextScenarioListener;
import jbehave.core.story.verifier.ScenarioVerifier;
import jbehave.core.story.verifier.StoryVerifier;
import jbehave.core.story.verifier.VisitingScenarioVerifier;



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
